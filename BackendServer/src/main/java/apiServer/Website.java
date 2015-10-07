package apiServer;

public class Website {
	private final String userName;
	private final String password;

	public Website(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	
	public Website(){
		userName = "test";
		password = "test";
	}

	public String loginUser(String userName, String password) {
		return "LoginUser: " +  userName + "::" + password;
	}

	public String createUser(String email, String userName, String password) {
		return "CreateUser " +  email + "::" + userName + "::" + password;
	}

	public String checkDuplicate(String userName) {
		return "CheckDuplicate " + "::" + userName;
	}

	public String saveNewUser(String email, String userName, String password) {
		return "saveNewUser " +  email + "::" + userName + "::" + password;
		
	}

	public String deleteAccount(String accountId) {
		return "deleteAccount " + "::" + accountId;
	}

	public String getTransactions(String accountId) {
		return "getTransactions " + "::" + accountId;
	}

	public String getLeaderBoards() {
		return "getLeaderBoards";
	}

	public String generateLeaderBoards() {
		return "generateLeaderBoards";
	}

	public void encrypt(String enc) {

	}

	public void decrypt(String dec) {

	}
}