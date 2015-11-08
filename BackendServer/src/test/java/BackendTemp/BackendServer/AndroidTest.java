package BackendTemp.BackendServer;

import java.sql.SQLException;

import apiServer.AndroidController;
import junit.framework.TestCase;

public class AndroidTest extends TestCase {
	AndroidController and = new AndroidController();

	public void testCheckPowerupsSuccess() throws SQLException {
		String accountId = "0000";

		String result = and.checkPowerUps(accountId);
		assertNotSame(result, null);
	}

	public void testCheckPowerupsUnSuccess() throws SQLException {
		String accountId = "9999";

		String result = and.checkPowerUps(accountId);
		assertSame(result, "No Powerups");
	}

	public void testUsePowerupsSuccess() throws SQLException {
		String accountId = "0000";
		String powerUp = "health";

		String result = and.usePowerUp(accountId, powerUp);

		assertSame(result, "User Updated");
	}

	public void testupdateLeaderboard() throws SQLException {
		String accountId = "0000";
		String level = "1_1";

		String result = and.updateLeaderboardLevel(accountId, level);
		assertSame(result, "User Updated");
	}
}
