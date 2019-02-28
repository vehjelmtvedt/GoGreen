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


    /**.
     * Adds a user to the database, also encoding the password of the User object
     * @param user - User object to add
     */
    public void addUser(User user) {
        user.setPassword(encodePassword(user.getPassword()));
        users.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder().encode(password);
    }

    /**.
     * Returns true or false whether to grant access to user with specified login details
     * @param email - input e-mail
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

    /**.
     * Deletes a user from the database (by e-mail)
     * @param email - e-mail of the User to delete
     */
    void deleteUser(String email) {
        users.deleteById(email);
    }

    /**.
     * Gets a user from the database (by e-mail)
     * @param email - e-mail of the user
     * @return User object (password encoded!), or null if not present
     */
    public User getUser(String email) {
        // User may not be present in the database
        Optional<User> user = users.findById(email);


        // Returns user if found, else returns null
        return user.orElse(null);
    }

    /**.
     * Gets a user from the database (by username)
     * @param username - Username of the User
     * @return User object (password encoded!), or null if not present
     */
    User getUserByUsername(String username) {
        // User may not be present in the database
        Optional<User> user = users.findByUsername(username);

        // Returns user if found, else returns null
        return user.orElse(null);
    }

    /**.
     * Befriends two users
     * @param accepting - username of User accepting the request.
     * @param requester - username of User who sent the request.
     */
    public String acceptFriendRequest(String requester, String accepting) {
        User requestingUser = getUserByUsername(requester);
        User receivingUser = getUserByUsername(accepting);

        // Make sure both users exist
        if (requestingUser != null && receivingUser != null) {
            requestingUser.addFriend(receivingUser.getUsername());
            receivingUser.addFriend(requestingUser.getUsername());
            receivingUser.deleteFriendRequest(requester);
            // Update changes in database
            users.save(requestingUser);
            users.save(receivingUser);
            return "OK";
        } else {
            return "Invalid username";
        }
    }

    /**.
     * Adds a friend request to a user's list of friend requests
     * @param senderUsername - The username of the friend request sender
     * @param receiverUsername - The username of the user receiving the request
     */
    public String addFriendRequest(String senderUsername, String receiverUsername) {
        User sender = getUserByUsername(senderUsername);
        User receiver = getUserByUsername(receiverUsername);

        if (sender != null && receiver != null) {
            receiver.newFriendRequest(sender.getUsername());
            // Update only the User that received the friend request
            users.save(receiver);
            users.save(sender);
            return "OK";
        } else {
            return "Invalid username";
        }
    }
    /**.
     * Rejects a friend request of a specific user
     * @param rejectedUser - the user rejecting the friend request
     * @param rejectingUser - the user whose friend request should be rejected
     */

    public String rejectFriendRequest(String rejectedUser, String rejectingUser) {
        User rejected = getUserByUsername(rejectedUser);
        User rejecting = getUserByUsername(rejectingUser);

        if (rejected != null && rejectingUser != null) {

            rejecting.deleteFriendRequest(rejectedUser);
            // Update only the User that rejected the friend request
            users.save(rejecting);
            return "OK";
        } else {
            return "Invalid username";
        }
    }

    /**.
     * Gets users' friends
     */
    List<User> getFriends(String email) {
        User user = getUser(email);

        if (user == null) {
            return new ArrayList<User>(); // return empty list
        } else {
            // Query that returns a list of all the user's friends
            return mongoTemplate.find(
                    new Query(Criteria.where("email") // Compare against User email
                            .in(user.getFriends())), // Email must be in users friend list
                    User.class); // Resulting Object type User
        }
    }
}
