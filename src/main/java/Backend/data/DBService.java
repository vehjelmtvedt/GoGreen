package Backend.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DBService")
@Transactional
public class DBService
{
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
        SpringApplication.run(DBService.class, args);
    }

    //REMOVE THIS: ONLY FOR TESTING
    public UserRepository getAllUsers() {
        return this.users;
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

    /** Deletes a user from the database (by email) */
    public void deleteUser(String email) { users.deleteById(email); }

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
            return new ArrayList<User>(); // return empty list
        else
        {
            // Query that returns a list of all the user's friends
            return mongoTemplate.find(
                    new Query(Criteria.where("email") // Compare against User email
                        .in(user.getFriends())), // Email must be in users friend list
                    User.class); // Resulting Object type User
        }
    }
}
