package apiServer;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsiteController {
	Website web = new Website();

	// Local URL : http://localhost:8080/login?userName=testDumby&password=test5
	@RequestMapping("/login")
	public String login(@RequestParam(value = "userName", defaultValue = "0000") String userName,
			@RequestParam(value = "password", defaultValue = "0000") String password)
					throws SQLException, InstantiationException, IllegalAccessException {
		return web.loginUser(userName, password);
	}

	// Local URL :
	// http://localhost:8080/create?firstName=test&lastName=test&userName=test&street=test&city=test&state=test&zipCode=12345&email=testEmail&password=test5
	@RequestMapping("/create")
	public String CreateUser(@RequestParam(value = "firstName", defaultValue = "0000") String firstName,
			@RequestParam(value = "lastName", defaultValue = "0000") String lastName,
			@RequestParam(value = "userName", defaultValue = "0000") String userName,
			@RequestParam(value = "street", defaultValue = "0000") String street,
			@RequestParam(value = "city", defaultValue = "0000") String city,
			@RequestParam(value = "state", defaultValue = "0000") String state,
			@RequestParam(value = "zipCode", defaultValue = "0000") String zipCode,
			@RequestParam(value = "email", defaultValue = "0000") String email,
			@RequestParam(value = "password", defaultValue = "0000") String password) throws SQLException {

		return web.createUser(firstName, lastName, userName, street, city, state, zipCode, email, password);
	}

	/*
	 * possible redudant code may delete // Local URL :
	 * http://localhost:8080/duplicate?userName=testDumby
	 * 
	 * @RequestMapping("/duplicate") public String
	 * checkDuplicate(@RequestParam(value="userName", defaultValue="0000")
	 * String userName){ Website web = new Website(); return
	 * web.checkDuplicate(userName); }
	 * 
	 * // Local URL :
	 * http://localhost:8080/newUser?email=dumbEmail&userName=testDumby&password
	 * =test5
	 * 
	 * @RequestMapping("/newUser") public String
	 * saveNewUser(@RequestParam(value="email", defaultValue="0000") String
	 * email, @RequestParam(value="userName", defaultValue="0000") String
	 * userName, @RequestParam(value="password", defaultValue="0000") String
	 * password){ Website web = new Website(); return web.saveNewUser(email,
	 * userName, password); }
	 */
	// Local URL : http://localhost:8080/delete?accountId=1028929
	@RequestMapping("/delete")
	public String deleteAccount(@RequestParam(value = "accountId", defaultValue = "0000") String accountId)
			throws SQLException {

		return web.deleteAccount(accountId);
	}

	// Local URL : http://localhost:8080/transactions?accountId=1028929
	@RequestMapping("/transactions")
	public String getTransactions(@RequestParam(value = "accountId", defaultValue = "0000") String accountId)
			throws SQLException {

		return web.getTransactions(accountId);
	}

	// Local URL : http://localhost:8080/getLeader
	@RequestMapping("/getLeader")
	public String getLeaderBoards() throws SQLException {

		return web.getLeaderBoards();
	}

}