package seolnavy.order.infra.order;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import seolnavy.order.domain.item.Item;
import seolnavy.order.domain.item.ItemReader;
import seolnavy.order.domain.order.Order;
import seolnavy.order.domain.order.OrderCommand.RegisterOrder;
import seolnavy.order.domain.order.OrderCommand.RegisterOrderItem;
import seolnavy.order.domain.order.OrderInfo.OrderResult;
import seolnavy.order.domain.order.OrderProcessor;
import seolnavy.order.domain.order.OrderStore;
import seolnavy.order.infra.order.delivery.DeliveryPriceCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProcessorImpl implements OrderProcessor {

	private final DeliveryPriceCalculator deliveryPriceCalculator;
	private final ItemReader itemReader;
	private final OrderStore orderStore;

	@Transactional(value = REQUIRES_NEW)
	public OrderResult order(final RegisterOrder orderCommand) {
		// 재고 감소처리
		decreaseItemStockQuantity(orderCommand);
		// 주문 생성
		final var storedOrder = storeOrder(orderCommand);
		return OrderResult.of(storedOrder);
	}

	private Order storeOrder(final RegisterOrder orderCommand) {
		final var deliveryPrice = deliveryPriceCalculator.calculate(orderCommand);

		final var order = Order.newOrder(deliveryPrice);
		final var storedOrder = orderStore.store(order);

		orderCommand.getOrderItems().forEach(orderItemCommand -> {
			final var orderItem = orderItemCommand.toEntity(storedOrder);
			orderStore.store(orderItem);
		});
		return storedOrder;
	}

	private Map<Long, Item> findItemMap(final RegisterOrder orderCommand) {
		final var itemIds = orderCommand.getOrderItems().stream()
				.map(RegisterOrderItem::getItemId)
				.collect(Collectors.toSet());
		return itemReader.getItems(itemIds)
				.stream()
				.collect(Collectors.toMap(Item::getItemId, Function.identity()));
	}

	private void decreaseItemStockQuantity(final RegisterOrder orderCommand) {
		final Map<Long, Item> itemMap = findItemMap(orderCommand);
		for (final RegisterOrderItem orderItem : orderCommand.getOrderItems()) {
			final var item = itemMap.get(orderItem.getItemId());
			item.decreaseStockQuantity(orderItem.getOrderQuantity());
		}
	}

}
