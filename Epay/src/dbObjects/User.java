package dbObjects;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User extends Entity {
	private long idUser;
	private String userName;
	private String pass;
	private String fullName;
	private String email;
	private String contact;
	private Date dateOfBirth;
	private String address;
	private boolean isIndividual;
	private String embg;
//	private double money;
	
	
	
	public User(long idUser) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		super();
		this.idUser = idUser;
		setUserById(idUser);
	}
	
	
	public User(long idUser, String userName, String pass, String fullName,
		String email, String contact, Date dateOfBirth, String address,
		boolean isIndividual, String embg) {
		super();
		this.idUser = idUser;
		this.userName = userName;
		this.pass = pass;
		this.fullName = fullName;
		this.email = email;
		this.contact = contact;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.isIndividual = isIndividual;
		this.embg = embg;
	}


	private void setUserById(long idUser) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		Connection conn = getConnection();
		String sql = "{call getUserById(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("id", idUser);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		while(resultSet.next()) {
			userName = resultSet.getString("username");
			pass = resultSet.getString("password");
			fullName = resultSet.getString("fullName");
			email = resultSet.getString("email");
			contact = resultSet.getString("contact");
			dateOfBirth = resultSet.getDate("dateOfBirth");
			address = resultSet.getString("address");
			isIndividual = resultSet.getBoolean("isIndividual");
			embg = resultSet.getString("embg");
			
		}
		conn.close();
	}
	
	
	/**
	 * queries the database and puts sets all the parameters, using the entered username and pass
	 * @param userName
	 * @param pass
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public User(String userName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		super();
		this.userName = userName;
		setUserByUsername(userName);
	}

	/**
	 * sets all data for the used based on the username and pass entered
	 * @param username
	 * @param pass
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	private void setUserByUsername(String username) throws InstantiationException, 
	IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call getUserDataForHomePage(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setString("username", username);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		while(resultSet.next()) {
			idUser = resultSet.getInt("idUser");
			pass = resultSet.getString("password");
			fullName = resultSet.getString("fullName");
			email = resultSet.getString("email");
			contact = resultSet.getString("contact");
			dateOfBirth = resultSet.getDate("dateOfBirth");
			address = resultSet.getString("address");
			isIndividual = resultSet.getBoolean("isIndividual");
			embg = resultSet.getString("embg");
			
		}
		conn.close();
	}
	
	/**
	 * checks if the usernme is free
	 * @param username
	 * @return true for free username, false if username is already taken
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public boolean isUsernameFree(String username) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
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
		conn.close();
		return existence == 0;
	}
	
	/**
	 * 
	 * @return list of the cardnumbers of all user's accounts
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<String> getAccounts() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call getAccountsForUser(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setString("username", userName);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		List<String> accounts = new ArrayList<String>();
		
		while(resultSet.next()) {
			accounts.add(resultSet.getString("acc"));
		}
		conn.close();
		return accounts;
	}
	
	/**
	 * 
	 * @return map of all user's pairs accountId -> cardnumber
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Map<String, String> getAccountsCards() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call getAccountsForUser(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setString("username", userName);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		Map<String, String> accounts = new HashMap<String, String>();
		
		while(resultSet.next()) {
			accounts.put(resultSet.getString("acc"), resultSet.getString("card"));
		}
		conn.close();
		return accounts;
	}
	
	/**
	 * 
	 * @return list of all accounts for the user
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Account> getCompleteAccounts() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		List<Account> accounts = new ArrayList<Account>();
		
		Connection conn = getConnection();
		String sql = "{call getAccountsByUserId(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("userid", idUser);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		
		while(resultSet.next()) {
			accounts.add(new Account(resultSet.getLong("accountId"), resultSet.getString("cardnumber")
					, resultSet.getDate("datefrom"), resultSet.getDate("dateto"), 
					resultSet.getDouble("balance"), resultSet.getDouble("limit"), resultSet.getString("bank")));
		}
		conn.close();
		return accounts;
	}
	
	public List<Invoice> getSentInvoices() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		List<Invoice> invoices = new ArrayList<Invoice>();
		
		Connection conn = getConnection();
		String sql = "{call getSentInvoicesByUser(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setInt("iduser", (int)idUser);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		while(resultSet.next()) {
			invoices.add(new Invoice(resultSet.getInt("idinvoice"), resultSet.getInt("sender"), 
					resultSet.getInt("receiver")));
		}
		conn.close();
		return invoices;
	}
	
	public List<Invoice> getReceivedInvoices() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		List<Invoice> invoices = new ArrayList<Invoice>();
		
		Connection conn = getConnection();
		String sql = "{call getReceivedInvoicesByUser(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setInt("iduser", (int)idUser);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		while(resultSet.next()) {
			invoices.add(new Invoice(resultSet.getInt("idinvoice"), resultSet.getInt("sender"), 
					resultSet.getInt("receiver")));
		}
		conn.close();
		return invoices;
	}
	
	public List<Paycheck> getSentPaychecks() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		List<Paycheck> sent = new ArrayList<Paycheck>();
		
		Connection conn = getConnection();
		String sql = "{call getSentPaychecksWithoutInvoice(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setInt("iduser", (int)idUser);
		st.execute();
		ResultSet rs = st.getResultSet();
		
		while(rs.next()) {
			sent.add(new Paycheck(rs.getLong("idpaycheck"), rs.getLong("accountFrom"), rs.getLong("accountTo"),
					rs.getDouble("amount"), rs.getString("description"), rs.getString("receiverName")));
		}
		conn.close();
		return sent;
	}
	
	public List<Paycheck> getReceivedPaychecks() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		List<Paycheck> received = new ArrayList<Paycheck>();
		
		Connection conn = getConnection();
		String sql = "{call getReceivedPaychecksWithoutInvoice(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setInt("iduser", (int)idUser);
		st.execute();
		ResultSet rs = st.getResultSet();
		
		while(rs.next()) {
			received.add(new Paycheck(rs.getLong("idpaycheck"), rs.getLong("accountFrom"), rs.getLong("accountTo"),
					rs.getDouble("amount"), rs.getString("description"), rs.getString("receiverName")));
		}
		conn.close();
		return received;
	}
	
//	
//	public List<Paycheck> sendPaychecks() {
//		
//	}
//	
//	public List<Paycheck> receiveInvoices() {
//		
//	}
	
	
	@Override
	public String toString() {
		return userName + "\t" + fullName + "\t" + email + "\t" + dateOfBirth;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public boolean isIndividual() {
		return isIndividual;	
	}

	public void setIndividual(boolean isIndividual) {
		this.isIndividual = isIndividual;
	}

	public String getEmbg() {
		return embg;
	}

	public void setEmbg(String embg) {
		this.embg = embg;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean equals(Object obj) {
		User u = (User) obj;
		return u.userName.equals(userName);
	}
	
}


