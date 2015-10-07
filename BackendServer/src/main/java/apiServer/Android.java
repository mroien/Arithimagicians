package apiServer;

public class Android {
	public final String accountId;
	public final String powerUp;
	
	public Android(){
		this.accountId = "test";
		this.powerUp = "test";
	}
	
	public String checkPowerUps(String accountId) {
		return "CheckPowerUps: " +  accountId;
	}
	
	public String usePowerUp(String accountId, String powerUp) {
		return "usePowerUp: " +  accountId + "::" + powerUp;
	}
}
