package backend;

import backend.data.DbService;

import backend.data.LoginDetails;
import backend.data.User;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
public class RequestHandler {
    @Resource(name = "DbService")
    private DbService dbService;


    /**
     * .
     * Login REST Method
     */
    @RequestMapping("/login")
    public String loginController(@RequestBody LoginDetails loginDetails) {

        if (dbService.grantAccess(loginDetails.getEmail(), loginDetails.getPassword())) {
            return "success";
        }
        return "failure";
    }

    /**
     * .
     * Sign-up REST Method
     */
    @RequestMapping("/signup")
    public String signupController(@RequestBody User user) {
        System.out.println(user);
        if (dbService.getUser(user.getEmail()) != null) {
            return "user exists already";
        }
        dbService.addUser(user);
        return "success";
        //return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * Processes a friend request.
     * @param yourUsername - Username of the person requesting another to befriend him.
     * @param friendUsername - Username of the person he/she wants to befriend.
     * @return - OK if successful.
     */
    @RequestMapping("/addfriend")
    public String friendRequest(@RequestParam String yourUsername, @RequestParam String friendUsername) {
        User thisUser = dbService.getUser(yourUsername);

        if (dbService.getUser(friendUsername) == null) {
            return "Not valid username";
        }

        else {
            thisUser.newFriendRequest(friendUsername);
            dbService.addUser(thisUser);
            return "OK";
        }

    }

    /**
     * Returns all friend requests for a certain User.
     * @param yourUsername - Username of the User requesting all their friend requests.
     * @return - all the friend requests of this user.
     */
    @RequestMapping("/getfriendreq")
    public ArrayList<String> getAllFriendRequests(@RequestParam String yourUsername) {
        User thisUser = dbService.getUser(yourUsername);
        return thisUser.getFriendRequests();

    }

    /**
     * Accept a friend request and add that person to each others friend list.
     * @param yourUsername - Username of person who wants to accept the request.
     * @param friendUsername - Username of the person User wants to accept.
     * @return
     */
    @RequestMapping("/acceptfriendreq")
    public String acceptFriend(@RequestParam String yourUsername, @RequestParam String friendUsername) {
        User thisUser = dbService.getUser(yourUsername);
        User friendUser = dbService.getUser(friendUsername);
        thisUser.addFriend(friendUsername);
        friendUser.addFriend(yourUsername);
        thisUser.deleteFriendRequest(friendUsername);
        dbService.addUser(thisUser);
        dbService.addUser(friendUser);
        return "OK";
    }



}
