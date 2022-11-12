package seolnavy.order.domain.order;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import seolnavy.order.domain.AbstractEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@Table(name = "ORDERS")
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class Order extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID", nullable = false)
	private Long orderId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
	private List<OrderItem> orderItems = new ArrayList<>();

	@Column(name = "ORDERED_AT", nullable = false)
	private ZonedDateTime orderedAt;

	@Column(name = "DELIVERY_PRICE", nullable = false)
	private Long deliveryPrice;

	public static Order newOrder(final Long deliveryPrice) {
		return Order.of(null, new ArrayList<>(), ZonedDateTime.now(), deliveryPrice);
	}

	public void addOrderItem(final OrderItem orderItem) {
		orderItems.add(orderItem);
	}

	public long getTotalItemPrice() {
		return orderItems.stream()
				.mapToLong(OrderItem::getOrderPrice)
				.sum();
	}
}
