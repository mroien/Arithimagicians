package apiServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AndroidController {

	
	// Local URL : http://localhost:8080/checkPowerUp?accountId=testDumby
    @RequestMapping("/checkPowerUp")
    public String checkPowerUps(@RequestParam(value="accountId", defaultValue="0000") String accountId){
    	Android and = new Android();
    	return and.checkPowerUps(accountId);
    }
    
	// Local URL : http://localhost:8080/usePowerUp?accountId=testDumby&powerUp=Fire
    @RequestMapping("/usePowerUp")
    public String usePowerUp(@RequestParam(value="accountId", defaultValue="0000") String accountId, @RequestParam(value="powerUp", defaultValue="0000") String powerUp){
    	Android and = new Android();
    	return and.usePowerUp(accountId, powerUp);
    }
}
