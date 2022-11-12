package seolnavy.order.common.exception;

public class SoldOutException extends BaseException {

	public SoldOutException() {
		super("주문한 상품량이 재고량보다 큽니다.");
	}

}
