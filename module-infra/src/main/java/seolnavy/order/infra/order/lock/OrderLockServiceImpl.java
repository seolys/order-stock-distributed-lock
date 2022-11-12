package seolnavy.order.infra.order.lock;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import seolnavy.order.common.exception.BaseException;
import seolnavy.order.common.exception.SoldOutException;
import seolnavy.order.domain.order.OrderCommand.RegisterOrder;
import seolnavy.order.domain.order.OrderCommand.RegisterOrderItem;
import seolnavy.order.domain.order.lock.OrderLockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderLockServiceImpl<V> implements OrderLockService<V> {

	private static final String LOCK_KEY_PREFIX = "order:item:";
	private static final int WAIT_TIME = 3;
	private static final int LEASE_TIME = 1;

	private final RedissonClient redissonClient;

	@Override
	public V tryLock(final RegisterOrder orderCommand, final Callable<V> callable) {
		final RLock multiLock = getMultiLock(orderCommand);
		boolean getLock = false;
		try {
			if (!multiLock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.MINUTES)) {
				throw new IllegalStateException();
			}
			getLock = true;
			return callable.call();
		} catch (final SoldOutException e) {
			throw e;
		} catch (final Exception e) {
			throw new BaseException(e);
		} finally {
			if (getLock) {
				multiLock.unlock();
			}
		}
	}

	private RLock getMultiLock(final RegisterOrder orderCommand) {
		final Set<String> lockKeys = orderCommand.getOrderItems().stream()
				.map(RegisterOrderItem::getItemId)
				.map(this::getLockKey)
				.sorted()
				.collect(Collectors.toCollection(TreeSet::new));
		final var locks = lockKeys.stream()
				.map(lockKey -> redissonClient.getLock(lockKey))
				.collect(Collectors.toList())
				.toArray(new RLock[lockKeys.size()]);
		return redissonClient.getMultiLock(locks);
	}

	private String getLockKey(final Long itemId) {
		return LOCK_KEY_PREFIX + itemId + ":lock";
	}

}
