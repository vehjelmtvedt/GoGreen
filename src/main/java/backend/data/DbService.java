package backend.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service("DbService")
@Transactional
public class DbService {

    @Autowired
    private UserRepository users;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(DbService.class, args);
    }

    /**
     * .
     * Returns a List of all users.
     *
     * @return List of all users
     */
    List<User> getAllUsers() {
        return users.findAll();
    }


    /**
     * .
     * Adds a new user to the database, also encoding the password of the User object (if new User)
     *
     * @param user - User object to add
     */
    public void addUser(User user) {
        // New User, encrypt password
        if (getUser(user.getEmail()) == null) {
            user.setPassword(encodePassword(user.getPassword()));
        }

        users.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder().encode(password);
    }

    /**
     * .
     * Returns true or false whether to grant access to user with specified login details
     *
     * @param email    - input e-mail
     * @param password - input password
     * @return true if access granted
     */
    public boolean grantAccess(String email, String password) {
        User user = getUser(email);

        if (user == null) {
            return false;
        }

        return passwordEncoder().matches(password, user.getPassword());
    }

    /**
     * .
     * Deletes a user from the database (by e-mail)
     *
     * @param email - e-mail of the User to delete
     */
    void deleteUser(String email) {
        users.deleteById(email);
    }

    /**
     * .
     * Gets a user from the database (by e-mail)
     *
     * @param email - e-mail of the user
     * @return User object (password encoded!), or null if not present
     */
    public User getUser(String email) {
        // User may not be present in the database
        Optional<User> user = users.findById(email);

        // Returns user if found, else returns null
        return user.orElse(null);
    }

    /**
     * .
     * Gets a user from the database (by username)
     *
     * @param username - Username of the User
     * @return User object (password encoded!), or null if not present
     */
    User getUserByUsername(String username) {
        // User may not be present in the database
        Optional<User> user = users.findByUsername(username);

        // Returns user if found, else returns null
        return user.orElse(null);
    }

    /**
     * .
     * Befriends two users
     *
     * @param email1 - e-mail of first User
     * @param email2 - e-mail of second User
     */
    void befriendUsers(String email1, String email2) {
        User user1 = getUser(email1);
        User user2 = getUser(email2);

        // Make sure both users exist
        if (user1 != null && user2 != null) {
            // --- Logic to be filled by Vetle ---

            // Update changes in database
            users.save(user1);
            users.save(user2);
        }
    }

    /**
     * .
     * Adds a friend request to a user's list of friend requests
     *
     * @param senderEmail   - The e-mail of the friend request sender
     * @param receiverEmail - The e-mail of the user receiving the request
     */
    void addFriendRequest(String senderEmail, String receiverEmail) {
        User sender = getUser(senderEmail);
        User receiver = getUser(receiverEmail);

        if (sender != null && receiver != null) {
            // --- Logic to be filled by Vetle ---

            // Update only the User that received the friend request
            users.save(receiver);
        }
    }

    /**
     * .
     * Rejects a friend request of a specific user
     *
     * @param userEmail         - the user rejecting the friend request
     * @param rejectedUserEmail - the user whose friend request should be rejected
     */
    void rejectFriendReqeuest(String userEmail, String rejectedUserEmail) {
        User user = getUser(userEmail);
        User rejectedUser = getUser(rejectedUserEmail);

        if (user != null && rejectedUser != null) {
            // -- Logic to be filled by Vetle --

            // Update only the User that rejected the friend request
            users.save(user);
        }
    }

    /**
     * .
     * Finds all usernames matching specified string
     *
     * @param username - part of username to match
     * @return A list of strings containing all matching usernames
     */
    List<String> getMatchingUsers(String username) {
        String usernamePattern = "/$%s/$";
        String regexPattern = String.format(username, usernamePattern);

        return mongoTemplate.find(
                new Query(Criteria.where("username") // Compare against username
                        .regex(regexPattern, "i")),
                // Check for pattern contained (case insensitive)
                User.class) // result as User Object
                .stream() // Convert to Stream
                .map(User::getUsername) // Map User to Username
                .collect(Collectors.toList()); // Return result as List
    }

    /*
     * Reserved for leaderboard queries
     */
    /*    List<String> getTopUsers(int top) {
            return mongoTemplate.find(
                    new Query()
                            .with(new Sort(Sort.Direction.DESC, "username"))
                            // sort in descending order by username
                            .limit(top), // return required number of users
                    User.class) // result as User Object
                    .stream() // Convert to Stream
                    .map(User::getUsername) // Map User to Username
                    .collect(Collectors.toList()); // Return result as List
        }*/

    /**
     * .
     * Gets users' friends
     */
    List<User> getFriends(String email) {
        User user = getUser(email);

        if (user == null) {
            return new ArrayList<>(); // return empty list
        } else {
            // Query that returns a list of all the user's friends
            return mongoTemplate.find(
                    new Query(Criteria.where("email") // Compare against User email
                            .in(user.getFriends())), // Email must be in users friend list
                    User.class); // Resulting Object type User
        }
    }
}
