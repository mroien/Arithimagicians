package apiServer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Android {
	public final String accountId;
	public final String powerUp;
	private Connection conn;
	private String dbUser;
	private String dbPass;
	private String ip;
	private String sshUser;
	private String sshPwd;
	private String sshHost;
	private String dbHost;
	private int localPort;

	public Android() {
		this.accountId = "test";
		this.powerUp = "test";
		dbUser = "ics499fa1501";
		dbPass = "ygsgxhuj";
		sshUser = "ics499fa1501";
		sshPwd = "ygsgxhuj";
		sshHost = "sp-cfsics.metrostate.edu";
		int sshPort = 22;
		dbHost = "localhost";
		int dbPort = 3306;
		localPort = 3366;
		ip = "jdbc:mysql://199.17.234.28:3306";

		try {
			sshTunnel(sshUser, sshPwd, sshHost, sshPort, dbHost, localPort, dbPort);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String checkPowerUps(String userId) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String queryUser = "SELECT * from powerups WHERE userId = ? and dateUsed IS NULL";
		try {
			PreparedStatement prepState = conn.prepareStatement(queryUser);
			prepState.setString(1, userId);
			ResultSet rs = prepState.executeQuery();
			if (rs.absolute(1)) {
				return rs.toString();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "DB Error";
		}
		return "No Powerups";
	}

	public String usePowerUp(String accountId, String powerUp) throws SQLException {
		String updateSQL = "Update powerups SET dateUsed = ? " + "WHERE userID = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		try {
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			PreparedStatement prepState = conn.prepareStatement(updateSQL);
			prepState.setString(1, date.toString());
			prepState.setString(2, accountId);
			prepState.executeUpdate();
			return "User Updated";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "DB Error";
		}
	}

	public String updateLeaderboardLevel(String accountId, String level) throws SQLException {
		String updateSQL = "Update leaderboard SET level = ? " + "WHERE userID = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		try {
			PreparedStatement prepState = conn.prepareStatement(updateSQL);
			prepState.setString(1, level);
			prepState.setString(2, accountId);
			prepState.executeUpdate();
			return "User Updated";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "DB Error";
		}
		
	}
	public String checkPhoneNumber(String number) throws SQLException {
		String queryUser = "SELECT * from users WHERE phoneNumber = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		try {
			PreparedStatement prepState = conn.prepareStatement(queryUser);
			prepState.setString(1, number);
			  prepState.executeQuery();
			return "Valid and Unused";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "DB Error";
		}
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
