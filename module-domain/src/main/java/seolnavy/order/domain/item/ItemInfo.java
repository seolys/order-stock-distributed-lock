package seolnavy.order.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ItemInfo {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class Main {

		private Long itemId;
		private String itemName;
		private Long itemPrice;
		private Long stockQuantity;
	}

}