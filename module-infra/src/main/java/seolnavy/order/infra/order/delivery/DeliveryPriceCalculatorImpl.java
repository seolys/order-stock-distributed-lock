package seolnavy.order.infra.order.delivery;

import seolnavy.order.domain.order.OrderCommand.RegisterOrder;
import seolnavy.order.domain.order.OrderCommand.RegisterOrderItem;
import org.springframework.stereotype.Component;

@Component
public class DeliveryPriceCalculatorImpl implements DeliveryPriceCalculator {

	@Override
	public Long calculate(final RegisterOrder orderCommand) {
		final var totalOrderPrice = orderCommand.getOrderItems().stream()
				.map(RegisterOrderItem::getOrderPrice)
				.reduce(Long::sum).orElse(0L);
		if (totalOrderPrice >= 50_000) {
			return 0L;
		}
		return 2_500L;
	}

}
