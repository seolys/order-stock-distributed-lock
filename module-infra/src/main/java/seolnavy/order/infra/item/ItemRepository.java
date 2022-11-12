package seolnavy.order.infra.item;


import java.util.Collection;
import java.util.List;
import seolnavy.order.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findAllByOrderByCreatedAtAsc();

	List<Item> findAllByItemIdIn(Collection<Long> itemIds);

}
