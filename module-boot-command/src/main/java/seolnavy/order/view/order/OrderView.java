package seolnavy.order.view.order;

import seolnavy.order.application.ItemFacade;
import seolnavy.order.application.OrderFacade;
import seolnavy.order.common.exception.SoldOutException;
import seolnavy.order.domain.order.OrderInfo.OrderResult;
import seolnavy.order.view.View;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderView implements View {

	private final ItemFacade itemFacade;
	private final OrderFacade orderFacade;

	@Override
	public void show() {
		// 상품목록 조회 및 출력
		final var items = itemFacade.getAllItems();
		OrderDtoMapper.toOrderAbleItems(items).print();

		// 주문 받기
		final var receivedOrderItems = new OrderReceiver().receiveOrderItem(items);
		if (receivedOrderItems.isEmpty()) { // 주문가능 상품이 없음.
			return;
		}

		// 주문하기
		final OrderResult orderResult;
		try {
			final var orderCommand = OrderDtoMapper.toRegisterOrder(receivedOrderItems);
			orderResult = orderFacade.registerOrder(orderCommand);
		} catch (final SoldOutException e) {
			System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
			return;
		}

		// 주문결과 출력
		final var order = orderFacade.retrieveOrder(orderResult.getOrderId());
		new OrderResultInfo(order).print();
	}

}
