package seolnavy.order.domain.order;

import static seolnavy.order.domain.order.OrderCommand.RegisterOrder;

import seolnavy.order.common.exception.EntityNotFoundException;
import seolnavy.order.domain.order.OrderInfo.Main;
import seolnavy.order.domain.order.OrderInfo.OrderResult;
import seolnavy.order.domain.order.lock.OrderLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderLockService orderLockService;
	private final OrderProcessor orderProcessor;
	private final OrderReader orderReader;

	@Override
	public OrderResult registerOrder(final RegisterOrder orderCommand) {
		// 상품 재고 Lock걸고, 주문 처리완료 후 Lock 해제
		return orderLockService.tryLock(orderCommand, orderProcessor::order);
	}

	@Transactional(readOnly = true)
	@Override
	public Main retrieveOrder(final Long orderId) {
		final var order = orderReader.findOrder(orderId)
				.orElseThrow(EntityNotFoundException::new);
		return OrderInfoMapper.of(order);
	}

}
