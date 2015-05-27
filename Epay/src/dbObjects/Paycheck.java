package dbObjects;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Paycheck extends Entity{
	   private long idPaycheck;
	   private long accountFrom;
	   private long accountTo;
	   private double amount;
	   private String description;
	   private String receiverName;
	   
   public Paycheck(long idPaycheck, long accountFrom, long accountTo,
			double amount, String description, String receiverName) {
		super();
		this.idPaycheck = idPaycheck;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
		this.description = description;
		this.receiverName = receiverName;
	}
   public Paycheck(long idPaycheck) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
	super();
	this.idPaycheck = idPaycheck;
	setPaycheckById(idPaycheck);
}
private void setPaycheckById(long idPaycheck) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		ResultSet resultSet = getResultSet("SELECT * FROM paycheck WHERE idpaycheck = " + idPaycheck);
		while(resultSet.next()) {
			System.out.println(resultSet.getLong("idpaycheck"));
			accountFrom = resultSet.getLong("accountFrom");
			accountTo = resultSet.getLong("accountTo");
			amount = resultSet.getDouble("amount");
			description = resultSet.getString("description");
			receiverName = resultSet.getString("receiverName");
		}
	}
	

public long getIdPaycheck() {
	return idPaycheck;
}

public void setIdPaycheck(long idPaycheck) {
	this.idPaycheck = idPaycheck;
}

public long getAccountFrom() {
	return accountFrom;
}

public void setAccountFrom(long accountFrom) {
	this.accountFrom = accountFrom;
}

public long getAccountTo() {
	return accountTo;
}

public void setAccountTo(long accountTo) {
	this.accountTo = accountTo;
}

public double getAmount() {
	return amount;
}

public void setAmount(double amount) {
	this.amount = amount;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getReceiverName() {
	return receiverName;
}

public void setReceiverName(String receiverName) {
	this.receiverName = receiverName;
}


	
    
}
