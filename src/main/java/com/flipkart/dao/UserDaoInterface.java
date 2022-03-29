package com.flipkart.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.flipkart.bean.Payment;
import com.flipkart.bean.User;
import com.flipkart.entity.UserEntity;


public interface UserDaoInterface {
	public Boolean registerUser(User user);
	public Boolean loginUser(String username, String password);
	public ArrayList<HashMap<String, String>> viewUsersList();
	public Boolean makePayment(Payment transaction);
}
