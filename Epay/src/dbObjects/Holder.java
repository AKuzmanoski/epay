package dbObjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public final class Holder {
	public static Connection getConnection() throws SQLException, InstantiationException, 
	IllegalAccessException, ClassNotFoundException, IOException {
		// passwords shouldn't be hard-coded into the code, a better practice is to read them from file
		Map<String, String> env = System.getenv();
		String passLoc =env.get("CATALINA_HOME") + "/webapps/data/dbpass";
		BufferedReader br = new BufferedReader(new FileReader(passLoc));
		String pass = br.readLine();
		br.close();
		String userDB = "root";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/epayFULL", userDB, pass);
		return conn;
	}
	
	
	
	public	 static ResultSet getResultSet(String sql) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		Connection conn = getConnection();
		Statement st = conn.createStatement();
		return st.executeQuery(sql);
	}
}
