package seolnavy.order.domain.order;

import seolnavy.order.domain.order.OrderCommand.RegisterOrder;
import seolnavy.order.domain.order.OrderInfo.OrderResult;

public interface OrderProcessor {

	OrderResult order(final RegisterOrder orderCommand);

}
