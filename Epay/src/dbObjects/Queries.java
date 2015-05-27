package dbObjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {

	/**
	 * 
	 * @param username the username we want to check if already has been taken by some of the existing users
	 * @return true if there is no such user, false if the username is already taken
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static boolean isUsernameFree(String username) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		Connection conn = getConnection();
		String sql = "{call checkUserExistence(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setString("username", username);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		int existence = 0;
		while(resultSet.next()) {
			existence = resultSet.getInt("cnt");
		}
		return existence == 0;
	}
	
	/**
	 * will be used to check if such an account already exists into the database
	 * @param cardnumber - the one used into the database
	 * @return true if the account with the corresponding cardnumber is free, false if it is already taken
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static boolean isAccountFree(String cardnumber) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call checkAccountExistence(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setString("cardnum", cardnumber);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		int existence = 0;
		while(resultSet.next()) {
			existence = resultSet.getInt("cnt");
		}
		return existence == 0;
	}
	
	/**
	 * 
	 * @param username
	 * @param cardnumber
	 * @return true if the the user is owner of the account with the corresponding cardnumber, false otherwise
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static boolean userAccountMatching(String username, String cardnumber) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call userAccountMatching(?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setString("username", username);
		st.setString("cardnum", cardnumber);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		int existence = 3;
		while(resultSet.next()) {
			existence = resultSet.getInt("cnt");
		}
		return existence == 1;
	}
	
	
	

	private static Connection getConnection() throws SQLException, InstantiationException, 
	IllegalAccessException, ClassNotFoundException, IOException {
		// passwords shouldn't be hard-coded into the code, a better practice is to read them from file
		String passLoc = "/home/goran/dbpass";
		BufferedReader br = new BufferedReader(new FileReader(passLoc));
		String pass = br.readLine();
		br.close();
		String userDB = "root";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/epayFULL", userDB, pass);
		return conn;
	}
	
	private static ResultSet getResultSet(String sql) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		Connection conn = getConnection();
		Statement st = conn.createStatement();
		return st.executeQuery(sql);
	}

}
