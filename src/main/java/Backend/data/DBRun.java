package Backend.data;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

// TBD: Combine this with Server, possibly?

@SpringBootApplication
public class DBRun implements CommandLineRunner
{
    @Autowired
    private UserRepository users;

    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args)
    {
        SpringApplication.run(DBRun.class, args);
    }

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

    @Override
    public void run(String... args) throws Exception
    {

    }
}
