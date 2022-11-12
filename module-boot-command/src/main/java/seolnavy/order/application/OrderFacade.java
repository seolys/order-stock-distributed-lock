package seolnavy.order.application;

import seolnavy.order.domain.order.OrderCommand.RegisterOrder;
import seolnavy.order.domain.order.OrderInfo.Main;
import seolnavy.order.domain.order.OrderInfo.OrderResult;
import seolnavy.order.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacade {

	private final OrderService orderService;

	public OrderResult registerOrder(final RegisterOrder orderCommand) {
		return orderService.registerOrder(orderCommand);
	}

	public Main retrieveOrder(final Long orderId) {
		return orderService.retrieveOrder(orderId);
	}
}
