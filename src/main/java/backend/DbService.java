package backend;

import backend.repos.AchievementRepository;
import backend.repos.UserRepository;
import backend.repos.UserStatisticsRepository;

import data.Achievement;
import data.AchievementsLogic;
import data.Activity;
import data.User;
import data.UserAchievement;
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
import tools.DateUtils;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service("DbService")
@Transactional
public class DbService {
    private static final int maxLoginStreak = 3;

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
        if (getUserByEmail(user.getEmail()) == null) {
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
        if (user == null || user.getLoginStreak() == maxLoginStreak) {
            return null;
        }
        if (passwordEncoder().matches(password, user.getPassword())) {
            // Update last login date to current (server) time
            user.setLastLoginDate(DateUtils.instance.dateToday());
            user.resetLoginStreak();
            return user;
        }

        user.incLoginStreak();
        users.save(user);
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
    public User getUserByEmail(String identifier) {
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
     * Gets user from the database (by identifier [email/password])
     *
     * @param identifier - identifier (can be e-mail or username
     * @return - User object (password encoded!), or null if not present
     */
    public User getUser(String identifier) {
        User user = getUserByEmail(identifier);

        if (user == null) {
            user = getUserByUsername(identifier);
        }

        return user;
    }

    /**
     * .
     * Befriends two users
     *
     * @param accepting - username of User accepting the request.
     * @param requester - username of User who sent the request.
     */
    public boolean acceptFriendRequest(String requester, String accepting) {
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

            //checks if an achievement is completed by adding a friend
            addAchievement(acceptingUser, AchievementsLogic.checkOther(acceptingUser),
                    DateUtils.instance.dateToday());
            addAchievement(requestingUser, AchievementsLogic.checkOther(requestingUser),
                    DateUtils.instance.dateToday());

            return true;
        } else {
            return false;
        }
    }

    /**
     * .
     * Adds a friend request to a user's list of friend requests
     *
     * @param senderUsername   - The username of the friend request sender
     * @param receiverUsername - The username of the user receiving the request
     */
    public boolean addFriendRequest(String senderUsername, String receiverUsername) {
        User sender = getUserByUsername(senderUsername);

        if (sender != null && sender.getFriendRequests().contains(receiverUsername)) {
            acceptFriendRequest(receiverUsername, senderUsername);
        }

        User receiver = getUserByUsername(receiverUsername);
        if (sender != null && receiver != null) {
            receiver.newFriendRequest(sender.getUsername());
            // Update only the User that received the friend request
            users.save(receiver);
            return true;
        } else {
            return false;
        }
    }

    /**
     * .
     * Rejects a friend request of a specific user
     *
     * @param rejectedUser  - the user rejecting the friend request
     * @param rejectingUser - the user whose friend request should be rejected
     */

    public boolean rejectFriendRequest(String rejectedUser, String rejectingUser) {
        User rejected = getUserByUsername(rejectedUser);
        User rejecting = getUserByUsername(rejectingUser);

        if (rejected != null && rejectingUser != null) {

            rejecting.deleteFriendRequest(rejectedUser);
            // Update only the User that rejected the friend request
            users.save(rejecting);
            return true;
        } else {
            return false;
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

        // check if an achievement is completed by this activity
        addAchievement(returned, AchievementsLogic.checkFoodActivity(
                returned, activity), activity.getDate());
        addAchievement(returned, AchievementsLogic.checkTranspostActivity(
                returned, activity), activity.getDate());
        addAchievement(returned, AchievementsLogic.checkTranspostActivity1(
                returned, activity), activity.getDate());
        addAchievement(returned , AchievementsLogic.checkotherActivities(
                returned , activity ), activity.getDate());

        // adds points to the user
        addCO2Points(returned, activity.getCarbonSaved());

        //checks the users level
        addAchievement(returned, AchievementsLogic.checkLevel(returned),
                DateUtils.instance.dateToday());

        //checks the leaderboards
        addAchievement(returned , checkLeaderboards(returned) , DateUtils.instance.dateToday());

        //returned.getProgress().hasChangedCheck();

        addUser(returned);

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
                        // sort in descending order by carbon saved
                        .limit(top), // return required number of users
                User.class); // result as User Object
    }

    /**
     * .
     * Gets User's friends
     *
     * @param identifier - identifier (e-mail/username) of User
     * @return - List of User's friends
     */
    public List<User> getFriends(String identifier) {
        User user = getUser(identifier);

        if (user == null) {
            return new ArrayList<>(); // return empty list
        } else {
            // Query that returns a list of all the user's friends
            return mongoTemplate.find(
                    new Query(Criteria.where("username") // Compare against User email
                            .in(user.getFriends())), // Username must be in users friend list
                    User.class); // Resulting Object type User
        }
    }

    /**
     * .
     * Gets User's top friends
     *
     * @param identifier - identifier (e-mail/username) of User
     * @param top        - Number of top friends to return
     * @return - top n friends of user
     */
    public List<User> getTopFriends(String identifier, int top) {
        User user = getUser(identifier);

        if (user == null) {
            return new ArrayList<>(); // return empty list
        } else {
            // Query that returns a list of all the user's top n friends
            return mongoTemplate.find(
                    new Query(Criteria.where("username") // Compare against User email
                            .in(user.getFriends())) // Username must be in users friend list
                            .with(new Sort(Sort.Direction.DESC, "totalCarbonSaved"))
                            // sort in descending order by carbon saved
                            .limit(top), // return required number of users,
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

    /**
     * Edits and updates user.
     *
     * @param user      the user to update
     * @param fieldName name of the field to update
     * @param newValue  new value of the field
     * @return the updated User
     */
    public User editProfile(User user, String fieldName, Object newValue) {
        System.out.println(fieldName);
        try {
            if (fieldName.equals("password")) {
                newValue = encodePassword((String) newValue);
                System.out.println(newValue);
            }
            Field field = user.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(user, newValue);
            field.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return null;
        }
        users.save(user);
        return user;
    }

    /**
     * .
     * Returns the total amount of CO2 saved by all the users
     *
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

        //checks the users level
        addAchievement(user, AchievementsLogic.checkLevel(user),
                DateUtils.instance.dateToday());

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

    /**
     * this method checks every achievements if its already in the List, if not add it.
     *
     * @param user current user
     * @param ids  achievements to check
     * @param date date to add
     */
    public void addAchievement(User user, ArrayList<Integer> ids, Date date) {

        for (Integer id : ids) {

            boolean alreadythere = false;

            for (UserAchievement userAchievement : user.getProgress().getAchievements()) {

                if (userAchievement.getId() == id) {

                    alreadythere = true;
                    break;
                }

            }

            // I tried .contains()  is uses the equals method
            // for user Achievement  that checks the id along with the date ,
            // this means that same achievements with different dates are going to be added

            if (!alreadythere) {

                UserAchievement userAchievement = new UserAchievement(id, true, date);

                user.getProgress().getAchievements().add(userAchievement);

                String idstring = Integer.toString(id);

                List<Achievement> list = getAchievements();

                user.getProgress().addPoints(list.get(id).getBonus());

                //user.getProgress().hasChangedCheck();
            }
        }

        //addUser(user);
    }

    /**
     * addes to the points the amount of co2 save.
     * every one co2 unite is worth 1 point
     *
     * @param user        user to add points to
     * @param carbonsaved co2 saved
     */
    public void addCO2Points(User user, double carbonsaved) {

        //user.getProgress().hasChangedCheck();

        user.getProgress().setPoints(user.getProgress().getPoints() + carbonsaved * 300);


    }

    /**
     * .
     * Gets the rank in terms of CO2 saved of the specified User
     *
     * @param identifier - Identifier of the User (either e-mail or username)
     * @return - Rank of the User (integer)
     */
    public int getUserRank(String identifier) {
        User user = getUser(identifier);

        // Invalid User specified
        if (user == null) {
            return -1;
        }

        double carbon = getUser(identifier).getTotalCarbonSaved();

        return (int) mongoTemplate.count(new Query( // Get count of matching documents
                Criteria.where("totalCarbonSaved") // Compare totalCarbonSaved of other Users
                .gt(carbon)), // Carbon Saved of queried Users should be greater
                User.class) // Search in User collection
                + 1; // Add 1 (to count in the User itself)
    }

    /**
     * for leader boards achievement logic.
     *
     * @param user user to check
     * @return array of ids
     */
    public ArrayList<Integer> checkLeaderboards(User user) {

        ArrayList<Integer> results = new ArrayList();

        int rank = getUserRank(user.getUsername());

        //Reach the top ten 26
        if (rank <= 10) {
            results.add(26);
        }
        //Reach the top five Users id 28
        if (rank <= 5) {
            results.add(27);
        }
        //Reach third place on the leader board id 18
        if (rank <= 3) {
            results.add(18);
        }
        //Reach second place on the leader board id 11
        if (rank <= 2) {
            results.add(11);
        }
        //Reach the top of the leader board id 10
        if (rank == 1) {
            results.add(10);
        }

        return results;

    }

}
