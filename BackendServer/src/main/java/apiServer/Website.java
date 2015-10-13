package apiServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Website {
	private final String userName;
	private final String password;
	private String dbUser;
	private String dbPass;
	private String ip;
	private Connection conn;
	public Website(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	
	public Website(){
		userName = "test";
		password = "test";
		dbUser = "";
		dbPass = "";
		ip = "";
		
	}

	public String loginUser(String userName, String password) throws SQLException {
		conn = DriverManager.getConnection(ip, dbUser, dbPass);
		String hash = encrypt(password);
		String queryUser = "SELECT * from Users WHERE username = '" + userName + "'" + "and hash = '" + hash +"'";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryUser);
			if(rs.absolute(1))
			{
				return "Success";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "DB Error";
		}
		return "Invalid Login";

	}

	public String createUser(String email, String userName, String password) throws SQLException {
		String insertTableSQL = "INSERT INTO user"
				+ "(userName, email, hash, salt) VALUES"
				+ "(?,?,?,?)";
		conn = DriverManager.getConnection(ip, dbUser, dbPass);
		String queryUser = "SELECT * from Users WHERE username = '" + userName + "'";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryUser);
			if(rs.absolute(1))
			{
				return "User already found";
			}
			else
			{
				String enc = encrypt(password);
				PreparedStatement prepState = conn.prepareStatement(insertTableSQL);
				prepState.setString(1, userName);
				prepState.setString(2, email);
				prepState.setString(3, enc);
				prepState.setString(4, "salt");
				
				prepState.executeUpdate();
				
				return "User Created";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "DB Error";
		}
		
	
	}
/**
 * 
		Think this is redudant code, may delete soon

	public String checkDuplicate(String userName) {
		return "CheckDuplicate " + "::" + userName;
	}

	public String saveNewUser(String email, String userName, String password) {
		return "saveNewUser " +  email + "::" + userName + "::" + password;
		
	}
 * @throws SQLException 
 */
	public String deleteAccount(String userName) throws SQLException {
		String deleteUser = "DELETE FROM user WHERE userName = '" + userName + "'";
		conn = DriverManager.getConnection(ip, dbUser, dbPass);
		String queryUser = "SELECT * from Users WHERE username = '" + userName + "'";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryUser);
			if(rs.absolute(1))
			{
				st.executeUpdate(deleteUser);
				return "Account Delete";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "DB Error";
		}
		return "Account not Found";
	}

	public String getTransactions(String accountId) throws SQLException {
		conn = DriverManager.getConnection(ip, dbUser, dbPass);
		String queryTrans = "SELECT * from Transactions WHERE username = '" + userName + "'";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryTrans);
			if(rs != null){
				return rs.toString();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "DB Error";
		}
		return "No Transactions";
	}

	public String getLeaderBoards() throws SQLException {
		conn = DriverManager.getConnection(ip, dbUser, dbPass);
		String queryLeaderboards = "SELECT top 20 from Leaderboard";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryLeaderboards);
			if(rs != null){
				return rs.toString();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "DB Error";
		}
		return "No Transactions";
	}


	public String encrypt(String enc) {
		return enc;

	}

	public String decrypt(String dec) {
		return dec;

	}
}