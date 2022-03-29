/**
 * 
 */
package com.flipkart.api;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.flipkart.bean.Payment;
import com.flipkart.bean.User;
import com.flipkart.dao.UserDaoInterface;
import com.flipkart.dao.UserDaoOperation;
import com.flipkart.dao.UserEntityDao;
import com.flipkart.entity.UserEntity;
import com.flipkart.exceptions.SelfPaymentException;
import com.flipkart.utilities.DBConnect;

/**
 * @author HP
 *
 */
public class UserApiImplementation implements UserApiInterface{
	
	Connection conn =  DBConnect.connectDB();

	public Boolean registerUser(User user) {
		UserDaoInterface dao = new UserDaoOperation();
		return dao.registerUser(user);
	}

	public Boolean loginUser(String username, String password) {
		UserDaoInterface dao = new UserDaoOperation();
		return dao.loginUser(username, password);
	}

	public ArrayList<HashMap<String,String>> viewUsersList() {
		UserDaoInterface dao = new UserDaoOperation();
		return dao.viewUsersList();
	}

	public Boolean makePayment(Payment transaction) throws SelfPaymentException {
		if(transaction.getPayerId() == transaction.getPayeeId())
		{
			throw new SelfPaymentException();
		}
		UserDaoInterface dao = new UserDaoOperation();
		return dao.makePayment(transaction);
	}

}
