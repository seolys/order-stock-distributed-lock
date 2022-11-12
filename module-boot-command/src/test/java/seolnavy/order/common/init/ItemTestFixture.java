package seolnavy.order.common.init;

import seolnavy.order.domain.item.Item;

public class ItemTestFixture {

	public static final Item ITEM_1 = buildItem(768848L, "[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종", 21000L, 100L);
	public static final Item ITEM_2 = buildItem(748943L, "디오디너리 데일리 세트 (Daily set)", 19000L, 100L);
	public static final Item ITEM_3 = buildItem(779989L, "버드와이저 HOME DJing 굿즈 세트", 35000L, 100L);
	public static final Item ITEM_4 = buildItem(779943L, "Fabrik Pottery Flat Cup & Saucer - Mint", 24900L, 100L);
	public static final Item ITEM_5 = buildItem(768110L, "네페라 손 세정제 대용량 500ml 이더블유지", 7000L, 100L);

	private static Item buildItem(final long itemId, final String itemName, final long itemPrice, final long stockQuantity) {
		return Item.builder()
				.itemId(itemId)
				.itemName(itemName)
				.itemPrice(itemPrice)
				.stockQuantity(stockQuantity)
				.build();
	}

}
