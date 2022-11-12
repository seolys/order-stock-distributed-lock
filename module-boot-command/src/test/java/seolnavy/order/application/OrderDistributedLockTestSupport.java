package seolnavy.order.application;

import seolnavy.order.domain.item.Item;
import seolnavy.order.infra.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;


@Component
public class OrderDistributedLockTestSupport {

	@Autowired private ItemRepository itemRepository;

	@Transactional
	@Commit
	public Item save(final Item item) {
		return itemRepository.save(item);
	}

	@Transactional
	@Commit
	public void delete(final Item item) {
		itemRepository.delete(item);
	}

	public static Item buildItem(final long itemId, final long stockQuantity) {
		return Item.builder()
				.itemId(itemId)
				.itemName("itemName")
				.itemPrice(10000L)
				.stockQuantity(stockQuantity)
				.build();
	}
}
