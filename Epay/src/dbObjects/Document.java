package dbObjects;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Document {
	private long idDocument;
	private long invoice;
	private String title;
	private String description;
	private String url;
	private String content_type;
	
	public Document(long idDocument, long invoice, String title,
			String description, String url, String content_type) {
		super();
		this.idDocument = idDocument;
		this.invoice = invoice;
		this.title = title;
		this.description = description;
		this.url = url;
		this.content_type = content_type;
	}
	
	public Document(long idDoc) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		setParamsById(idDoc);
	}
	
	public void setParamsById(long idDoc) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		Connection conn = Holder.getConnection();
		String sql = "{call getDocumentById(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("id", idDoc);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
		while(resultSet.next()) {
			idDocument = resultSet.getLong("idDocuments");
			invoice = resultSet.getLong("invoice");
			title = resultSet.getString("title");
			description = resultSet.getString("description");
			url = resultSet.getString("url");
			content_type = resultSet.getString("content_type");
		}
	}

	public long getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(long idDocument) {
		this.idDocument = idDocument;
	}

	public long getInvoice() {
		return invoice;
	}

	public void setInvoice(long invoice) {
		this.invoice = invoice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	@Override
	public String toString() {
		return idDocument + "\t" + invoice + "\t" + title + "\t" + description + "\t" + url;
	}
	
}
