package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.flipkart.api.UserApiImplementation;
import com.flipkart.bean.Payment;
import com.flipkart.bean.User;
import com.flipkart.utilities.DBConnect;

public class UserDaoOperation implements UserDaoInterface{
	private static Logger logger = Logger.getLogger(UserApiImplementation.class);
	Connection conn =  DBConnect.connectDB();

	public Boolean registerUser(User user) {
			
			try {
				PreparedStatement stmt = null;
				String sql = "SELECT * FROM user WHERE username = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, user.getUsername());
				ResultSet rs = stmt.executeQuery();
	            if(rs.next()) {
	                return false;
	            }
			}catch (Exception e) {
				// Handle errors for Class.forName
				logger.error("Exception raised" + e.getMessage());
			}
			try
			{
				PreparedStatement stmt = null;
				String sql = "INSERT INTO user(username,password,name,contactNo) values(?,?,?,?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());
				stmt.setString(3, user.getName());
				stmt.setString(4, user.getContactNo());
				int rs = stmt.executeUpdate();
				if (rs == 0)
					return false;
				return true;
			} catch (SQLException se) {
				// Handle errors for JDBC
				logger.error("Exception raised" + se.getMessage());
			}
			return false;
	}

	public Boolean loginUser(String username, String password) {
		try 
		{
			PreparedStatement stmt = null;
			String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				return true;
			}
		}
		catch(Exception e){
			logger.error("Exception raised"+e.getMessage());
		}
		return false;
	}

	public ArrayList<HashMap<String,String>> viewUsersList() {
		try 
		{
			PreparedStatement stmt = null;
			String sql = "SELECT * FROM user";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
			while(rs.next()){
				HashMap<String,String> hm = new HashMap<String, String>();
				hm.put("userId", String.valueOf(rs.getInt("id")));
				hm.put("username", rs.getString("username"));
				hm.put("name", rs.getString("name"));
				data.add(hm);
			}
			return data;
		}
		catch(Exception e){
			logger.error("Exception raised"+e.getMessage());
		}
		return null;
	}

	public Boolean makePayment(Payment transaction){
		try
		{
			PreparedStatement stmt = null;
			String sql = "INSERT INTO transactions(payerId,payeeId,amount) values(?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, transaction.getPayerId());
			stmt.setInt(2, transaction.getPayeeId());
			stmt.setInt(3, transaction.getAmount());
			int rs = stmt.executeUpdate();
			if (rs == 0)
				return false;
			return true;
		} catch (SQLException se) {
			// Handle errors for JDBC
			logger.error("Exception raised" + se.getMessage());
		}
		return false;
	}
}
