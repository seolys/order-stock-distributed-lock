package seolnavy.order.infra.common.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import seolnavy.order.common.exception.BaseException;

public interface DistributedLock<V> {

	RLock getLock(final String lockName);

	RLock getMultiLock(final RLock... locks);

	V tryLock(final int waitTime, final int leaseTime, final RLock lock, final Callable<V> callable);

}
