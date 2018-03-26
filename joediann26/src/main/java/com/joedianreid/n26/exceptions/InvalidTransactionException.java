package com.joedianreid.n26.exceptions;

public class InvalidTransactionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTransactionException() {
	}

	public InvalidTransactionException(String arg0) {
		super(arg0);
	}

	public InvalidTransactionException(Throwable arg0) {
		super(arg0);
	}

	public InvalidTransactionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidTransactionException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
