package seolnavy.order.domain.order;

import java.util.Optional;

public interface OrderReader {

	Optional<Order> findOrder(Long orderId);
	
}
