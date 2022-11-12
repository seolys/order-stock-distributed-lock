package seolnavy.order.infra.order.lock;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;
import seolnavy.order.domain.order.OrderCommand.RegisterOrder;
import seolnavy.order.domain.order.OrderCommand.RegisterOrderItem;
import seolnavy.order.domain.order.OrderInfo.OrderResult;
import seolnavy.order.domain.order.lock.OrderLockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;
import seolnavy.order.infra.common.lock.DistributedLockImpl;

@Component
@RequiredArgsConstructor
public class OrderLockServiceImpl implements OrderLockService {

	private static final String LOCK_KEY_PREFIX = "order:item:";
	private static final int WAIT_TIME = 3;
	private static final int LEASE_TIME = 1;

	private final DistributedLockImpl<OrderResult> lock;

	@Override
	public OrderResult tryLock(final RegisterOrder orderCommand, final Function<RegisterOrder, OrderResult> processor) {
		final RLock multiLock = getMultiLock(orderCommand);
		return lock.tryLock(WAIT_TIME, LEASE_TIME, multiLock, () -> processor.apply(orderCommand));
	}

	private RLock getMultiLock(final RegisterOrder command) {
		final Set<String> lockKeys = getSortedLockKeys(command);
		final RLock[] locks = getLocks(lockKeys);
		return lock.getMultiLock(locks);
	}

	private RLock[] getLocks(final Set<String> lockKeys) {
		return lockKeys.stream()
				.map(lockKey -> lock.getLock(lockKey))
				.collect(Collectors.toList())
				.toArray(RLock[]::new);
	}

	private Set<String> getSortedLockKeys(final RegisterOrder command) {
		return command.getOrderItems().stream()
				.map(RegisterOrderItem::getItemId)
				.map(this::getLockKey)
				.sorted()
				.collect(Collectors.toCollection(TreeSet::new));
	}

	private String getLockKey(final Long itemId) {
		return LOCK_KEY_PREFIX + itemId + ":lock";
	}

}
