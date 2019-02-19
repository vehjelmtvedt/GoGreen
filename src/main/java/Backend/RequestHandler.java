package Backend;

import Backend.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class RequestHandler {

    @Autowired
    private UserRepository users;

    @Autowired
    private MongoTemplate mongoTemplate;

    /** Adds a user to the database */
    public void addUser(User user)
    {
        users.save(user);
    }

    /** Gets a user from the database */
    public User getUser(String email)
    {
        // User may not be present in the database
        Optional<User> user = users.findById(email);

        // Returns user if found, else returns null
        return user.orElse(null);
    }

    /** Gets users' friends */
    public List<User> getFriends(String email)
    {
        User user = getUser(email);

        if (user == null)
            return null;
        else
        {
            // Query that returns a list of all the user's friends
            return mongoTemplate.find(
                    new Query(Criteria.where("email") // Compare against User email
                            .in(user.getFriends())), // Email must be in users friend list
                    User.class); // Resulting Object type User
        }
    }


    @RequestMapping("/greeting")
    public String respond() {
        return "Success!";
    }

    //Handles authentication
    @RequestMapping("/login")
    public String loginController(@RequestBody LoginDetails loginDetails) {
        User user = getUser(loginDetails.getEmail());
        if(user==null)
            return "user does not exist";
        if(user.getPassword().equals(loginDetails.getPassword()))
            return "auth success";
        else
            return "auth fail";
    }

    //Handles creating a new user
    @RequestMapping("/signup")
    public String signupController(@RequestBody User user){
        if(getUser(user.getEmail())!=null)
            return "email already exists";
        addUser(user);
        return "success";
    }
}