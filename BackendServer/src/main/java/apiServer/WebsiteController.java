package apiServer;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsiteController {
	Website web = new Website();

	// Local URL : http://52.32.43.132:8080/login?userName=testDumby&password=test5
	@RequestMapping("/login")
	public String login(@RequestParam(value = "userName", defaultValue = "0000") String userName,
			@RequestParam(value = "password", defaultValue = "0000") String password)
					throws SQLException, InstantiationException, IllegalAccessException, NoSuchAlgorithmException, InvalidKeySpecException {
		return web.loginUser(userName, password);
	}

	// Local URL :
	// http://52.32.43.132:8080/create?firstName=test&lastName=test&userName=test&street=test&city=test&state=test&zipCode=12345&email=testEmail&phoneNumber=1234&password=test5
	@RequestMapping("/create")
	public String CreateUser(@RequestParam(value = "firstName", defaultValue = "0000") String firstName,
			@RequestParam(value = "lastName", defaultValue = "0000") String lastName,
			@RequestParam(value = "userName", defaultValue = "0000") String userName,
			@RequestParam(value = "street", defaultValue = "0000") String street,
			@RequestParam(value = "city", defaultValue = "0000") String city,
			@RequestParam(value = "state", defaultValue = "0000") String state,
			@RequestParam(value = "zipCode", defaultValue = "0000") String zipCode,
			@RequestParam(value = "email", defaultValue = "0000") String email,
			@RequestParam(value = "phoneNumber", defaultValue = "0000") String phoneNumber,
			@RequestParam(value = "password", defaultValue = "0000") String password) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {

		return web.createUser(firstName, lastName, userName, street, city, state, zipCode, email, phoneNumber,  password);
	}


	// Local URL : http://52.32.43.132:8080/delete?userID=1028929
	@RequestMapping("/delete")
	public String deleteAccount(@RequestParam(value = "userID", defaultValue = "0000") String userID)
			throws SQLException {

		return web.deleteAccount(userID);
	}
	
	// Local URL : http://52.32.43.132:8080/getUserInfo?userID=8
	@RequestMapping("/getUserInfo")
	public String getUserInfo(@RequestParam(value = "userID", defaultValue = "0000") String userID)
			throws SQLException {

		return web.getUserInfo(userID);
	}

	// Local URL : http://52.32.43.132:8080/transactions?accountId=1028929
	@RequestMapping("/transactions")
	public String getTransactions(@RequestParam(value = "userID", defaultValue = "0000") String userID)
			throws SQLException {

		return web.getTransactions(userID);
	}

	// Local URL : http://52.32.43.132:8080/getLeader
	@RequestMapping("/getLeader")
	public String getLeaderBoards() throws SQLException {

		return web.getLeaderBoards();
	}
	
	// Local URL : http://52.32.43.132:8080/addPowerup?powerID=DMGBONUS&transactionID=1&userID=8
	@RequestMapping("/addPowerup")
	public String addPowerup(@RequestParam(value = "powerID", defaultValue = "0000") String powerID,
			@RequestParam(value = "transactionID", defaultValue = "0000") String transactionID, @RequestParam(value="userID", defaultValue ="0000") String userID)
					throws SQLException, InstantiationException, IllegalAccessException, NoSuchAlgorithmException, InvalidKeySpecException {
		return web.addPowerup(powerID, transactionID, userID);
	}
	
	// Local URL : http://52.32.43.132:8080/addTransaction?&userID=1&amount=8
	@RequestMapping("/addTransaction")
	public String addTransaction(
			@RequestParam(value = "userID", defaultValue = "0000") String userID, @RequestParam(value="amount", defaultValue ="0000") String amount)
					throws SQLException, InstantiationException, IllegalAccessException, NoSuchAlgorithmException, InvalidKeySpecException {
		return web.addTransaction( userID, amount);
	}
	
	// Local URL : http://52.32.43.132:8080/getPowerups?userID=8
	@RequestMapping("/getPowerups")
	public String getPowerups(@RequestParam(value = "userID", defaultValue = "0000") String userID)
					throws SQLException, InstantiationException, IllegalAccessException, NoSuchAlgorithmException, InvalidKeySpecException {
		return web.getPowerups(userID);
	}
	
	// Local URL : http://52.32.43.132:8080/addCard?userID=8&creditCardNumber=1234&creditCardType=visa
	@RequestMapping("/addCard")
	public String addCard(@RequestParam(value = "userID", defaultValue = "0000") String userID, @RequestParam(value = "creditCardNumber", defaultValue = "0000") String creditCardNumber,
			@RequestParam(value = "creditCardType", defaultValue = "0000") String creditCardType)
					throws SQLException, InstantiationException, IllegalAccessException, NoSuchAlgorithmException, InvalidKeySpecException {
		return web.addCard(userID, creditCardNumber, creditCardType);
	}

}