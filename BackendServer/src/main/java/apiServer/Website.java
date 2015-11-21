package apiServer;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import ch.qos.logback.core.net.SyslogOutputStream;

public class Website {
	private final String userName;
	private final String password;
	private String dbUser;
	private String dbPass;
	private String ip;
	private String sshUser;
	private String sshPwd;
	private String sshHost;
	private String dbHost;
	private int localPort;
	private Connection conn;
	private PasswordHash pwdHash;

	public Website(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public Website() {
		userName = "test";
		password = "test";
		dbUser = "ics499fa1501";
		dbPass = "ygsgxhuj";
		sshUser = "ics499fa1501";
		sshPwd = "ygsgxhuj";
		sshHost = "sp-cfsics.metrostate.edu";
		int sshPort = 22;
		dbHost = "localhost";
		int dbPort = 3306;
		localPort = 3366;
		pwdHash = new PasswordHash();
		try {
			sshTunnel(sshUser, sshPwd, sshHost, sshPort, dbHost, localPort, dbPort);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String loginUser(String userName, String password)
			throws SQLException, InstantiationException, IllegalAccessException, NoSuchAlgorithmException, InvalidKeySpecException {
		PreparedStatement prepState = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String queryUser = "SELECT hash FROM users WHERE (userName = ?);";
		
		try {
			prepState = conn.prepareStatement(queryUser);
			prepState.setString(1, userName);
			ResultSet rs = prepState.executeQuery();
			if (rs.absolute(1)) {
				String hash = rs.getString(1);
				boolean results = pwdHash.validatePassword(password, hash);
				if(results == true){
					String selectQuery = "Select userID from users WHERE userName = ? AND deleted = 0";
					PreparedStatement query = conn.prepareStatement(selectQuery);
					query.setString(1, userName);
					ResultSet rs2 = query.executeQuery();
					if(rs2.absolute(1)){
					
					String userId = rs2.getString(1);
					return userId;
					}
				}
				else{
				conn.close();
				return "Invalid Login";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.close();
			return prepState.toString();
		}
		conn.close();
		return "Invalid Login";

	}

	public String createUser(String firstName, String lastName, String userName, String street, String city,
			String state, String zipCode, String email, String phoneNumber, String password) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
		String insertTableSQL = "INSERT INTO users"
				+ "(firstName, lastName, userName, street, city, state, zipCode, email, phoneNumber, hash) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?)";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String queryUser = "SELECT * from users WHERE username = ?";
		try {
			PreparedStatement prepStateFirst = conn.prepareStatement(queryUser);
			prepStateFirst.setString(1, userName);
			System.out.println(prepStateFirst.toString());
			ResultSet rs = prepStateFirst.executeQuery();
			if (rs.absolute(1)) {
				conn.close();
				return "User already found";
			} else {
				 String hash = pwdHash.createHash(password);

				PreparedStatement prepState = conn.prepareStatement(insertTableSQL);
				prepState.setString(1, firstName);
				prepState.setString(2, lastName);
				prepState.setString(3, userName);
				prepState.setString(4, street);
				prepState.setString(5, city);
				prepState.setString(6, state);
				prepState.setString(7, zipCode);
				prepState.setString(8, email);
				prepState.setString(9, phoneNumber);
				
				prepState.setString(10, hash);
				

				prepState.executeUpdate();
				
				String query = "Select userID from users where username = ? AND email = ?";
				PreparedStatement queryState = conn.prepareStatement(query);
				queryState.setString(1, userName);
				queryState.setString(2, email);
				rs = queryState.executeQuery();
				rs.next();
				return rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.close();
			return e.getMessage();
		}

	}

	public String deleteAccount(String userName) throws SQLException {
		String deleteUser = "Update users SET deleted = 1 " + "WHERE userID = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String queryUser = "SELECT * from users WHERE userID = ?";
		try {
			PreparedStatement prepStateFirst = conn.prepareStatement(queryUser);
			prepStateFirst.setString(1, userName);
			ResultSet rs = prepStateFirst.executeQuery();
			if (rs.absolute(1)) {
				PreparedStatement prepStateDelete = conn.prepareStatement(deleteUser);
				
				prepStateDelete.setString(1, userName);
				System.out.println(prepStateDelete.toString());
				prepStateDelete.executeUpdate();
				conn.close();
				return "Account Deleted";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.close();
			return "DB Error";
		}
		conn.close();
		return "Account not Found";
	}

	public String getTransactions(String userId) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String queryTrans = "SELECT * from transactions WHERE userID = ?";
		try {
			PreparedStatement prepStateFirst = conn.prepareStatement(queryTrans);
			prepStateFirst.setInt(1, Integer.parseInt(userId));
			ResultSet rs = prepStateFirst.executeQuery();
			System.out.println(userId);
			System.out.println(prepStateFirst.toString());
			String result = "";
			if(rs.absolute(1)){
				System.out.println("in");
				rs.beforeFirst();
			while(rs.next())
			{
				System.out.println(rs.getString(1));
				result += "transactionId:" +  rs.getString(1) + " userId:" + rs.getString(2) + " date:" + rs.getString(3) + " amount:" + rs.getString(4) + ",";
				System.out.println(result);
			}
			conn.close();
			return result;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.close();
			return "DB Error";
		}
		conn.close();
		return "No Transactions";
	}

	public String getLeaderBoards() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String queryLeaderboards = "SELECT * from leaderboard";
		try {
			PreparedStatement prepStateFirst = conn.prepareStatement(queryLeaderboards);
			ResultSet rs = prepStateFirst.executeQuery();
			
			String result = "";
		
			if(rs.absolute(1)){
				System.out.println("in");
				rs.beforeFirst();
			while(rs.next())
			{
				System.out.println(rs.getString(1));
				result += "userID:" +  rs.getString(1) + " username:" + rs.getString(2) + " level:" + rs.getString(3) + " accuracyPerLeve:" + rs.getString(4) + " highestAccuracy:"
				+  rs.getString(5) + " maxTotalDamag:" + rs.getString(6) 
				+ " maxSingleDamage:" + rs.getString(7) + " Date:" + rs.getString(8) + ",";
				System.out.println(result);
			}
			conn.close();
			return result;
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.close();
			return "DB Error";
		}
		conn.close();
		return "No Transactions";
	}
	
	public String getUserInfo(String userID) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String queryLeaderboards = "SELECT * from users WHERE (userID = ?)";
		try {
			PreparedStatement prepStateFirst = conn.prepareStatement(queryLeaderboards);
			prepStateFirst.setString(1, userID);
			ResultSet rs = prepStateFirst.executeQuery();
			
			String result = "";
		
			if(rs.absolute(1)){
				rs.beforeFirst();
			while(rs.next())
			{
				result += "userID:" +  rs.getString(1) + "*firstname:" + rs.getString(2) + "*lastname:" + rs.getString(3) + "*username:" + rs.getString(4) 
				+ "*street:" +  rs.getString(5) + "*city:" + rs.getString(6)  + "*state:" + rs.getString(7) + "*zipCode:" + rs.getString(8)  
				+ "*email:" + rs.getString(9) +  "*accuracy:" + rs.getString(10) +  "*operation:" + rs.getString(11) +  "*target:" + rs.getString(12)  
				+ "*numberOfTries:" + rs.getString(13)  + "*maxLevel:" + rs.getString(14)  + "*lastLogin:" + rs.getString(15)  + "*created:" + rs.getString(16) 
				+ "*phoneNumber:" + rs.getString(20);
			}
			conn.close();
			return result;
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.close();
			return "DB Error";
		}
		conn.close();
		return "No User Found";
	}
	
	public String addPowerup(String powerID, String transactionID, String userID) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String insert = "INSERT INTO powerups"
				+ "(powerID, transactionID, userID) VALUES"
				+ "(?,?,?)";
		try {
			PreparedStatement prep = conn.prepareStatement(insert);
			prep.setString(1, powerID);
			prep.setInt(2, Integer.parseInt(transactionID));
			prep.setInt(3,  Integer.parseInt(userID));
			prep.executeUpdate();
			conn.close();
			return "Powerup Added";
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.close();
			return "DB Error";
		}
		
		
	}
	
	public String addTransaction(String userID, String amount) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String insert = "INSERT INTO transactions"
				+ "( userID, date, amount) VALUES"
				+ "(?,?,?)";
		try {
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

			PreparedStatement prep = conn.prepareStatement(insert);
			
			prep.setInt(1, Integer.parseInt(userID));
			prep.setDate(2, date);
			prep.setDouble(3,  Double.parseDouble(amount));
			prep.executeUpdate();
			conn.close();
			return "Transaction Added";
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.close();
			return "DB Error";
		}
		
		
	}
	public String getPowerups(String userId) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String queryUser = "SELECT powerID from powerups WHERE userID = ? and dateUsed = '0000-00-00'";
		try {
			
			PreparedStatement prepState = conn.prepareStatement(queryUser);
			prepState.setString(1, userId);
			ResultSet rs = prepState.executeQuery();
			String result = "";
			if (rs.absolute(1)) {
				result += rs.getString(1);
				while(rs.next()){
					result += "," + rs.getString(1);
				}
				return result;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "DB Error";
		}
		return "No Powerups";
	}

	public String addCard(String userID, String creditCardNumber, String creditCardType) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String insert = "INSERT INTO creditCards"
				+ "(userID, creditCardNumber, creditCardType, dateCreated) VALUES"
				+ "(?,?,?,?)";
		try {
			PreparedStatement prep = conn.prepareStatement(insert);
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			prep.setString(1, userID);
			prep.setInt(2, Integer.parseInt(creditCardNumber));
			prep.setString(3,creditCardType);
			prep.setDate(4, date);
			prep.executeUpdate();
			conn.close();
			return "Card Added";
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.close();
			return "DB Error";
		}
		
		
	}
	public ArrayList<byte[]> encrypt(String enc) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		ArrayList<byte[]> ret = new ArrayList<byte[]>();
		SecureRandom sr = new SecureRandom();
		byte salt[] = new byte[20];
		sr.nextBytes(salt);
		String pwd = enc + new String(salt);
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(pwd.getBytes("UTF-8"));
		ret.add(salt);
		ret.add(hash);
		return ret;
	}

	public String decrypt(String dec) {
		return dec;

	}

	public static void sshTunnel(String sshUser, String sshPwd, String sshHost, int sshPort, String remoteHost,
			int localPort, int remotePort) throws JSchException {
		final JSch jsch = new JSch();
		Session session = jsch.getSession(sshUser, sshHost, 22);
		session.setPassword(sshPwd);
		final Properties prop = new Properties();
		prop.put("StrictHostKeyChecking", "no");
		session.setConfig(prop);

		session.connect();
		session.setPortForwardingL(localPort, remoteHost, remotePort);
	}

}