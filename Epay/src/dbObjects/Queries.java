package dbObjects;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import security.PasswordHash;

public class Queries {
	
	public static boolean userAuthentication(String username, String enteredPass) throws NoSuchAlgorithmException, InvalidKeySpecException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call getHashedPasswordForUser(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setString("username", username);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		resultSet.next();
		String hashedPass = resultSet.getString("password");
		conn.close();
		return PasswordHash.validatePassword(enteredPass, hashedPass);
	}
//	
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
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static long insertNewUser(String username, String password, String fullname,
		String email, String contact, Date dateOfBirth, String address, boolean isIndividual, String embg,
		String cardnum, double balance, double limit, String bank) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		Connection conn = Holder.getConnection();
		String sql = "{call insertNewUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
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
		st.setDouble("balance", balance);
		st.setDouble("lim", limit);
		st.setString("bank", bank);
		st.execute();
		ResultSet rs = st.getResultSet();
		rs.next();
		long r = rs.getLong("newId");
		conn.close();
		return r;
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
		conn.close();
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
	public static long insertNewAccount(String cardnum, Date datefrom, Date dateto, 
			double balance, double limit, String bank) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
			Connection conn = Holder.getConnection();
			String sql = "{call insertNewAccount(?, ?, ?, ?, ?, ?)}";
			CallableStatement st = conn.prepareCall(sql);
			st.setString("cardnumber", cardnum);
			st.setDate("datefrom", datefrom);
			st.setDate("dateto", dateto);
			st.setDouble("balance", balance);
			st.setDouble("lim", limit);
			st.setString("bank", bank);
			st.execute();
			ResultSet rs = st.getResultSet();
			long newId = 0;
			while(rs.next()) {
				newId = rs.getLong("newId");
			}
			conn.close();
			return newId;
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
		conn.close();
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
		conn.close();
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
		conn.close();
		return existence == 1;
	}

	/**
	 * 
	 * @param sender
	 * @param receiver
	 * @return id of the newly inserted invoice
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static long insertNewInvoice(long sender, long receiver) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call insertNewInvoice(?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("send", sender);
		st.setLong("rec", receiver);
		st.execute();
		
		ResultSet rs = st.getResultSet();
		long newId = 0;
		while(rs.next()) {
			newId = rs.getLong("newId");
		}
		conn.close();
		return newId;
	}

	/**
	 * 
	 * @param invoiceid
	 * @param paycheckid
	 * @return id of the newly inserted invoice_paycheck
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static long insertNewInvoicePaycheck(long invoiceid, long paycheckid) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call insertNewInvoicePaycheck(?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("invoiceid", invoiceid);
		st.setLong("paycheckid", paycheckid);
		st.execute();
		
		ResultSet rs = st.getResultSet();
		long newId = 0;
		while(rs.next()) {
			newId = rs.getLong("newId");
		}
		conn.close();
		return newId;
	}
	
	/**
	 * deletes the document with the specified docId
	 * @param docId
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void deleteDocument(long docId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call deleteDocument(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("docId", docId);
		st.execute();
		conn.close();
	}
	
	/**
	 * updates the URL and Description of the document
	 * @param docId
	 * @param newDesc
	 * @param newUrl
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void updateDocumentUrlDescription(long docId, String newDesc, String newUrl) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call updateDocumentUrlDescription(?, ?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("docId", docId);
		st.setString("newDesc", newDesc);
		st.setString("newUrl", newUrl);
		st.execute();
		conn.close();
	}
	
	public static List<User> getAllUsers() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		List<User> allUsers = new ArrayList<User>();
		
		Connection conn = Holder.getConnection();
		String sql = "{call getAllUsers()}";
		CallableStatement st = conn.prepareCall(sql);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		while(resultSet.next()) {
			allUsers.add(new User(resultSet.getLong("iduser"), resultSet.getString("username"),
					resultSet.getString("password"), resultSet.getString("fullname"),
					resultSet.getString("email"), resultSet.getString("contact"),
					resultSet.getDate("dateofbirth"), resultSet.getString("address"), 
					resultSet.getBoolean("isindividual"), resultSet.getString("embg")));
		}
		conn.close();
		return allUsers;
	}
	
	public static long insertNewPaycheck(long accountFrom, long accountTo,
			double amount, String description, String receiverName, boolean isPaid) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call insertNewPaycheck(?, ?, ?, ?, ?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("accFrom", accountFrom);
		st.setLong("accTo", accountTo);
		st.setDouble("amount", amount);
		st.setString("description", description);
		st.setString("receiverName", receiverName);
		st.setBoolean("isPaid", isPaid);
		st.execute();
		
		ResultSet rs = st.getResultSet();
		long newId = 0;
		while(rs.next()) {
			newId = rs.getLong("newId");
		}
		conn.close();
		return newId;
	}
	
	public static long insertNewDocument(long invoice, String title, String description, 
			String url, String content_type) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call insertNewDocument(?, ?, ?, ?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("invoice", invoice);
		st.setString("title", title);
		st.setString("description", description);
		st.setString("url", url);
		st.setString("content_type", content_type);
		st.execute();
		
		ResultSet rs = st.getResultSet();
		long newId = 0;
		while(rs.next()) {
			newId = rs.getLong("newId");
		}
		conn.close();
		return newId;
	}
	
	public static long insertNewUserDocumentPermissions(long user, long doc, boolean r, boolean w, boolean x) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call insertNewUserDocumentPermissions(?, ?, ?, ?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("user", user);
		st.setLong("doc", doc);
		st.setBoolean("r", r);
		st.setBoolean("w", w);
		st.setBoolean("x", x);
		st.execute();
		
		ResultSet rs = st.getResultSet();
		long newId = 0;
		while(rs.next()) {
			newId = rs.getLong("newId");
		}
		conn.close();
		return newId;
	}
	
	public static void updatePermissionById(long id, boolean r, boolean w, boolean x) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call updatePermissionsById(?, ?, ?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("id", id);
		st.setBoolean("r", r);
		st.setBoolean("w", w);
		st.setBoolean("x", x);
		st.execute();
		conn.close();
	}
	
	public static void updatePermissionsByUserDoc(long user, long doc, boolean r, boolean w, boolean x) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call updatePermissionsByUserDoc(?, ?, ?, ?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("user", user);
		st.setLong("doc", doc);
		st.setBoolean("r", r);
		st.setBoolean("w", w);
		st.setBoolean("x", x);
		st.execute();
		conn.close();
	}
	
	public static long insertNewUserAccount(long user, long account, Date date) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call insertNewUserAccountByIds(?, ?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("user", user);
		st.setLong("account", account);
		st.setDate("datecreated", date);
		st.execute();
		
		ResultSet rs = st.getResultSet();
		long newId = 0;
		while(rs.next()) {
			newId = rs.getLong("newId");
		}
		conn.close();
		return newId;
	}
	
	public static List<Paycheck> getReceivedPaychecksByAccountAndInvoice(long accTo, long invId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		List<Paycheck> paychecks = new ArrayList<Paycheck>();
		
		Connection conn = Holder.getConnection();
		String sql = "{call getReceivedPaychecksByInvoice(?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("accTo", accTo);
		st.setLong("invId", invId);
		st.execute();
		ResultSet rs = st.getResultSet();
		
		while(rs.next()) {
			paychecks.add(new Paycheck(rs.getLong("idpaycheck"), rs.getLong("accountFrom"),
						rs.getLong("accountTo"), rs.getDouble("amount"), rs.getString("description"),
						rs.getString("receiverName"), rs.getBoolean("isPaid")));
		}
		conn.close();
		return paychecks;
	}
	
	public static List<Paycheck> getSentPaychecksByAcccountAndInvoice(long accFrom, long invId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		List<Paycheck> paychecks = new ArrayList<Paycheck>();
		
		Connection conn = Holder.getConnection();
		String sql = "{call getSentPaychecksByInvoice(?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("accFrom", accFrom);
		st.setLong("invId", invId);
		st.execute();
		ResultSet rs = st.getResultSet();
		
		while(rs.next()) {
			paychecks.add(new Paycheck(rs.getLong("idpaycheck"), rs.getLong("accountFrom"),
						rs.getLong("accountTo"), rs.getDouble("amount"), rs.getString("description"),
						rs.getString("receiverName"), rs.getBoolean("isPaid")));
		}
		conn.close();
		return paychecks;
	}
	
	public static List<Paycheck> getReceivedPaychecksByAccount(long accTo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		List<Paycheck> paychecks = new ArrayList<Paycheck>();
		
		Connection conn = Holder.getConnection();
		String sql = "{call getReceivedPaychecksByAccount(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("accTo", accTo);
		st.execute();
		ResultSet rs = st.getResultSet();
		
		while(rs.next()) {
			paychecks.add(new Paycheck(rs.getLong("idpaycheck"), rs.getLong("accountFrom"),
						rs.getLong("accountTo"), rs.getDouble("amount"), rs.getString("description"),
						rs.getString("receiverName"), rs.getBoolean("isPaid")));
		}
		conn.close();
		return paychecks;
	}
	
	public static List<Paycheck> getSentPaychecksByAccount(long accFrom) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		List<Paycheck> paychecks = new ArrayList<Paycheck>();
		
		Connection conn = Holder.getConnection();
		String sql = "{call getSentPaychecksByAccount(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("accFrom", accFrom);
		st.execute();
		ResultSet rs = st.getResultSet();
		
		while(rs.next()) {
			paychecks.add(new Paycheck(rs.getLong("idpaycheck"), rs.getLong("accountFrom"),
						rs.getLong("accountTo"), rs.getDouble("amount"), rs.getString("description"),
						rs.getString("receiverName"), rs.getBoolean("isPaid")));
		}
		conn.close();
		return paychecks;
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
//		updatePermissionsByUserDoc(1, 3, false, true, true);
//		System.out.println(new UserDocumentPermissions(1, 3));
//		System.out.println(insertNewUserAccount(1, 8, new Date(0)));
//		Paycheck p = new Paycheck(10);
//		System.out.println(p);
//		System.out.println(isAccountNotInOwnership("348"));
		List<Paycheck> l1 = getReceivedPaychecksByAccount(1001), 
					   l2 = getReceivedPaychecksByAccountAndInvoice(1001, 9), 
					   l3 = getSentPaychecksByAccount(1001),
					   l4 = getSentPaychecksByAcccountAndInvoice(1001, 9);
		System.out.println(l1.size());
		System.out.println(l2.size());
		System.out.println(l3.size());
		System.out.println(l4.size());
		for(Paycheck p : l1) {
			System.out.println(p);
		}
		System.out.println("==================");
		
		for(Paycheck p : l2) {
			System.out.println(p);
		}
		System.out.println("==================");
		
		for(Paycheck p : l3) {
			System.out.println(p);
		}
		System.out.println("==================");
		
		
		for(Paycheck p : l4) {
			System.out.println(p);
		}
		System.out.println("==================");
	}
}


