package dbObjects;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Account extends Entity {
	private long accountId;
	private String cardNumber;
	private Date dateFrom;
	private Date dateTo;
	private double balance;
	private double limit;
	private String bank;
	
	public Account(String cardNumber) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		this.cardNumber = cardNumber;
		setParams(cardNumber);
	}
	
	public Account(long id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		this.accountId = id;
		setParamsById(id);
	}
	
	

	public Account(long accountId, String cardNumber, Date dateFrom,
			Date dateTo, double balance, double limit, String bank) {
		super();
		this.accountId = accountId;
		this.cardNumber = cardNumber;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.balance = balance;
		this.limit = limit;
		this.bank = bank;
	}

	public void setParamsById(long id) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		Connection conn = getConnection();
		String sql = "{call getAccountById(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("id", id);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		while(resultSet.next()) {
			cardNumber = resultSet.getString("cardnumber");
			dateFrom = resultSet.getDate("dateFrom");
			dateTo = resultSet.getDate("dateto");
			balance = resultSet.getDouble("balance");
			limit = resultSet.getDouble("limit");
			bank = resultSet.getString("bank");
		}
		conn.close();
	}

	public void setParams(String cardnumber) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call getAccountByCardnumber(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setString("cardnumber", cardnumber);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		while(resultSet.next()) {
			accountId = resultSet.getLong("accountId");
			dateFrom = resultSet.getDate("dateFrom");
			dateTo = resultSet.getDate("dateto");
			balance = resultSet.getDouble("balance");
			limit = resultSet.getDouble("limit");
			bank = resultSet.getString("bank");
		}
		conn.close();
	}
	
	/**
	 * 
	 * @return paychecks in which this account was referenced by the accountFrom foreign key
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Paycheck> getSentPaychecks() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call paidPaychecks(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("accountid", accountId);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		List<Paycheck> paychecks = new ArrayList<Paycheck>();

		while(resultSet.next()) {
			paychecks.add(new Paycheck(resultSet.getLong("idpaycheck"), resultSet.getLong("accountFrom"),
					resultSet.getLong("accountTo"), resultSet.getDouble("amount"), 
					resultSet.getString("description"), resultSet.getString("receiverName")));
		}
		conn.close();
		return paychecks;
	}
	
	/**
	 * 
	 * @return paychecks in which this account was referenced by the accountTo foreign key
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Paycheck> getReceivedPaychecks() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call receivedPaychecks(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("accountid", accountId);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		List<Paycheck> paychecks = new ArrayList<Paycheck>();

		while(resultSet.next()) {
			paychecks.add(new Paycheck(resultSet.getLong("idpaycheck"), resultSet.getLong("accountFrom"),
					resultSet.getLong("accountTo"), resultSet.getDouble("amount"), 
					resultSet.getString("description"), resultSet.getString("receiverName")));
		}
		conn.close();
		return paychecks;
	}
	
	/**
	 * 
	 * @param accountTo
	 * @param amount
	 * @param description
	 * @param receiverName
	 * @return true if the paying has been successful, false if not succesful (the limit would be crossed)
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public boolean tryToPay(long id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call paying(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("id", id);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		resultSet.next();
		boolean ok =  resultSet.getInt("isSuccesful") == 1;
		conn.close();
		return ok;
	}
	
	public User accountOwner() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call getAccountOwner(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("accountId", accountId);
		st.execute();
		ResultSet rs = st.getResultSet();
		rs.next();
		User u = new User(rs.getLong("iduser"), rs.getString("username"), rs.getString("password"),
				rs.getString("fullname"), rs.getString("email"), rs.getString("contact"),
				rs.getDate("dateOfBirth"), rs.getString("address"), rs.getBoolean("isindividual"), 
				rs.getString("embg"));
		conn.close();
		return u;
	}

	@Override
	public String toString() {
		return accountId + "\t" + cardNumber + "\t" + dateFrom + "\t" + dateTo + "\t" + balance + "\t" + limit + "\t" + bank;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	
}
