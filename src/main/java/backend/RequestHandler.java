package backend;

import backend.data.Activity;
import backend.data.DbService;
import backend.data.LoginDetails;
import backend.data.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

        if (dbService.getUserByUsername(user.getUsername()) != null) {
            return "Username exists";
        }

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
    public User friendRequest(@RequestParam String sender, @RequestParam String receiver) {
        return dbService.addFriendRequest(sender, receiver);
    }

    @RequestMapping("/acceptfriend")
    public User acceptFriendRequest(@RequestParam String sender, @RequestParam String accepting) {
        return dbService.acceptFriendRequest(sender, accepting);
    }

    @RequestMapping("/rejectfriend")
    public User rejectFriendRequest(@RequestParam String sender, @RequestParam String rejecting) {
        return dbService.rejectFriendRequest(sender, rejecting);

    }

    /**
     * Checks if the user is a valid user.
     * @param identifier username or email
     * @return - OK if valid user, NONE otherwise
     */
    @RequestMapping("/validateUser")
    public String validateUser(@RequestBody String identifier) {
        if  (dbService.getUser(identifier) != null
                || dbService.getUserByUsername(identifier) != null) {
            return "OK";
        } else {
            return "NONE";
        }
    }

    /**
     * Request to add activity to User.
     * @param activity - what activity to add.
     * @param identifier - username of the User
     * @return - the User the activity was added to
     */
    @RequestMapping(value = "/addActivity", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public User addActivity(@RequestBody Activity activity, @RequestParam String identifier) {
        User returned = dbService.getUserByUsername(identifier);
        if (returned == null || activity == null) {
            return null;
        }
        returned.addActivity(activity);
        dbService.addUser(returned);
        return returned;
    }

}




