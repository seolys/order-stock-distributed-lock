package seolnavy.order.domain.order;

public interface OrderStore {

	Order store(Order order);

	OrderItem store(OrderItem orderItem);
	
}
