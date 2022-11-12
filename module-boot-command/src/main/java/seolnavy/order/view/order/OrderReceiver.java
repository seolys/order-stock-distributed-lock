package seolnavy.order.view.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import seolnavy.order.domain.item.ItemInfo.Main;
import seolnavy.order.view.order.OrderDto.ReceivedOrderItem;
import seolnavy.order.view.order.exception.OrderItemChoiceComplete;

public class OrderReceiver {

	private final Scanner scan = new Scanner(System.in);

	public List<ReceivedOrderItem> receiveOrderItem(final List<Main> orderAbleItems) {
		final var orderAbleItemMap = orderAbleItems.stream()
				.collect(Collectors.toMap(Main::getItemId, Function.identity()));

		final List<ReceivedOrderItem> orderItems = new ArrayList();
		try {
			while (true) {
				final var orderItem = receiveOrderItem(orderAbleItemMap);
				orderItems.add(orderItem);
			}
		} catch (final OrderItemChoiceComplete e) {
			return orderItems;
		}
	}

	private ReceivedOrderItem receiveOrderItem(final Map<Long, Main> orderAbleItemMap) {
		final var item = inputItemIds(orderAbleItemMap);
		final var orderQuantity = inputOrderQuantity();
		return ReceivedOrderItem.of(item.getItemId(), item.getItemName(), item.getItemPrice(), orderQuantity);
	}

	private long inputOrderQuantity() {
		while (true) {
			System.out.print("주문수량 : ");
			try {
				final var orderQuantity = Long.parseLong(scan.nextLine());
				if (orderQuantity <= 0) {
					System.out.println("수량을 다시 확인해주세요.");
					continue;
				}
				return orderQuantity;
			} catch (final Exception e) {
				System.out.println("수량은 숫자만 입력 가능합니다.");
			}
		}
	}

	private Main inputItemIds(final Map<Long, Main> orderAbleItemMap) {
		while (true) {
			System.out.print("상품번호 : ");
			final String inputItemId = scan.nextLine();
			if (isOrderComplete(inputItemId)) {
				throw new OrderItemChoiceComplete();
			}

			try {
				final var itemId = Long.parseLong(inputItemId);
				if (!validItemId(orderAbleItemMap, itemId)) {
					System.out.println("상품번호를 다시 입력해주세요.");
					continue;
				}
				return orderAbleItemMap.get(itemId);
			} catch (final Exception e) {
				System.out.println("상품번호를 다시 입력해주세요.");
			}
		}
	}

	private boolean validItemId(final Map<Long, Main> orderAbleItemMap, final Long itemId) {
		return orderAbleItemMap.containsKey(itemId);
	}

	private boolean isOrderComplete(final String itemId) {
		return "".equals(itemId.trim());
	}

}
