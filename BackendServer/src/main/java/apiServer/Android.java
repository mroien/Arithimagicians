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

import javax.xml.transform.Result;

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
		localPort = 3367;
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
		String queryUser = "SELECT powerID from powerups WHERE userID = ? and dateUsed = '0000-00-00' and dateAdded = '0000-00-00'";
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
	
	public String powerUpAddedToInv(String accountId, String powerUp) throws SQLException {
		String updateSQL = "Update powerups SET dateAdded = ? " + "WHERE userID = ? AND powerID = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		try {
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			PreparedStatement prepState = conn.prepareStatement(updateSQL);
			prepState.setString(1, date.toString());
			prepState.setString(2, accountId);
			prepState.setString(3, powerUp);
			prepState.executeUpdate();
			return "User Updated";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "DB Error";
		}
	}

	public String updateLeaderboard(String accountId, String username, String level, double accuracyPerLevel, double highestAcc, int maxTotalDmg, int maxSingleDmg) throws SQLException {
		String updateSQL = "Update leaderboard SET level = ?, accuracyPerLevel = ?, highestAccuracy = ?, maxTotalDamage = ?, maxSingleDamage = ?, dateTime = ? " + "WHERE userID = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		String createSql = "INSERT INTO leaderboard (userID, username,level,accuracyPerLevel,highestAccuracy,maxTotalDamage,maxSingleDamage, dateTime) VALUES (?,?,?,?,?,?,?,?)";
		String querySql = "Select * from leaderboard WHERE userID = ?";
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		try {
		
			PreparedStatement query = conn.prepareStatement(querySql);
			query.setString(1, accountId);
			ResultSet rs = query.executeQuery();
			if(rs.absolute(1)){
				PreparedStatement prepState = conn.prepareStatement(updateSQL);
				prepState.setString(1, level);
				prepState.setDouble(2, accuracyPerLevel);
				prepState.setDouble(3, highestAcc);
				prepState.setInt(4, maxTotalDmg);
				prepState.setInt(5, maxSingleDmg);
				prepState.setString(6, date.toString());
				prepState.setString(7, accountId);
				prepState.executeUpdate();
				return "User Updated";
			}
			

			PreparedStatement prepState = conn.prepareStatement(createSql);
			prepState.setString(1, accountId);
			prepState.setString(2, username);
			prepState.setString(3, level);
			prepState.setDouble(4, accuracyPerLevel);
			prepState.setDouble(5, highestAcc);
			prepState.setInt(6, maxTotalDmg);
			prepState.setInt(7, maxSingleDmg);
			prepState.setString(8, date.toString());
			prepState.executeUpdate();
			return "User Updated";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "DB Error";
		}
		
	}
	public String checkPhoneNumber(String number) throws SQLException {
		String queryUser = "SELECT userID from users WHERE phoneNumber = ? AND connected = 0";
		
		conn = DriverManager.getConnection("jdbc:mysql://localhost:" + localPort + "/ics499fa1501", dbUser, dbPass);
		try {
			PreparedStatement prepState = conn.prepareStatement(queryUser);
			 prepState.setString(1, number);
			 ResultSet rs = prepState.executeQuery();
			if(rs.absolute(1)){
				String updateSQL = "Update users SET connected = 1 WHERE userID = " + rs.getString(1);
				PreparedStatement update = conn.prepareStatement(updateSQL);
				update.executeUpdate();
				return rs.getString(1);
			}
			else
				return "Already Used";

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
