package Backend;

import Backend.data.*;
import Backend.data.User;
import Backend.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class DBHandler {
    @Autowired
    private UserRepository users;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args)
    {
        SpringApplication.run(DBRun.class, args);
    }

    /** Adds a user to the database */
    public void addUser(User user)
    {
        user.setPassword(encodePassowrd(user.getPassword()));
        users.save(user);
    }

    private String encodePassowrd(String password)
    {
        return passwordEncoder().encode(password);
    }

    public boolean grantAccess(String email, String password)
    {
        User user = getUser(email);

        if (user == null)
            return false;

        return (passwordEncoder().matches(password, user.getPassword()));
    }

    /** Deletes a user from the database */
    public void deleteUser(User user) { users.delete(user); }

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

    @RequestMapping("/DBaddUser")
    public String DBaddUserHandler(@RequestBody User user){
        addUser(user);
        return "success";
    }

    @RequestMapping("/DBauthenticate")
    public boolean DBauthHandler(@RequestBody LoginDetails loginDetails){
        return grantAccess(loginDetails.getEmail(),loginDetails.getPassword());
    }
}
