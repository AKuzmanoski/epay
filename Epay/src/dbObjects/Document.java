package dbObjects;

public class Document {
	private long idDocument;
	private long invoice;
	private String title;
	private String description;
	private String url;
	
	public Document(long idDocument, long invoice, String title,
			String description, String url) {
		super();
		this.idDocument = idDocument;
		this.invoice = invoice;
		this.title = title;
		this.description = description;
		this.url = url;
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
	
	
}
