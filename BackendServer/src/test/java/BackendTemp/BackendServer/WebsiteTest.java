package BackendTemp.BackendServer;

import java.sql.SQLException;

import apiServer.WebsiteController;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WebsiteTest extends TestCase {
	WebsiteController webCont = new WebsiteController();

	public void testLoginSuccess() throws InstantiationException, IllegalAccessException, SQLException {

		String userName = "test";
		String password = "pwd";
		String result = webCont.login(userName, password);
		assertSame(result, "Success");
		webCont = null;

	}

	public void testLoginFailure() throws InstantiationException, IllegalAccessException, SQLException {
		String userName = "notaLogin";
		String password = "pwd";
		String result = webCont.login(userName, password);
		assertNotSame(result, "Success");
		assertSame(result, "Invalid Login");
	}

	public void testCreateUserSuccess() throws SQLException {
		String firstName = "junitTest";
		String lastName = "junitsuperTest";
		String userName = "junitTest";
		String street = "1234 test st";
		String city = "test city";
		String state = "mn";
		String zipCode = "55125";
		String email = "testEmail@gmail.com";
		String password = "testPassword";

		String result = webCont.CreateUser(firstName, lastName, userName, street, city, state, zipCode, email,
				password);
		assertSame(result, "User Created");
	}

	public void testCreateUserAlreadyRegistered() throws SQLException {
		String firstName = "test";
		String lastName = "junitsuperTest";
		String userName = "junitTest";
		String street = "1234 test st";
		String city = "test city";
		String state = "mn";
		String zipCode = "55125";
		String email = "testEmail@gmail.com";
		String password = "testPassword";

		String result = webCont.CreateUser(firstName, lastName, userName, street, city, state, zipCode, email,
				password);
		assertSame(result, "User already found");
	}

	public void testDeleteAccountSuccess() throws SQLException {
		String accountId = "0000";

		String result = webCont.deleteAccount(accountId);
		assertSame(result, "Account Deleted");

	}

	public void testDeleteAccountUnsuccessful() throws SQLException {
		String accountId = "9999";

		String result = webCont.deleteAccount(accountId);
		assertSame(result, "Account not Found");

	}

	public void testGetTransactionsSuccessful() throws SQLException {
		String accountId = "0000";

		String result = webCont.getTransactions(accountId);
		assertNotSame(result, null);
	}

	public void testGetTransactionsUnSuccessful() throws SQLException {
		String accountId = "9999";

		String result = webCont.getTransactions(accountId);
		assertSame(result, "No Transactions");
	}

	public void testGetLeaderboardsSuccessful() throws SQLException {
		String result = webCont.getLeaderBoards();
		assertNotSame(result, null);
	}

}
