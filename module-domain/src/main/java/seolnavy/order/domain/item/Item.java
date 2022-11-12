package seolnavy.order.domain.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import seolnavy.order.common.exception.SoldOutException;
import seolnavy.order.domain.AbstractEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;

@Slf4j
@Getter
@Entity
@Table(
		name = "item",
		indexes = {
				@Index(name = "INDEX_ITEM__CREATED_AT", columnList = "CREATED_AT")
		}
)
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends AbstractEntity {

	@Id
	@Column(name = "ITEM_ID", nullable = false)
	private Long itemId;
	@Column(name = "ITEM_NAME", nullable = false)
	private String itemName;
	@Column(name = "ITEM_PRICE", nullable = false)
	private Long itemPrice;
	@Column(name = "STOCK_QUANTITY", nullable = false)
	private Long stockQuantity;

	public void decreaseStockQuantity(final Long quantity) {
		if (this.stockQuantity - quantity < 0) {
			throw new SoldOutException();
		}
		this.stockQuantity -= quantity;
		log.info("상품({}) 현재고={}", itemId, stockQuantity);
	}

	@Builder
	public Item(
			@NonNull final Long itemId,
			@NonNull final String itemName,
			@NonNull final Long itemPrice,
			@NonNull final Long stockQuantity
	) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.stockQuantity = stockQuantity;
	}

}
