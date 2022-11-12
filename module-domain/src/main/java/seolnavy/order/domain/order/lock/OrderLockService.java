package seolnavy.order.domain.order.lock;

import java.util.concurrent.Callable;
import seolnavy.order.domain.order.OrderCommand.RegisterOrder;

public interface OrderLockService<V> {

	V tryLock(RegisterOrder orderCommand, Callable<V> callable);

}
