package org.ttang.fluent.reflection;

public class NoSuchPropertyException extends Exception {

	private static final long serialVersionUID = 880684675279013520L;

	public NoSuchPropertyException() {
		super();
	}

	public NoSuchPropertyException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoSuchPropertyException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchPropertyException(String message) {
		super(message);
	}

	public NoSuchPropertyException(Throwable cause) {
		super(cause);
	}
}
