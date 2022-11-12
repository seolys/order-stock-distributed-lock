package seolnavy.order.application;

import java.util.List;
import seolnavy.order.domain.item.ItemCommand;
import seolnavy.order.domain.item.ItemInfo.Main;
import seolnavy.order.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemFacade {

	private final ItemService itemService;

	public void registerItem(final ItemCommand.RegisterItemRequest request) {
		itemService.registerItem(request);
	}

	public List<Main> getAllItems() {
		return itemService.getAllItems();
	}
}
