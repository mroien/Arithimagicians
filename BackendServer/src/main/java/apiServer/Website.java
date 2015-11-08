package apiServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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

		try {
			sshTunnel(sshUser, sshPwd, sshHost, sshPort, dbHost, localPort, dbPort);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String loginUser(String userName, String password)
			throws SQLException, InstantiationException, IllegalAccessException {
		PreparedStatement prepState = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);

		String hash = "tempHash";
		String queryUser = "SELECT * FROM users WHERE (userName = ?) " + "and (hash = ?);";
		try {
			prepState = conn.prepareStatement(queryUser);
			prepState.setString(1, userName);
			prepState.setString(2, hash);
			ResultSet rs = prepState.executeQuery();
			if (rs.absolute(1)) {
				conn.close();
				return "Success";
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
			String state, String zipCode, String email, String password) throws SQLException {
		String insertTableSQL = "INSERT INTO users"
				+ "(firstName, lastName, userName, street, city, state, zipCode, email, hash, salt) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?)";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String queryUser = "SELECT * from users WHERE username = ?";
		try {
			PreparedStatement prepStateFirst = conn.prepareStatement(queryUser);
			prepStateFirst.setString(1, userName);
			ResultSet rs = prepStateFirst.executeQuery();
			if (rs.absolute(1)) {
				conn.close();
				return "User already found";
			} else {
				// String enc = encrypt(password);
				String enc = "sksjsjsj";
				PreparedStatement prepState = conn.prepareStatement(insertTableSQL);
				prepState.setString(1, firstName);
				prepState.setString(2, lastName);
				prepState.setString(3, userName);
				prepState.setString(4, street);
				prepState.setString(5, city);
				prepState.setString(6, state);
				prepState.setString(7, zipCode);
				prepState.setString(8, email);
				prepState.setString(9, "tempHash");
				prepState.setString(10, "tempSalt");

				prepState.executeUpdate();
				conn.close();
				return "User Created";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.close();
			return e.getMessage();
		}

	}

	public String deleteAccount(String userName) throws SQLException {
		String deleteUser = "DELETE FROM users WHERE userName = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String queryUser = "SELECT * from users WHERE userID = ?";
		try {
			PreparedStatement prepStateFirst = conn.prepareStatement(queryUser);
			prepStateFirst.setString(1, userName);
			ResultSet rs = prepStateFirst.executeQuery();
			if (rs.absolute(1)) {
				PreparedStatement prepStateDelete = conn.prepareStatement(deleteUser);
				prepStateDelete.setString(1, userName);
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
		String queryTrans = "SELECT * from transactions WHERE userId = ?";
		try {
			PreparedStatement prepStateFirst = conn.prepareStatement(queryTrans);
			prepStateFirst.setString(1, userId);
			ResultSet rs = prepStateFirst.executeQuery();
			if (rs.absolute(1)) {
				conn.close();
				return rs.getString(1);
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
		String queryLeaderboards = "SELECT top 20 * from leaderboard";
		try {
			PreparedStatement prepStateFirst = conn.prepareStatement(queryLeaderboards);
			ResultSet rs = prepStateFirst.executeQuery();
			if (rs != null) {
				conn.close();
				return rs.toString();
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

	public String encrypt(String enc) {
		return enc;

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