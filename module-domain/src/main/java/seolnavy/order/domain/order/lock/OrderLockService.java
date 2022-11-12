package seolnavy.order.domain.order.lock;

import java.util.concurrent.Callable;
import java.util.function.Function;
import seolnavy.order.domain.order.OrderCommand.RegisterOrder;
import seolnavy.order.domain.order.OrderInfo.OrderResult;

public interface OrderLockService {

	OrderResult tryLock(RegisterOrder orderCommand, Function<RegisterOrder, OrderResult> processor);

}
