package seolnavy.order.domain.order;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class OrderInfo {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class OrderResult {

		private Long orderId;

		public static OrderResult of(final Order storedOrder) {
			return OrderResult.of(storedOrder.getOrderId());
		}
	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class Main {

		private Long orderId;

		private ZonedDateTime orderedAt;

		private Long totalItemPrice;

		private Long deliveryPrice;

		private List<OrderItem> orderItems;
	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class OrderItem {

		private Long orderItemId;

		private Order order;

		private String itemName;

		private Long itemPrice;

		private Long orderQuantity;
	}

}
