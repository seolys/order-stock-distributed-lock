package seolnavy.order.domain.order;

import java.util.stream.Collectors;
import seolnavy.order.domain.order.OrderInfo.Main;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class OrderInfoMapper {

	public static Main of(final Order order) {
		final var orderItems = order.getOrderItems().stream()
				.map(OrderInfoMapper::of)
				.collect(Collectors.toList());
		return OrderInfo.Main.of(order.getOrderId(), order.getOrderedAt(), order.getTotalItemPrice(), order.getDeliveryPrice(), orderItems);
	}

	public static OrderInfo.OrderItem of(final OrderItem orderItem) {
		return OrderInfo.OrderItem.of(
				orderItem.getOrderItemId(),
				orderItem.getOrder(),
				orderItem.getItemName(),
				orderItem.getItemPrice(),
				orderItem.getOrderQuantity());
	}
}
