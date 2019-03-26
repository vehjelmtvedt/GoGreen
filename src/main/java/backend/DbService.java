package backend;

import backend.repos.AchievementRepository;
import backend.repos.UserRepository;
import backend.repos.UserStatisticsRepository;
import data.Achievement;
import data.Activity;
import data.User;
import data.UserStatistics;
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
    private UserStatisticsRepository userStatistics;

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
            updateTotalUsersStatistics();
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
        Optional<User> user = users.findById(email);

        if (user.isPresent()) {
            removeUserUpdateStatistics(user.get());
            users.deleteById(email);
        }
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
     * Adds specified Activity to specified User, if the User exists.
     *
     * @param username - Username of the User to add the Activity to
     * @param activity - Activity to add to User
     * @return - Updated User
     */
    public User addActivityToUser(String username, Activity activity) {
        User returned = getUserByUsername(username);

        if (returned == null || activity == null) {
            return null;
        }

        returned.addActivity(activity);
        returned.setTotalCarbonSaved(returned.getTotalCarbonSaved() + activity.getCarbonSaved());
        addUser(returned);
        updateTotalCo2SavedStatistics(returned);
        return returned;
    }

    /**
     * .
     * Finds all usernames matching specified string
     *
     * @param username - part of username to match
     * @return A list of strings containing all matching usernames
     */
    public List<String> getMatchingUsers(String username) {
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

    /**
     * Returns top users.
     *
     * @param top to return a "top" number of users
     * @return top n users
     */
    public List<User> getTopUsers(int top) {
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
    public List<User> getFriends(String identifier) {
        User user = getUser(identifier);

        if (user == null) {
            user = getUserByUsername(identifier);
        }

        if (user == null) {
            return new ArrayList<>(); // return empty list
        } else {
            // Query that returns a list of all the user's friends
            return mongoTemplate.find(
                    new Query(Criteria.where("username") // Compare against User email
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

    /**.
     * Returns the total amount of CO2 saved by all the users
     * @return - Total amount of CO2 saved
     */
    public double getTotalCO2Saved() {
        // Querying is too slow (keeping for reference)
        /*// Create aggregation query
        Aggregation userAggregation = Aggregation.newAggregation(
                Aggregation.group()
                        .sum("totalCarbonSaved")
                        .as("totalCarbonSaved"));

        // Aggregate result to single User
        User sumUser = mongoTemplate.aggregate(userAggregation, User.class, User.class)
                .getUniqueMappedResult();

        // No Users found
        if (sumUser == null) {
            return 0.0;
        }

        // Return sum result
        return sumUser.getTotalCarbonSaved();*/

        return getTotalStatistics().getTotalCO2Saved();
    }

    public int getTotalUsers() {
        return getTotalStatistics().getTotalUsers();
    }

    public double getAverageCO2Saved() {
        return getTotalStatistics().getAverageCO2Saved();
    }

    private UserStatistics getTotalStatistics() {
        return mongoTemplate.findById("all", UserStatistics.class);
    }

    private void updateTotalCo2SavedStatistics(User user) {
        Activity recentActivity = user.getActivities().get(user.getActivities().size() - 1);

        UserStatistics allStatistics = getTotalStatistics();
        allStatistics.addTotalCo2Saved(recentActivity.getCarbonSaved());

        userStatistics.save(allStatistics);
    }

    private void updateTotalUsersStatistics() {
        UserStatistics allStatistics = getTotalStatistics();
        allStatistics.incrementTotalUsers();

        userStatistics.save(allStatistics);
    }

    private void removeUserUpdateStatistics(User user) {
        UserStatistics allStatistics = getTotalStatistics();
        allStatistics.deleteUser(user);

        userStatistics.save(allStatistics);
    }
}
