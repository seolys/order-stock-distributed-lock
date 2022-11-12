package seolnavy.order.domain.item;

import java.util.List;
import seolnavy.order.domain.item.ItemInfo.Main;

public interface ItemService {

	void registerItem(ItemCommand.RegisterItemRequest request);

	List<Main> getAllItems();

}
