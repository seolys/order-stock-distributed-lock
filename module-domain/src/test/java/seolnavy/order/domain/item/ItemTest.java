package seolnavy.order.domain.item;

import static org.assertj.core.api.Assertions.*;

import seolnavy.order.common.exception.SoldOutException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("상품 엔티티 테스트")
class ItemTest {

	@Test
	@DisplayName("재고 정상 감소")
	void decreaseStockQuantity_success() {
		// given
		final var item = Item.builder()
				.itemId(1L)
				.itemName("재고 보유 상품")
				.itemPrice(10_000L)
				.stockQuantity(5L)
				.build();

		// when
		item.decreaseStockQuantity(3L);

		// then
		assertThat(item.getStockQuantity()).isEqualByComparingTo(2L);
	}

	@Test
	@DisplayName("재고 부족 시 예외 발생")
	void decreaseStockQuantity_soldOut_fail() {
		// given
		final var item = Item.builder()
				.itemId(1L)
				.itemName("재고 부족 상품")
				.itemPrice(10_000L)
				.stockQuantity(5L)
				.build();

		// when
		assertThatThrownBy(() -> item.decreaseStockQuantity(6L))
				.as("재고 부족 시 SoldOutException 발생")
				.isInstanceOf(SoldOutException.class);
	}

}