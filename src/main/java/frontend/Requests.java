package frontend;

import backend.data.Activity;
import backend.data.LoginDetails;
import backend.data.User;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class Requests {

    /**
     * Sends signup request to the server.
     * @param user - user signing up
     * @return response from the server.
     */
    public static String signupRequest(User user) {
        String url = "http://localhost:8080/signup";
        RestTemplate rest = new RestTemplate();
        return rest.postForEntity(url,user,String.class).getBody();
    }

    /**
     * Sends login request to the server.
     * @param loginDetails - login details of user wanting to log in
     * @return response from server
     */
    public static User loginRequest(LoginDetails loginDetails) {
        String url = "http://localhost:8080/login";
        RestTemplate rest = new RestTemplate();
        return rest.postForEntity(url, loginDetails, User.class).getBody();
    }

    /**
     * Get User identified by username or email from server.
     * @param identifier - username or email
     * @return - the User of the identifier (if any)
     */
    public static User getUserRequest(String identifier) {
        String url = "http://localhost:8080/getUser";

        RestTemplate restTemplate = new RestTemplate();

        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("identifier", identifier);


        return restTemplate.getForEntity(uriBuilder.toUriString(), User.class).getBody();
    }

    /**
     * Sends a friend request from one user to another.
     * @param sender - user sending the friend request
     * @param receiver - user receiving the friend request
     * @return - returns the User who sent the request
     */
    public static User sendFriendRequest(String sender, String receiver) {

        String url = "http://localhost:8080/friendrequest";

        RestTemplate restTemplate = new RestTemplate();

        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sender", sender)
                .queryParam("receiver", receiver);

        return restTemplate.getForEntity(uriBuilder.toUriString(), User.class).getBody();
    }

    /**
     * Request from a user to accept a friend request.
     * @param sender - user who sent the friend request
     * @param accepting - user who accepts the request
     * @return - User accepting the friend request
     */
    public static User acceptFriendRequest(String sender, String accepting) {

        String url = "http://localhost:8080/acceptfriend";

        RestTemplate restTemplate = new RestTemplate();

        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sender", sender)
                .queryParam("accepting", accepting);

        return restTemplate.getForEntity(uriBuilder.toUriString(), User.class).getBody();
    }

    /**
     * Request from a user to reject a friend request.
     * @param sender - user who sent the request
     * @param rejecting - user who is rejecting the request
     * @return - User rejecting the friend request
     */
    public static User rejectFriendRequest(String sender, String rejecting) {

        String url = "http://localhost:8080/rejectfriend";

        RestTemplate restTemplate = new RestTemplate();

        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sender", sender)
                .queryParam("rejecting", rejecting);

        return restTemplate.getForEntity(uriBuilder.toUriString(), User.class).getBody();
    }

    /**
     * Sends request to server/db to check if user is valid.
     * @param identifier - username or email
     * @return - returns true if user is validated, false if not.
     */
    public static boolean validateUserRequest(String identifier) {
        String url = "http://localhost:8080/validateUser";
        RestTemplate rest = new RestTemplate();
        return rest.postForEntity(url, identifier, String.class).getBody().equals("OK");
    }

    /**
     * Adds an activity to a User.
     * @param activity - what activity to add to the user.
     * @param username - of the User to add an activity
     * @return - User the activity was added to.
     */
    public static User addActivityRequest(Activity activity, String username) {

        String url = "http://localhost:8080/addActivity";

        RestTemplate restTemplate = new RestTemplate();

        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("identifier", username);

        return restTemplate.postForEntity(uriBuilder.toUriString(), activity, User.class).getBody();
    }

    /**
     * Gets matching user based on keyword.
     * @param keyword - keyword to match
     * @param loginDetails - to authenticate
     * @return - a list of users matching the keyword
     */
    public static List getMatchingUsersRequest(String keyword, LoginDetails loginDetails) {
        String url = "http://localhost:8080/searchUsers";

        RestTemplate restTemplate = new RestTemplate();

        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("keyword", keyword);

        return restTemplate.postForEntity(
                uriBuilder.toUriString(), loginDetails, List.class).getBody();
    }
}
