package dbObjects;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Queries {
	
//	in username varchar(45),
//	in password varchar(45),
//	in fullname varchar(200),
//	in email varchar(45),
//	in contact varchar(15),
//	in dateofbirth datetime,
//	in address varchar(200),
//	in isindividual bit,
//	in embg varchar(25))
	
	/**
	 * Inserts new user, all nullable values should explicitly set as null 
	 * 					(not a problem, because will be received as null from the text boxes)
	 * @param username
	 * @param password
	 * @param fullname
	 * @param email
	 * @param contact
	 * @param dateOfBirth
	 * @param address
	 * @param isIndividual
	 * @param embg
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void insertNewUser(String username, String password, String fullname,
		String email, String contact, Date dateOfBirth, String address, boolean isIndividual, String embg,
		String cardnum) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call insertNewUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setString("username", username);
		st.setString("password", password);
		st.setString("fullname", fullname);
		st.setString("email", email);
		st.setString("contact", contact);
		st.setDate("dateofbirth", dateOfBirth);
		st.setString("address", address);
		st.setBoolean("isindividual", isIndividual);
		st.setString("embg", embg);
		st.setString("cardnumber", cardnum);
		st.execute();
	}

	
	/**
	 * Checks whether the Account has linked User.
	 * 
	 * @param cardnumber
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static boolean isAccountNotInOwnership(String cardnumber)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call checkAccountOwnership(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setString("cardnum", cardnumber);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		int existence = 0;
		while (resultSet.next()) {
			existence = resultSet.getInt("cnt");
		}
		return existence == 0;
	}
	
	
	/*
	 * inserting new account, datefrom is set to now() in case it is null,
	 * 						  dateto is set to datefrom + 30 days in case it was nul
	 * @param cardnum
	 * @param datefrom
	 * @param dateto
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void insertNewAccount(String cardnum, Date datefrom, Date dateto) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
			Connection conn = Holder.getConnection();
			String sql = "{call insertNewAccount(?, ?, ?)}";
			CallableStatement st = conn.prepareCall(sql);
			st.setString("cardnumber", cardnum);
			st.setDate("datefrom", datefrom);
			st.setDate("dateto", dateto);
			st.execute();
	}

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
		Connection conn = Holder.getConnection();
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
		Connection conn = Holder.getConnection();
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
		Connection conn = Holder.getConnection();
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

	

}
