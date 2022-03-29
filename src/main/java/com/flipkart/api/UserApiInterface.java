/**
 * 
 */
package com.flipkart.api;

import java.util.ArrayList;
import java.util.HashMap;

import com.flipkart.bean.Payment;
import com.flipkart.bean.User;
import com.flipkart.entity.UserEntity;
import com.flipkart.exceptions.SelfPaymentException;

/**
 * @author HP
 *
 */
public interface UserApiInterface {
	public Boolean registerUser(User user);
	public Boolean loginUser(String username, String password);
	public ArrayList<HashMap<String, String>> viewUsersList();
	public Boolean makePayment(Payment transaction) throws SelfPaymentException;
}
