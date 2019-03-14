package backend.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service("DbService")
@Transactional
public class DbService {

    @Autowired
    private UserRepository users;

    @Autowired
    private AchievementRepository achievements;

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
     * @param identifier - input e-mail
     * @param password   - input password
     * @return true if access granted
     */
    public User grantAccess(String identifier, String password) {
        User user = getUser(identifier);
        System.out.println(user);
        if (user == null) {
            user = getUserByUsername(identifier);
        }

        if (user == null) {
            return null;
        }

        System.out.println(user);

        if (passwordEncoder().matches(password, user.getPassword())) {
            // Update last login date to current (server) time
            user.setLastLoginDate(Calendar.getInstance().getTime());
            return user;
        }

        return null;
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
     * @param identifier - e-mail/username of the user
     * @return User object (password encoded!), or null if not present
     */
    public User getUser(String identifier) {
        // User may not be present in the database
        Optional<User> user = users.findById(identifier);

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
    public User getUserByUsername(String username) {
        // User may not be present in the database
        Optional<User> user = users.findByUsername(username);
        // Returns user if found, else returns null
        return user.orElse(null);
    }

    /**
     * .
     * Befriends two users
     *
     * @param accepting - username of User accepting the request.
     * @param requester - username of User who sent the request.
     */
    public User acceptFriendRequest(String requester, String accepting) {
        User requestingUser = getUserByUsername(requester);
        User acceptingUser = getUserByUsername(accepting);

        // Make sure both users exist
        if (requestingUser != null && acceptingUser != null) {
            requestingUser.addFriend(acceptingUser.getUsername());
            acceptingUser.addFriend(requestingUser.getUsername());
            acceptingUser.deleteFriendRequest(requester);
            // Update changes in database
            users.save(requestingUser);
            users.save(acceptingUser);
            return acceptingUser;
        } else {
            return null;
        }
    }

    /**
     * .
     * Adds a friend request to a user's list of friend requests
     *
     * @param senderUsername   - The username of the friend request sender
     * @param receiverUsername - The username of the user receiving the request
     */
    public User addFriendRequest(String senderUsername, String receiverUsername) {
        User sender = getUserByUsername(senderUsername);
        User receiver = getUserByUsername(receiverUsername);

        if (sender != null && receiver != null) {
            receiver.newFriendRequest(sender.getUsername());
            // Update only the User that received the friend request
            users.save(receiver);
            return receiver;
        } else {
            return null;
        }
    }

    /**
     * .
     * Rejects a friend request of a specific user
     *
     * @param rejectedUser  - the user rejecting the friend request
     * @param rejectingUser - the user whose friend request should be rejected
     */

    public User rejectFriendRequest(String rejectedUser, String rejectingUser) {
        User rejected = getUserByUsername(rejectedUser);
        User rejecting = getUserByUsername(rejectingUser);

        if (rejected != null && rejectingUser != null) {

            rejecting.deleteFriendRequest(rejectedUser);
            // Update only the User that rejected the friend request
            users.save(rejecting);
            return rejecting;
        } else {
            return null;
        }
    }

    /**
     * .
     * Finds all usernames matching specified string
     *
     * @param username - part of username to match
     * @return A list of strings containing all matching usernames
     */
    protected List<String> getMatchingUsers(String username) {
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
    protected List<User> getTopUsers(int top) {
        return mongoTemplate.find(
                new Query()
                        .with(new Sort(Sort.Direction.DESC, "totalCarbonSaved"))
                        // sort in descending order by username
                        .limit(top), // return required number of users
                User.class); // result as User Object
    }

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

    /**
     * .
     * Returns the list of all achievements.
     *
     * @return List of all achievements
     */
    public List<Achievement> getAchievements() {
        return achievements.findAll();
    }
}
