package com.flipkart.exceptions;

public class SelfPaymentException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SelfPaymentException()
	{
		System.out.println("Self payment not allowed");
	}
}
