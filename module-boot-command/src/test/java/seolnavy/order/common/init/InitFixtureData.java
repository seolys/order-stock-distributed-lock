package seolnavy.order.common.init;

import static seolnavy.order.common.init.ItemTestFixture.ITEM_1;
import static seolnavy.order.common.init.ItemTestFixture.ITEM_2;
import static seolnavy.order.common.init.ItemTestFixture.ITEM_3;
import static seolnavy.order.common.init.ItemTestFixture.ITEM_4;
import static seolnavy.order.common.init.ItemTestFixture.ITEM_5;

import java.util.List;
import javax.annotation.PostConstruct;
import seolnavy.order.infra.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitFixtureData {

	@Autowired private ItemRepository itemRepository;

	@PostConstruct
	public void initData() {
		final var items = List.of(
				ITEM_1, ITEM_2, ITEM_3, ITEM_4, ITEM_5
		);
		itemRepository.saveAll(items);
	}

}
