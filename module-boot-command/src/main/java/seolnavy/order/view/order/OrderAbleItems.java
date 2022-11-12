package seolnavy.order.view.order;

import java.util.List;
import seolnavy.order.view.order.OrderDto.OrderAbleItem;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class OrderAbleItems {

	private static final String ITEM_PRINT_FORMAT = "%-10s%-60s%-15s%-15s\n";

	private final List<OrderAbleItem> items;

	public void print() {
		printItemHeader();
		items.forEach(this::printItemInfo);
	}

	private void printItemHeader() {
		System.out.printf(ITEM_PRINT_FORMAT, "상품번호", "상품명", "판매가격", "재고수");
	}

	private void printItemInfo(final OrderAbleItem item) {
		System.out.printf(ITEM_PRINT_FORMAT, item.getItemId(), item.getItemName(), item.getItemPrice(), item.getStockQuantity());
	}
}
