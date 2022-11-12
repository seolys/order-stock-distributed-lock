package seolnavy.order.domain.order;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class OrderCommand {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class RegisterOrder {

		private List<RegisterOrderItem> orderItems;

	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class RegisterOrderItem {

		private Long itemId;
		private String itemName;
		private Long itemPrice;
		private Long orderQuantity;

		public Long getOrderPrice() {
			return itemPrice * orderQuantity;
		}

		public OrderItem toEntity(final Order order) {
			return OrderItem.of(order, itemId, itemName, itemPrice, orderQuantity);
		}
	}

}
