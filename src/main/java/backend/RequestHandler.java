package backend;

import backend.data.DbService;

import backend.data.LoginDetails;
import backend.data.User;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;



@RestController
public class RequestHandler {
    @Resource(name = "DbService")
    private DbService dbService;

    /*    @RequestMapping("/greeting")
        public String respond() {
            return "TestGreeting";
        }*/

    /**
     * .
     * Login REST Method
     */
    @RequestMapping("/login")
    public User loginController(@RequestBody LoginDetails loginDetails) {

        return dbService.grantAccess(loginDetails.getIdentifier(),loginDetails.getPassword());
    }

    /**
     * .
     * Sign-up REST Method
     */
    @RequestMapping("/signup")
    public String signupController(@RequestBody User user) {

        System.out.println(user);
        if (dbService.getUserByUsername(user.getUsername()) != null) {
            return "Username exists";
        }
        System.out.println("IN1");
        if (dbService.getUser(user.getEmail()) != null) {
            return "Email exists";
        }
        dbService.addUser(user);
        return "success";
        //return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @RequestMapping("/getUser")
    public User getUser(@RequestBody String identifier) {
        return dbService.getUser(identifier);
    }

    @RequestMapping("/friendrequest")
    public String friendRequest(@RequestParam String sender, @RequestParam String receiver) {
        return dbService.addFriendRequest(sender, receiver);
    }

    @RequestMapping("/acceptfriend")
    public String acceptFriendRequest(@RequestParam String sender, @RequestParam String accepting) {
        return dbService.acceptFriendRequest(sender, accepting);
    }

    @RequestMapping("/rejectfriend")
    public String rejectFriendRequest(@RequestParam String sender, @RequestParam String rejecting) {
        return dbService.rejectFriendRequest(sender, rejecting);

    }

    @RequestMapping("/validateEmail")
    public boolean validateEmail(@RequestParam String email) {
        return dbService.getUser(email)!=null;
    }

    @RequestMapping("/validateUsername")
    public boolean validateUsername(@RequestParam String username) {
        return dbService.getUserByUsername(username)!=null;
    }
}




