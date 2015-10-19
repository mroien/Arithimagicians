package apiServer;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AndroidController {
	Android and = new Android();

	// Local URL : http://localhost:8080/checkPowerUp?accountId=testDumby
	@RequestMapping("/checkPowerUp")
	public String checkPowerUps(@RequestParam(value = "accountId", defaultValue = "0000") String accountId)
			throws SQLException {
		return and.checkPowerUps(accountId);
	}

	// Local URL :
	// http://localhost:8080/usePowerUp?accountId=testDumby&powerUp=Fire
	@RequestMapping("/usePowerUp")
	public String usePowerUp(@RequestParam(value = "accountId", defaultValue = "0000") String accountId,
			@RequestParam(value = "powerUp", defaultValue = "0000") String powerUp) throws SQLException {

		return and.usePowerUp(accountId, powerUp);
	}

	// Local URL : http://localhost:8080/updateLeaderboardLevel?accountId=test
	@RequestMapping("/updateLeaderboardLevel")
	public String updateLeaderboardLevel(@RequestParam(value = "accountId", defaultValue = "0000") String accountId,
			@RequestParam(value = "level", defaultValue = "0000") String level) throws SQLException {
		return and.updateLeaderboardLevel(accountId, level);
	}
}
