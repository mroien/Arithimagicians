package apiServer;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsiteController {

	// Local URL : http://localhost:8080/login?userName=testDumby&password=test5
    @RequestMapping("/login")
    public String login(@RequestParam(value="userName", defaultValue="0000") String userName, @RequestParam(value="password", defaultValue="0000") String password){
    	Website web = new Website();
    	return web.loginUser(userName, password);
    }
    
	// Local URL : http://localhost:8080/create?email=dumbEmail&userName=testDumby&password=test5
    @RequestMapping("/create")
    public String CreateUser(@RequestParam(value="email", defaultValue="0000") String email, @RequestParam(value="userName", defaultValue="0000") String userName, @RequestParam(value="password", defaultValue="0000") String password){
    	Website web = new Website();
    	return web.createUser(email, userName, password);
    }
    
	// Local URL : http://localhost:8080/duplicate?userName=testDumby
    @RequestMapping("/duplicate")
    public String checkDuplicate(@RequestParam(value="userName", defaultValue="0000") String userName){
    	Website web = new Website();
    	return web.checkDuplicate(userName);
    }
    
	// Local URL : http://localhost:8080/newUser?email=dumbEmail&userName=testDumby&password=test5
    @RequestMapping("/newUser")
    public String saveNewUser(@RequestParam(value="email", defaultValue="0000") String email, @RequestParam(value="userName", defaultValue="0000") String userName, @RequestParam(value="password", defaultValue="0000") String password){
    	Website web = new Website();
    	return web.saveNewUser(email, userName, password);
    }
    
	// Local URL : http://localhost:8080/delete?accountId=1028929
    @RequestMapping("/delete")
    public String deleteAccount(@RequestParam(value="accountId", defaultValue="0000") String accountId){
    	Website web = new Website();
    	return web.deleteAccount(accountId);
    }
    
	// Local URL : http://localhost:8080/transactions?accountId=1028929
    @RequestMapping("/transactions")
    public String getTransactions(@RequestParam(value="accountId", defaultValue="0000") String accountId){
    	Website web = new Website();
    	return web.getTransactions(accountId);
    }
    
	// Local URL : http://localhost:8080/getLeader
    @RequestMapping("/getLeader")
    public String getLeaderBoards(){
    	Website web = new Website();
    	return web.getLeaderBoards();
    }
    
	// Local URL : http://localhost:8080/generateLeader
    @RequestMapping("/generateLeader")
    public String generateLeaderBoards(){
    	Website web = new Website();
    	return web.generateLeaderBoards();
    }
}