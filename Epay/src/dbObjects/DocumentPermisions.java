package dbObjects;

public class DocumentPermisions {
	Document document;
	boolean r1;
	boolean w1;
	boolean x1;
	boolean r2;
	boolean w2;
	boolean x2;

	public DocumentPermisions(Document document, boolean r1, boolean w1,
			boolean x1, boolean r2, boolean w2, boolean x2) {
		this.document = document;
		this.r1 = r1;
		this.w1 = w1;
		this.x1 = x1;
		this.r2 = r2;
		this.w2 = w2;
		this.x2 = x2;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public boolean isR1() {
		return r1;
	}

	public void setR1(boolean r1) {
		this.r1 = r1;
	}

	public boolean isW1() {
		return w1;
	}

	public void setW1(boolean w1) {
		this.w1 = w1;
	}

	public boolean isX1() {
		return x1;
	}

	public void setX1(boolean x1) {
		this.x1 = x1;
	}

	public boolean isR2() {
		return r2;
	}

	public void setR2(boolean r2) {
		this.r2 = r2;
	}

	public boolean isW2() {
		return w2;
	}

	public void setW2(boolean w2) {
		this.w2 = w2;
	}

	public boolean isX2() {
		return x2;
	}

	public void setX2(boolean x2) {
		this.x2 = x2;
	}
}
