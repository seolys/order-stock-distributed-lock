package seolnavy.order.application;

import static seolnavy.order.application.OrderDistributedLockTestSupport.buildItem;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import seolnavy.order.domain.item.Item;
import seolnavy.order.domain.order.OrderCommand.RegisterOrder;
import seolnavy.order.domain.order.OrderCommand.RegisterOrderItem;
import seolnavy.order.domain.order.OrderService;
import seolnavy.order.infra.item.ItemRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("주문 분산 락 테스트")
class OrderDistributedLockTest {

	public static final Integer STOCK_QUANTITY = 100;

	@Autowired private OrderService orderService;
	@Autowired private ItemRepository itemRepository;
	@Autowired private OrderDistributedLockTestSupport testSupport;

	private Item item1;
	private Item item2;

	@BeforeEach
	void setUp() {
		item1 = testSupport.save(buildItem(1L, STOCK_QUANTITY));
		item2 = testSupport.save(buildItem(2L, STOCK_QUANTITY));
	}

	@AfterEach
	void tearDown() {
		testSupport.delete(item1);
		testSupport.delete(item2);
	}

	@Test
	@DisplayName("재고 순차 감소 테스트")
	void registerOrder_success() throws InterruptedException {
		// given
		final int customerCount = STOCK_QUANTITY;
		final CountDownLatch countDownLatch = new CountDownLatch(customerCount);
		final var customers = IntStream.range(0, customerCount)
				.mapToObj(this::buildMixedOrderItemList)
				.map(registerOrderItems -> new Thread(new Customer(registerOrderItems, countDownLatch)))
				.collect(Collectors.toList());

		// when
		customers.forEach(Thread::start);

		// then
		countDownLatch.await(10, TimeUnit.SECONDS);
		final var afterItem1 = itemRepository.findById(item1.getItemId()).get();
		final var afterItem2 = itemRepository.findById(item2.getItemId()).get();
		assertThat(afterItem1.getStockQuantity()).isZero();
		assertThat(afterItem2.getStockQuantity()).isZero();
	}

	private List<RegisterOrderItem> buildMixedOrderItemList(final Integer i) {
		// 상품 순서 섞기
		if (i % 2 == 0) {
			return List.of(buildRegisterOrderItem(item1, 1), buildRegisterOrderItem(item2, 1));
		} else {
			return List.of(buildRegisterOrderItem(item2, 1), buildRegisterOrderItem(item1, 1));
		}
	}

	private RegisterOrderItem buildRegisterOrderItem(final Item item, final long orderQuantity) {
		return RegisterOrderItem.of(item.getItemId(), item.getItemName(), item.getItemPrice(), orderQuantity);
	}

	@AllArgsConstructor
	private class Customer implements Runnable {

		private List<RegisterOrderItem> orderItems;
		private CountDownLatch countDownLatch;

		@Override
		public void run() {
			final var orderRequest = RegisterOrder.of(orderItems);
			orderService.registerOrder(orderRequest);
			countDownLatch.countDown();
		}
	}

}