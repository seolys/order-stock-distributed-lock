package seolnavy.order.domain.item;

import java.util.List;
import java.util.stream.Collectors;
import seolnavy.order.domain.item.ItemInfo.Main;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemInfoMapper {

	public static List<Main> of(final List<Item> allItems) {
		return allItems.stream()
				.map(ItemInfoMapper::of)
				.collect(Collectors.toList());
	}

	public static Main of(final Item item) {
		return Main.of(
				item.getItemId(),
				item.getItemName(),
				item.getItemPrice(),
				item.getStockQuantity()
		);
	}
}
