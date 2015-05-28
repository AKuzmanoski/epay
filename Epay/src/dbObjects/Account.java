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
	
	public Account(String cardNumber) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		this.cardNumber = cardNumber;
		setParams(cardNumber);
	}
	
	public Account(long id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		this.accountId = id;
		setParamsById(id);
	}
	
	public Account(long accountId, String cardNumber, Date dateFrom, Date dateTo) {
		super();
		this.accountId = accountId;
		this.cardNumber = cardNumber;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
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
		}
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
		}
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
		String sql = "{call getPaychecksById(?)}";
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
	public boolean tryToPay(long accountTo, double amount, String description, String receiverName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call paying(?, ?, ?, ?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("accFrom", accountId);
		st.setLong("accTo", accountTo);
		st.setDouble("amount", amount);
		st.setString("description", description);
		st.setString("receiverName", receiverName);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		resultSet.next();
		return resultSet.getInt("isSuccesful") == 1;
	}

	@Override
	public String toString() {
		return accountId + "\t" + cardNumber + "\t" + dateFrom + "\t" + dateTo;
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
	
	
	
}
