package org.ttang.fluent.reflection;

public class NoSuchValueException extends Exception {

	private static final long serialVersionUID = -6947382794290173735L;

	public NoSuchValueException() {
		super();
	}

	public NoSuchValueException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoSuchValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchValueException(String message) {
		super(message);
	}

	public NoSuchValueException(Throwable cause) {
		super(cause);
	}

}
