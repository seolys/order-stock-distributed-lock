package seolnavy.order.view.order;

import java.util.List;
import java.util.stream.Collectors;
import seolnavy.order.domain.item.ItemInfo.Main;
import seolnavy.order.domain.order.OrderCommand;
import seolnavy.order.domain.order.OrderCommand.RegisterOrder;
import seolnavy.order.domain.order.OrderCommand.RegisterOrderItem;
import seolnavy.order.view.order.OrderDto.OrderAbleItem;
import seolnavy.order.view.order.OrderDto.ReceivedOrderItem;

public class OrderDtoMapper {

	public static OrderAbleItems toOrderAbleItems(final List<Main> items) {
		final var orderAbleItems = items.stream()
				.map(OrderDtoMapper::toOrderAbleItem)
				.collect(Collectors.toList());
		return OrderAbleItems.of(orderAbleItems);
	}

	private static OrderAbleItem toOrderAbleItem(final Main main) {
		return OrderAbleItem.of(main.getItemId(), main.getItemName(), main.getItemPrice(), main.getStockQuantity());
	}

	public static RegisterOrder toRegisterOrder(final List<ReceivedOrderItem> receivedOrderItems) {
		final var registerOrderItems = receivedOrderItems.stream()
				.map(OrderDtoMapper::toRegisterOrderItem)
				.collect(Collectors.toList());
		return OrderCommand.RegisterOrder.of(registerOrderItems);
	}

	private static RegisterOrderItem toRegisterOrderItem(final ReceivedOrderItem receivedOrderItem) {
		return RegisterOrderItem.of(receivedOrderItem.getItemId(), receivedOrderItem.getItemName(), receivedOrderItem.getItemPrice(),
				receivedOrderItem.getOrderQuantity());
	}

}
