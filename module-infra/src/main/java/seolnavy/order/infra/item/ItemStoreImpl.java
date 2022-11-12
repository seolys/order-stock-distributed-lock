package seolnavy.order.infra.item;

import seolnavy.order.domain.item.Item;
import seolnavy.order.domain.item.ItemStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemStoreImpl implements ItemStore {

	private final ItemRepository itemRepository;

	@Override
	public Item store(final Item item) {
		return itemRepository.save(item);
	}


}
