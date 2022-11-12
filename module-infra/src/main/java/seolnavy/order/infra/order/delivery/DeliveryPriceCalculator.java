package seolnavy.order.infra.order.delivery;

import seolnavy.order.domain.order.OrderCommand.RegisterOrder;

public interface DeliveryPriceCalculator {

	Long calculate(RegisterOrder price);
}
