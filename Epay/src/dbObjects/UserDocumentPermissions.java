package dbObjects;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDocumentPermissions extends Entity {
	private long idUserDocumentPermissions;
	private long user;
	private long document;
	private boolean r;
	private boolean w;
	private boolean x;
	
	public UserDocumentPermissions(long idUserDocumentPermissions, long user,
			long document, boolean r, boolean w, boolean x) {
		super();
		this.idUserDocumentPermissions = idUserDocumentPermissions;
		this.user = user;
		this.document = document;
		this.r = r;
		this.w = w;
		this.x = x;
	}

	public UserDocumentPermissions(long idUserDocumentPermissions) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		super();
		setParamsById(idUserDocumentPermissions);
	}

	public UserDocumentPermissions(long user, long document) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		super();
		this.user = user;
		this.document = document;
		setParamsByUserDoc(user, document);
	}
	
	public void setParamsByUserDoc(long user, long doc) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call getPermissionsByUserDoc(?, ?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("user", user);
		st.setLong("doc", doc);
		st.execute();
		ResultSet rs = st.getResultSet();
		
		while(rs.next()) {
			idUserDocumentPermissions = rs.getLong("idUserDocumentPermissions");
			user = rs.getLong("user");
			document = rs.getLong("document");
			r = rs.getBoolean("r");
			w = rs.getBoolean("w");
			x = rs.getBoolean("x");
		}
		conn.close();
	}
	
	public void setParamsById(long id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call getPermissionsById(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setLong("id", id);
		st.execute();
		ResultSet rs = st.getResultSet();
		
		while(rs.next()) {
			idUserDocumentPermissions = rs.getLong("idUserDocumentPermissions");
			user = rs.getLong("user");
			document = rs.getLong("document");
			r = rs.getBoolean("r");
			w = rs.getBoolean("w");
			x = rs.getBoolean("x");
		}
		conn.close();
	}
	
	@Override
	public String toString() {
		return idUserDocumentPermissions + "\t" + user + "\t" + document + "\t" + r + "\t" + w + "\t" + x;
	}

}
