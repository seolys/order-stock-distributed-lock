package seolnavy.order.infra.order;

import java.util.Optional;
import seolnavy.order.domain.order.Order;
import seolnavy.order.domain.order.OrderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReaderImpl implements OrderReader {

	private final OrderRepository orderRepository;

	@Override
	public Optional<Order> findOrder(final Long orderId) {
		return orderRepository.findById(orderId);
	}
}
