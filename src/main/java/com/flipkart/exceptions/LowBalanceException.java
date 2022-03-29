/**
 * 
 */
package com.flipkart.exceptions;

/**
 * @author HP
 *
 */
public class LowBalanceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2129515284480503795L;
	public LowBalanceException(String message)
	{
		super(message);
	}
}
