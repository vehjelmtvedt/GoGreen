package backend;

import data.Activity;
import data.LoginDetails;
import data.User;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.annotation.Resource;




@RestController
public class UserRequestHandler {
    @Resource(name = "DbService")
    private DbService dbService;

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

        if (dbService.getUserByEmail(user.getEmail()) != null) {
            return "Email exists";
        }

        dbService.addUser(user);
        return "success";
        //return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @RequestMapping("/friendrequest")
    public boolean friendRequest(@RequestParam String sender,
                                 @RequestParam String receiver) {
        return dbService.addFriendRequest(sender, receiver);
    }

    @RequestMapping("/acceptfriend")
    public boolean acceptFriendRequest(@RequestParam String sender,
                                       @RequestParam String accepting) {
        return dbService.acceptFriendRequest(sender, accepting);
    }

    @RequestMapping("/rejectfriend")
    public boolean rejectFriendRequest(@RequestParam String sender,
                                       @RequestParam String rejecting) {
        return dbService.rejectFriendRequest(sender, rejecting);

    }

    /**
     * Checks if the user is a valid user.
     * @param identifier username or email
     * @return - OK if valid user, NONE otherwise
     */
    @RequestMapping("/validateUser")
    public String validateUser(@RequestBody String identifier) {
        if  (dbService.getUser(identifier) != null) {
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
        return dbService.addActivityToUser(identifier, activity);
    }

    /**
     * Request to retrieve friends.
     * @param loginDetails for auth
     * @return a list of friends
     */
    @RequestMapping("/getFriends")
    public List<User> getFriends(@RequestBody LoginDetails loginDetails) {
        if (dbService.grantAccess(loginDetails.getIdentifier(),
                loginDetails.getPassword()) != null) {
            return dbService.getFriends(loginDetails.getIdentifier());
        }
        return null;
    }

    /**
     * Request to edit profile.
     * @param loginDetails for auth
     * @param fieldName the name of the field being changed
     * @param newValue the new value of the field
     * @return the updated user
     */
    @RequestMapping("/editProfile")
    public User editProfile(@RequestBody LoginDetails loginDetails, @RequestParam String fieldName,
                            @RequestParam Object newValue,@RequestParam String typeName) {

        if (typeName.equals("Integer")) {
            newValue = Integer.parseInt((String)newValue);
        } else if (typeName.equals("Double")) {
            newValue = Double.parseDouble((String)newValue);
        }

        User user = dbService.grantAccess(loginDetails.getIdentifier(),loginDetails.getPassword());
        System.out.println(user);
        if (user == null) {
            return null;
        }

        return dbService.editProfile(user,fieldName,newValue);
    }
    
    /**
     * request to reset password.
     * @param email email of the user
     * @param answer answer to security question
     * @param newPass new password
     * @return true if success, false if not
     */
    @RequestMapping("/forgotPass")
    public Boolean forgotPass(@RequestParam String email, @RequestParam String answer,
                           @RequestParam int questionid ,@RequestParam String newPass) {
        User user = dbService.getUser(email);
        if (user == null) {
            return null;
        }

        if (user.getSecurityQuestionAnswer().equals(answer)
                && user.getSecurityQuestionId() == questionid) {
            dbService.editProfile(user, "password", newPass);
            return true;
        }

        return false;
    }

    /**
     * request to get rank of a user.
     * @param loginDetails auth and identifier
     * @return the rank
     */
    @RequestMapping("/getRank")
    public Integer getRank(@RequestBody LoginDetails loginDetails) {
        if (dbService.grantAccess(loginDetails.getIdentifier(),
                loginDetails.getPassword()) != null) {
            return dbService.getUserRank(loginDetails.getIdentifier());
        }
        return null;
    }


}




