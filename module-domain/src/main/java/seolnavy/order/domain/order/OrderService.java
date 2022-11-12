package seolnavy.order.domain.order;

public interface OrderService {

	OrderInfo.OrderResult registerOrder(OrderCommand.RegisterOrder orderCommand);

	OrderInfo.Main retrieveOrder(Long orderId);

}
