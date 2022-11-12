package seolnavy.order.infra.order;

import seolnavy.order.domain.order.Order;
import seolnavy.order.domain.order.OrderItem;
import seolnavy.order.domain.order.OrderStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStoreImpl implements OrderStore {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;

	@Override
	public Order store(final Order order) {
		return orderRepository.save(order);
	}

	@Override
	public OrderItem store(final OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

}
