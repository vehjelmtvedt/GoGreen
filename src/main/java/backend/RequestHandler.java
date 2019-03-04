package backend;

import backend.data.DbService;

import backend.data.LoginDetails;
import backend.data.User;
import org.springframework.web.bind.annotation.GetMapping;
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

        if (dbService.grantAccess(loginDetails.getIdentifier(), loginDetails.getPassword())) {
            return dbService.getUser(loginDetails.getIdentifier());
        }

        return null;
    }

    /**
     * .
     * Sign-up REST Method
     */
    @RequestMapping("/signup")
    public String signupController(@RequestBody User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());

        if (dbService.getUserByUsername(user.getUsername()) != null) {
            return "Username exists";
        }

        if (dbService.getUser(user.getEmail()) != null) {
            return "Email exists";
        }

        dbService.addUser(user);
        return "success";
    }

    @RequestMapping("/getUser")
    public User getUser(@RequestBody String identifier) {
        return dbService.getUser(identifier);
    }

    @GetMapping("/friendrequest")
    public String friendRequest(@RequestParam String sender, @RequestParam String receiver) {
        return dbService.addFriendRequest(sender, receiver);
    }

    @GetMapping("/acceptfriend")
    public String acceptFriendRequest(@RequestParam String sender, @RequestParam String accepting) {
        return dbService.acceptFriendRequest(sender, accepting);
    }

    @GetMapping("/rejectfriend")
    public String rejectFriendRequest(@RequestParam String sender, @RequestParam String rejecting) {
        return dbService.rejectFriendRequest(sender, rejecting);

    }


}




