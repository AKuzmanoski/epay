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
	
	
	
	public Account(long accountId, String cardNumber, Date dateFrom, Date dateTo) {
		super();
		this.accountId = accountId;
		this.cardNumber = cardNumber;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
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
	
	public List<Paycheck> getPaychecks() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call getPaychecksById(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setInt("accountid", (int)accountId);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		List<Paycheck> paycheks = new ArrayList<Paycheck>();
		
		
////		long idPaycheck, long accountFrom, long accountTo,
////		double amount, String description, String receiverName
//		while(resultSet.next()) {
//			paycheks.add(new Paycheck(resultSet.getLong(""), ))
//		}
		
		return null;
	}

	@Override
	public String toString() {
		return accountId + "\t" + cardNumber + "\t" + dateFrom + "\t" + dateTo;
	}
	
}
