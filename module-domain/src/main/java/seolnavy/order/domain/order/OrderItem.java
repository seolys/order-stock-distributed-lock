package seolnavy.order.domain.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import seolnavy.order.domain.AbstractEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@Table(name = "ORDERS_ITEM")
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItem extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ITEM_ID", nullable = false)
	private Long orderItemId;

	@ManyToOne
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private Order order;

	@Column(name = "ITEM_ID", nullable = false)
	private Long itemId;

	@Column(name = "ITEM_NAME", nullable = false)
	private String itemName;
	@Column(name = "ITEM_PRICE", nullable = false)
	private Long itemPrice;

	@Column(name = "ORDER_QUANTITY", nullable = false)
	private Long orderQuantity;

	public static OrderItem of(
			@NonNull final Order order,
			@NonNull final Long itemId,
			@NonNull final String itemName,
			@NonNull final Long itemPrice,
			@NonNull final Long orderQuantity
	) {
		final var orderItem = new OrderItem(null, order, itemId, itemName, itemPrice, orderQuantity);
		order.addOrderItem(orderItem);
		return orderItem;
	}

	public long getOrderPrice() {
		return itemPrice * orderQuantity;
	}
}
