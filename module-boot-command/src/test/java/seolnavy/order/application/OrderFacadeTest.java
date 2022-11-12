package seolnavy.order.application;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import seolnavy.order.common.exception.SoldOutException;
import seolnavy.order.domain.item.Item;
import seolnavy.order.domain.order.OrderCommand.RegisterOrder;
import seolnavy.order.domain.order.OrderCommand.RegisterOrderItem;
import seolnavy.order.domain.order.OrderService;
import seolnavy.order.infra.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seolnavy.order.common.init.ItemTestFixture;

@Transactional
@SpringBootTest
@DisplayName("주문 테스트")
class OrderFacadeTest {

	@Autowired private OrderService orderService;
	@Autowired private OrderRepository orderRepository;

	@Test
	@DisplayName("50,000원 미만 주문 생성 테스트")
	void registerOrder_less50000won_success() {
		// given
		final var registerOrderItems = List.of(
				buildRegisterOrderItem(ItemTestFixture.ITEM_1, 100L, 10L),
				buildRegisterOrderItem(ItemTestFixture.ITEM_2, 200L, 5L)
		);
		final var registerOrder = RegisterOrder.of(registerOrderItems);

		// when
		final var orderResult = orderService.registerOrder(registerOrder);
		final var orderId = orderResult.getOrderId();

		// then
		final var order = orderRepository.findById(orderId).get();
		assertThat(order.getTotalItemPrice()).as("총 주문금액").isEqualTo(2000);
		assertThat(order.getDeliveryPrice()).as("배송비").isEqualTo(2500);
		assertThat(order.getOrderItems().size()).as("주문상품 수").isEqualTo(2);
	}

	@Test
	@DisplayName("50,000원 이상 주문 생성 테스트")
	void registerOrder_greaterOrEqual50000won_success() {
		// given
		final var registerOrderItems = List.of(
				buildRegisterOrderItem(ItemTestFixture.ITEM_1, 50000L, 1L)
		);
		final var registerOrder = RegisterOrder.of(registerOrderItems);

		// when
		final var orderResult = orderService.registerOrder(registerOrder);
		final var orderId = orderResult.getOrderId();

		// then
		// 주문
		final var order = orderRepository.findById(orderId).get();
		assertThat(order.getTotalItemPrice()).as("총 주문금액").isEqualTo(50000);
		assertThat(order.getDeliveryPrice()).as("배송비").isZero();
		assertThat(order.getOrderItems().size()).as("주문상품 수").isEqualTo(1);

		// 주문상품
		final var orderItem = order.getOrderItems().get(0);
		assertThat(orderItem.getItemId()).as("상품번호").isEqualTo(ItemTestFixture.ITEM_1.getItemId());
		assertThat(orderItem.getOrderPrice()).as("상품가격").isEqualTo(50000);
		assertThat(orderItem.getOrderQuantity()).as("주문수량").isEqualTo(1);
	}

	@Test
	@DisplayName("재고 초과 주문 생성 테스트")
	void registerOrder_overstock_fail() {
		// given
		final var registerOrderItems = List.of(
				buildRegisterOrderItem(ItemTestFixture.ITEM_1, 50000L, ItemTestFixture.ITEM_1.getStockQuantity() + 500000)
		);
		final var registerOrder = RegisterOrder.of(registerOrderItems);

		// when
		assertThatThrownBy(() -> orderService.registerOrder(registerOrder))
				.as("재고 부족 시 SoldOutException 발생")
				.isInstanceOf(SoldOutException.class);
	}

	private static RegisterOrderItem buildRegisterOrderItem(final Item item, final Long itemPrice, final long orderQuantity) {
		return RegisterOrderItem.of(item.getItemId(), item.getItemName(), itemPrice, orderQuantity);
	}

}