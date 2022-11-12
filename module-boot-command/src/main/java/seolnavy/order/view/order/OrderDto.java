package seolnavy.order.view.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDto {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class OrderAbleItem {

		private Long itemId;
		private String itemName;
		private Long itemPrice;
		private Long stockQuantity;
	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class ReceivedOrderItem {

		private Long itemId;
		private String itemName;
		private Long itemPrice;
		private Long orderQuantity;
	}

}
