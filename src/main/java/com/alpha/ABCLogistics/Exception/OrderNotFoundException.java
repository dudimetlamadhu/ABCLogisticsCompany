package com.alpha.ABCLogistics.Exception;

public class OrderNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(String meassage) {
	super(meassage);
	}

	public OrderNotFoundException() {
		// TODO Auto-generated constructor stub
	}

}
