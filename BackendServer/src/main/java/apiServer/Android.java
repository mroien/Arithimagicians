package apiServer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Android {
	public final String accountId;
	public final String powerUp;
	private Connection conn;
	private String dbUser;
	private String dbPass;
	private String ip;
	public Android(){
		this.accountId = "test";
		this.powerUp = "test";
		dbUser = "";
		dbPass = "";
		ip = "";
	}
	
	public String checkPowerUps(String userId) throws SQLException {
		conn = DriverManager.getConnection(ip, dbUser, dbPass);
		String queryUser = "SELECT * from PowerUps WHERE userId = '" + userId + "'" + "and dateUsed IS NULL";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryUser);
			if(rs.absolute(1))
			{
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
		String updateSQL = "Update PowerUps SET dateUsed = ? " + "WHERE userID = ?";
		conn = DriverManager.getConnection(ip, dbUser, dbPass);
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
		String updateSQL = "Update Leaderboard SET level = ? " + "WHERE userID = ?";
		conn = DriverManager.getConnection(ip, dbUser, dbPass);
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
}
