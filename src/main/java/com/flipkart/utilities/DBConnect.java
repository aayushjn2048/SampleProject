/**
 * 
 */
package com.flipkart.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * @author HP
 *
 */
public class DBConnect {
	private static Logger logger = Logger.getLogger(DBConnect.class);

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/sampledb";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "aayushjn0248";

	/**
	 * Method to Establish the connection
	 * 
	 * @return connection object
	 */
	public static Connection connectDB() {

		// Declare the Connection or prepared statement variable here
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);

			// Step 4 make/open a connection

			// System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			return conn;

		} catch (SQLException se) {
			// Handle errors for JDBC
			logger.error("Exception raised" + se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			logger.error("Exception raised" + e.getMessage());
		} finally {
			//finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				logger.error("Exception raised" + se2.getMessage());
			} // nothing we can d
		} // end try
		System.out.println("Goodbye!");
		return null;
	}// end main
}
