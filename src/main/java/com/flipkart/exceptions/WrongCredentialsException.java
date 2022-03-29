/**
 * 
 */
package com.flipkart.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author HP
 *
 */

public class WrongCredentialsException extends Exception{

	public WrongCredentialsException(String message) {
		super(message);
	}

}
