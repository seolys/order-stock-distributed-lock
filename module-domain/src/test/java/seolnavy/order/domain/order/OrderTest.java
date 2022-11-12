package seolnavy.order.domain.order;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("주문 엔티티 테스트")
class OrderTest {

	@Test
	@DisplayName("총 주문금액")
	void getTotalItemPrice() {
		// given
		final var order = Order.newOrder(0L);

		// when
		OrderItem.of(order, 1L, "상품1", 1000L, 5L);
		OrderItem.of(order, 2L, "상품1", 2000L, 5L);

		// then
		assertThat(order.getTotalItemPrice()).isEqualByComparingTo(15_000L);
	}

	@Test
	@DisplayName("배송비")
	void getDeliveryPrice() {
		// given
		final var deliveryPrice = 2_500L;

		// when
		final var order = Order.newOrder(deliveryPrice);

		// then
		assertThat(order.getDeliveryPrice()).isEqualByComparingTo(deliveryPrice);
	}
}