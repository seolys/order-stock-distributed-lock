package seolnavy.order.application;

import static seolnavy.order.application.OrderDistributedLockTestSupport.buildItem;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import seolnavy.order.common.exception.SoldOutException;
import seolnavy.order.domain.item.Item;
import seolnavy.order.domain.order.OrderCommand.RegisterOrder;
import seolnavy.order.domain.order.OrderCommand.RegisterOrderItem;
import seolnavy.order.domain.order.OrderInfo.OrderResult;
import seolnavy.order.domain.order.OrderService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("주문 분산 락 매진 테스트")
class OrderDistributedLockSoldOutTest {

	public static final Integer STOCK_QUANTITY = 100;

	@Autowired private OrderService orderService;
	@Autowired private OrderDistributedLockTestSupport testSupport;

	private Item item1;

	@BeforeEach
	void setUp() {
		item1 = testSupport.save(buildItem(1L, STOCK_QUANTITY));
	}

	@AfterEach
	void tearDown() {
		testSupport.delete(item1);
	}

	@Test
	@DisplayName("SoldOutException 테스트")
	void registerOrder_soldOut_fail() throws InterruptedException {
		// given
		final int customerCount = STOCK_QUANTITY + 1;
		final CountDownLatch countDownLatch = new CountDownLatch(customerCount);
		final var customers = IntStream.range(0, customerCount)
				.mapToObj(i -> List.of(buildRegisterOrderItem(item1, 1)))
				.map(registerOrderItems -> new Customer(registerOrderItems, countDownLatch))
				.collect(Collectors.toList());

		// when
		final ExecutorService service = Executors.newFixedThreadPool(10);
		final var futures = service.invokeAll(customers);

		// then
		assertThatThrownBy(() -> futures.forEach(future -> get(future)))
				.isInstanceOf(SoldOutException.class);
	}

	private static void get(final Future<OrderResult> future) {
		try {
			future.get();
		} catch (final InterruptedException | ExecutionException e) {
			if (isSoldOutException(e)) {
				throw (SoldOutException) e.getCause();
			}
			throw new RuntimeException(e);
		}
	}

	private static boolean isSoldOutException(final Exception e) {
		return e.getCause() instanceof SoldOutException;
	}

	private RegisterOrderItem buildRegisterOrderItem(final Item item, final long orderQuantity) {
		return RegisterOrderItem.of(item.getItemId(), item.getItemName(), item.getItemPrice(), orderQuantity);
	}

	@AllArgsConstructor
	private class Customer implements Callable<OrderResult> {

		private List<RegisterOrderItem> orderItems;
		private CountDownLatch countDownLatch;

		@Override public OrderResult call() {
			final var orderRequest = RegisterOrder.of(orderItems);
			final var orderResult = orderService.registerOrder(orderRequest);
			countDownLatch.countDown();
			return orderResult;
		}
	}

}