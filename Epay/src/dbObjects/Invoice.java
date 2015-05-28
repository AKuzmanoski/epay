package dbObjects;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Invoice extends Entity {
	private long idInvoice;
	private long sender;
	private long receiver;
	
	public Invoice(long idInvoice, long sender, long receiver) {
		super();
		this.idInvoice = idInvoice;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	/**
	 * 
	 * @return documents attached to this Invoice
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Document> getDocuments() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call getDocumentsForInvoice(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("invoice", idInvoice);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		List<Document> documents = new ArrayList<Document>();
		
		while(resultSet.next()) {
			documents.add(new Document(st.getLong("idDocuments"), st.getLong("invoice"), 
					st.getString("title"), st.getString("description"), st.getString("url")));
		}
		
		return documents;
	}

	public long getIdInvoice() {
		return idInvoice;
	}

	public void setIdInvoice(long idInvoice) {
		this.idInvoice = idInvoice;
	}

	public long getSender() {
		return sender;
	}

	public void setSender(long sender) {
		this.sender = sender;
	}

	public long getReceiver() {
		return receiver;
	}

	public void setReceiver(long receiver) {
		this.receiver = receiver;
	}
	
	
}
