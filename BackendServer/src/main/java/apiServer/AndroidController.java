package apiServer;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AndroidController {
	Android and = new Android();

	//  URL : http://52.32.43.132:8080/checkPowerUp?accountId=testDumby
	@RequestMapping("/checkPowerUp")
	public String checkPowerUps(@RequestParam(value = "accountId", defaultValue = "0000") String accountId)
			throws SQLException {
		return and.checkPowerUps(accountId);
	}
	//  URL : http://52.32.43.132:8080/powerUpAddedToInv?accountId=testDumby&powerUpName=HealthPotion
	@RequestMapping("/powerUpAddedToInv")
	public String powerUpAddedToInv(@RequestParam(value = "accountId", defaultValue = "0000") String accountId ,@RequestParam(value = "powerUpName", defaultValue = "0000") String powerUpName)
			throws SQLException {
		return and.powerUpAddedToInv(accountId, powerUpName);
	}
	//  URL :
	// http://52.32.43.132:8080/usePowerUp?accountId=testDumby&powerUp=Fire
	@RequestMapping("/usePowerUp")
	public String usePowerUp(@RequestParam(value = "accountId", defaultValue = "0000") String accountId,
			@RequestParam(value = "powerUp", defaultValue = "0000") String powerUp) throws SQLException {

		return and.usePowerUp(accountId, powerUp);
	}

	//  URL : http://localhost:8080/updateLeaderboard?accountId=8&username=test&level=1_1&accuracyPerLevel=20&highestAcc=100&maxTotalDmg=10&maxSingleDmg=5&operation=+&target=5&numberOfTries=10
	@RequestMapping("/updateLeaderboard")
	public String updateLeaderboard(@RequestParam(value = "accountId", defaultValue = "0000") String accountId,
			 @RequestParam(value = "username", defaultValue = "0000") String username,
			@RequestParam(value = "level", defaultValue = "0000") String level,
			@RequestParam(value = "accuracyPerLevel", defaultValue = "0000") double accuracyPerLevel
			, @RequestParam(value = "highestAcc", defaultValue = "0000") double highestAcc
			, @RequestParam(value = "maxTotalDmg", defaultValue = "0000") int maxTotalDmg
			, @RequestParam(value = "maxSingleDmg", defaultValue = "0000") int maxSingleDmg
			, @RequestParam(value = "operation", defaultValue = "0000") String operation
			, @RequestParam(value = "target", defaultValue = "0000") String target
			, @RequestParam(value = "numberOfTries", defaultValue = "0000") int numberOfTries)throws SQLException {
		return and.updateLeaderboard(accountId,username, level, accuracyPerLevel, highestAcc, maxTotalDmg, maxSingleDmg, operation, target, numberOfTries);
	}
	
	//  URL : http://52.32.43.132:8080/checkPhoneNumber?number=1234
	@RequestMapping("/checkPhoneNumber")
	public String checkPhoneNumber(@RequestParam(value = "number", defaultValue = "0000") String number) throws SQLException {
		return and.checkPhoneNumber(number);
	}
}
