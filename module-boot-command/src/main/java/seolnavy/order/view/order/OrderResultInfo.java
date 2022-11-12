package seolnavy.order.view.order;

import java.util.List;
import seolnavy.order.domain.order.OrderInfo.Main;
import seolnavy.order.domain.order.OrderInfo.OrderItem;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderResultInfo {

	private final Main order;

	public void print() {
		final var orderItems = order.getOrderItems();
		System.out.println("\n");
		System.out.println("주문 내역:");
		System.out.println("------------------------------------------------------------------------");
		printOrderItems(orderItems);
		System.out.println("------------------------------------------------------------------------");
		printTotalOrderPrice(order);
		System.out.println("------------------------------------------------------------------------");
		printPaymentAmount(order);
		System.out.println("------------------------------------------------------------------------");
		System.out.println("\n");
	}

	private void printPaymentAmount(final Main order) {
		final var paymentAmount = order.getTotalItemPrice() + order.getDeliveryPrice();
		System.out.printf("지불금액: %,d원\n", paymentAmount);
	}

	private void printTotalOrderPrice(final Main order) {
		final var totalItemPrice = order.getTotalItemPrice();
		System.out.printf("주문금액: %,d원\n", totalItemPrice);
		final var deliveryPrice = order.getDeliveryPrice();
		if (deliveryPrice > 0) {
			System.out.printf("배송비: %,d원\n", deliveryPrice);
		}
	}

	private static void printOrderItems(final List<OrderItem> orderItems) {
		for (final OrderItem orderItem : orderItems) {
			System.out.printf("%s - %s개\n", orderItem.getItemName(), orderItem.getOrderQuantity());
		}
	}
}
