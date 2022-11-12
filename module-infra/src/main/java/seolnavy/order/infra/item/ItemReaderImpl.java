package seolnavy.order.infra.item;

import java.util.Collection;
import java.util.List;
import seolnavy.order.domain.item.Item;
import seolnavy.order.domain.item.ItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemReaderImpl implements ItemReader {

	private final ItemRepository itemRepository;

	@Override
	public List<Item> getAllItems() {
		return itemRepository.findAllByOrderByCreatedAtAsc();
	}

	@Override
	public List<Item> getItems(final Collection<Long> itemIds) {
		return itemRepository.findAllByItemIdIn(itemIds);
	}
}
