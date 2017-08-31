package com.btcwallet.exceptions;

public class BitcoinNotEnoughException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BitcoinNotEnoughException (String message) {
		super (message);
	}

	public BitcoinNotEnoughException (Throwable cause) {
		super (cause);
	}

	public BitcoinNotEnoughException (String message, Throwable cause) {
		super (message, cause);
	}

}
