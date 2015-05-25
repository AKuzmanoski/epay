package dbObjects;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class User extends Entity {
	private long idUser;
	private String userName;
	private String pass;
	private String fullName;
	private String email;
	private Date dateOfBirth;
	private String address;
	private double money;
	
	public User(long idUser, String userName, String pass, String fullName,
			String email, Date dateOfBirth, String address, double money) {
		super();
		this.idUser = idUser;
		this.userName = userName;
		this.pass = pass;
		this.fullName = fullName;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.money = money;
	}

	public User(long idUser) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		super();
		this.idUser = idUser;
		setUserById(idUser);
	}
	
	private void setUserById(long idUser) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		ResultSet resultSet = getResultSet("SELECT * FROM User WHERE idUser = " + idUser);
		while(resultSet.next()) {
			System.out.println(resultSet.getInt("idUser"));
			userName = resultSet.getString("username");
			pass = resultSet.getString("pass");
			fullName = resultSet.getString("fullname");
			email = resultSet.getString("email");
			dateOfBirth = resultSet.getDate("dateOfBirth");
			address = resultSet.getString("address");
			money = resultSet.getDouble("money");
		}
	}
	
	
	
	public User(String userName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		super();
		this.userName = userName;
		setUserByUsername(userName);
	}

	private void setUserByUsername(String username) throws InstantiationException, 
	IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Connection conn = getConnection();
		String sql = "{call getUserByUsername(?)}";
		CallableStatement st = conn.prepareCall(sql);
		st.setString("username", username);
		st.execute();
		ResultSet resultSet = st.getResultSet();
		
//		ResultSet resultSet = getResultSet("SELECT * FROM User WHERE username = " + username);
		while(resultSet.next()) {
//			System.out.println(resultSet.getInt("idUser"));
			idUser = resultSet.getInt("idUser");
//			userName = resultSet.getString("username");
			pass = resultSet.getString("pass");
			fullName = resultSet.getString("fullName");
			email = resultSet.getString("email");
			dateOfBirth = resultSet.getDate("dateOfBirth");
			address = resultSet.getString("address");
			money = resultSet.getDouble("money");
		}
	}
	
	@Override
	public String toString() {
		return userName;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}
}


