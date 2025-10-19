package com.alpha.ABCLogistics.Exception;

public class AddressNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AddressNotFoundException(String meassage) {
		super(meassage);
	}

	public AddressNotFoundException() {
		super();
	}

}
