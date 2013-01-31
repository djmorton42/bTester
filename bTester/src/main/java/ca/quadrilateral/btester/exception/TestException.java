package ca.quadrilateral.btester.exception;

public class TestException extends RuntimeException {
	private static final long serialVersionUID = -9076166631752494899L;

	public TestException() {
		super();
	}

	public TestException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TestException(String message, Throwable cause) {
		super(message, cause);
	}

	public TestException(String message) {
		super(message);
	}

	public TestException(Throwable cause) {
		super(cause);
	}

}
