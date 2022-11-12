package seolnavy.order.infra.common.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import seolnavy.order.common.exception.BaseException;

@Component
@RequiredArgsConstructor
public class DistributedLockImpl<V> implements DistributedLock<V> {

	private final RedissonClient redissonClient;

	public RLock getLock(final String lockName) {
		return redissonClient.getLock(lockName);
	}

	public RLock getMultiLock(final RLock... locks) {
		return redissonClient.getMultiLock(locks);
	}

	public V tryLock(final int waitTime, final int leaseTime, final RLock lock, final Callable<V> callable) {
		boolean getLock = false;
		try {
			if (!lock.tryLock(waitTime, leaseTime, TimeUnit.MINUTES)) {
				throw new IllegalStateException();
			}
			getLock = true;
			return callable.call();
		} catch (final BaseException e) {
			throw e;
		} catch (final Exception e) {
			throw new BaseException(e);
		} finally {
			if (getLock) {
				lock.unlock();
			}
		}
	}

}
