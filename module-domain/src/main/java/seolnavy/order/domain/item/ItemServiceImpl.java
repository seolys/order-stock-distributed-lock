package seolnavy.order.domain.item;

import java.util.List;
import seolnavy.order.domain.item.ItemCommand.RegisterItemRequest;
import seolnavy.order.domain.item.ItemInfo.Main;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

	private final ItemStore itemStore;
	private final ItemReader itemReader;

	@Transactional
	@Override
	public void registerItem(final RegisterItemRequest request) {
		itemStore.store(request.toEntity());
	}

	@Transactional(readOnly = true)
	@Override
	public List<Main> getAllItems() {
		final var items = itemReader.getAllItems();
		return ItemInfoMapper.of(items);
	}

}
