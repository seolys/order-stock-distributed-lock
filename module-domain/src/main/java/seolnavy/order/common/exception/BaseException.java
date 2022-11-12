package seolnavy.order.common.exception;

public class BaseException extends RuntimeException {

	public BaseException() {
	}

	public BaseException(final String message) {
		super(message);
	}

	public BaseException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BaseException(final Throwable cause) {
		super(cause);
	}

}
