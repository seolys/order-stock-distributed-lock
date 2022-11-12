package seolnavy.order.domain.item;

import java.util.Collection;
import java.util.List;

public interface ItemReader {

	List<Item> getAllItems();

	List<Item> getItems(Collection<Long> itemIds);
}
