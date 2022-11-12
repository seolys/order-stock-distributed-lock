package seolnavy.order.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ItemCommand {

	@Getter
	@AllArgsConstructor(staticName = "of")
	public static class RegisterItemRequest {

		private Long itemId;
		private String itemName;
		private Long itemPrice;
		private Long stockQuantity;

		public Item toEntity() {
			return Item.builder()
					.itemId(itemId)
					.itemName(itemName)
					.itemPrice(itemPrice)
					.stockQuantity(stockQuantity)
					.build();
		}
	}
}
