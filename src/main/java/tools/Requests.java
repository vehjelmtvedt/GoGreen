package tools;

import data.Achievement;
import data.Activity;
import data.LoginDetails;
import data.User;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class Requests {
    public static Requests instance = new Requests();

    protected String url;
    private RestTemplate restTemplate;

    protected Requests() {
        url = System.getProperty("remote.url");

        // Uncomment to test via remote server
        //url = "https://cse38-go-green.herokuapp.com";

        if (url != null && !url.contains("localhost")) {
            buildSecureRestTemplate(url);
        } else {
            buildInsecureRestTemplate();
        }
    }

    /**.
     * Sets the REST Template to an HTTP REST Template that connects to localhost with port 8080
     */
    private void buildInsecureRestTemplate() {
        url = "http://localhost:8080";
        restTemplate = new RestTemplate();
    }


    /**.
     * Sets the REST Template to an HTTPS secure REST Template that connects to remote Heroku host
     * @param remoteUrl - Remote Host URL
     */
    private void buildSecureRestTemplate(String remoteUrl) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);

        restTemplate = new RestTemplate(requestFactory);

        url = remoteUrl;
    }

    /**
     * Sends signup request to the server.
     * @param user - user signing up
     * @return response from the server.
     */
    public String signupRequest(User user) {
        return restTemplate.postForEntity(url + "/signup",user,String.class).getBody();
    }

    /**
     * Sends login request to the server.
     * @param loginDetails - login details of user wanting to log in
     * @return response from server
     */
    public User loginRequest(LoginDetails loginDetails) {
        return restTemplate.postForEntity(url + "/login", loginDetails, User.class).getBody();
    }

    /**
     * Sends a friend request from one user to another.
     * @param sender - user sending the friend request
     * @param receiver - user receiving the friend request
     * @return - returns the User who sent the request
     */
    public boolean sendFriendRequest(String sender, String receiver) {
        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/friendrequest")
                .queryParam("sender", sender)
                .queryParam("receiver", receiver);

        return restTemplate.getForEntity(uriBuilder.toUriString(), boolean.class).getBody();
    }

    /**
     * Request from a user to accept a friend request.
     * @param sender - user who sent the friend request
     * @param accepting - user who accepts the request
     * @return - User accepting the friend request
     */
    public boolean acceptFriendRequest(String sender, String accepting) {
        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/acceptfriend")
                .queryParam("sender", sender)
                .queryParam("accepting", accepting);

        return restTemplate.getForEntity(uriBuilder.toUriString(), boolean.class).getBody();
    }

    /**
     * Request from a user to reject a friend request.
     * @param sender - user who sent the request
     * @param rejecting - user who is rejecting the request
     * @return - User rejecting the friend request
     */
    public boolean rejectFriendRequest(String sender, String rejecting) {
        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/rejectfriend")
                .queryParam("sender", sender)
                .queryParam("rejecting", rejecting);

        return restTemplate.getForEntity(uriBuilder.toUriString(), boolean.class).getBody();
    }

    /**
     * Sends request to server/db to check if user is valid.
     * @param identifier - username or email
     * @return - returns true if user is validated, false if not.
     */
    public boolean validateUserRequest(String identifier) {
        return restTemplate.postForEntity(url + "/validateUser",
                 identifier, String.class).getBody().equals("OK");
    }

    /**
     * Adds an activity to a User.
     * @param activity - what activity to add to the user.
     * @param username - of the User to add an activity
     * @return - User the activity was added to.
     */
    public User addActivityRequest(Activity activity, String username) {
        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/addActivity")
                .queryParam("identifier", username);

        return restTemplate.postForEntity(uriBuilder.toUriString(), activity, User.class).getBody();
    }

    /**
     * Gets matching user based on keyword.
     * @param keyword - keyword to match
     * @param loginDetails - to authenticate
     * @return - a list of users matching the keyword
     */
    public List getMatchingUsersRequest(String keyword, LoginDetails loginDetails) {
        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/searchUsers")
                .queryParam("keyword", keyword);

        return restTemplate.postForEntity(
                uriBuilder.toUriString(), loginDetails, List.class).getBody();
    }

    /**
     * Requets to retrieve list of friends.
     * @param loginDetails - login details of User requesting their friends.
     * @return - list of friends.
     */
    public List<User> getFriends(LoginDetails loginDetails) {
        ParameterizedTypeReference<List<User>> typeRef =
                new ParameterizedTypeReference<List<User>>() {
        };
        return restTemplate.exchange(url + "/getFriends",
                HttpMethod.POST, new HttpEntity<>(loginDetails), typeRef).getBody();
    }

    /**
     * Request to get all achievements.
     * @return a list of achievements
     */
    public List<Achievement> getAllAchievements() {
        ParameterizedTypeReference<List<Achievement>> typeRef =
                new ParameterizedTypeReference<List<Achievement>>() {};
        return restTemplate.exchange(url + "/getAllAchievements",HttpMethod.GET,
                new HttpEntity<>(""),typeRef).getBody();

    }

    /**
     * Request to get the Top Users.
     * @param loginDetails username and password for auth
     * @param top a limit on the number of users to return
     * @return returns a list of top "top" users
     */
    public List<User> getTopUsers(LoginDetails loginDetails, int top) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/getTopUsers")
                .queryParam("top", top);

        ParameterizedTypeReference<List<User>> typeRef =
                new ParameterizedTypeReference<List<User>>() {};
        return restTemplate.exchange(uriBuilder.toUriString(),HttpMethod.POST,
                new HttpEntity<LoginDetails>(loginDetails),typeRef).getBody();
    }

    /**
     * Request to edit profile.
     * @param loginDetails for auth
     * @param fieldName name of the field being changed
     * @param newValue new value for the field
     * @return returns the updated user
     */
    public User editProfile(LoginDetails loginDetails, String fieldName, Object newValue) {
        System.out.println(newValue.getClass().getName());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/editProfile")
                .queryParam("fieldName",fieldName).queryParam("newValue", newValue)
                .queryParam("typeName",newValue.getClass().getSimpleName());
        return restTemplate.postForEntity(uriBuilder.toUriString(),
                loginDetails,User.class).getBody();
    }  
    
    /**
     * Request to rest password.
     * @param email email of user.
     * @param answer answer of the security question
     * @param newPass changed password
     * @return - true if successfully changed password, false otherwise
     */
    public Boolean forgotPass(String email, int questionid, String answer, String newPass) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url + "/forgotPass")
                .queryParam("email",email).queryParam("answer",answer)
                        .queryParam("questionid",questionid).queryParam("newPass",newPass);

        return restTemplate.getForEntity(uriBuilder.toUriString(),Boolean.class).getBody();
    }

    /**
     * get total Users.
     * @return number of total users
     */
    public int getTotalUsers() {
        return restTemplate.getForEntity(url + "/getTotalUsers", int.class).getBody();
    }

    /**
     * get total CO2 saved.
     * @return total amount of CO2 saved
     */
    public double getTotalCO2Saved() {
        return restTemplate.getForEntity(url + "/getTotalCO2Saved",double.class).getBody();
    }

    /**
     * get average CO2 saved.
     * @return average CO2 saved
     */
    public double getAverageCO2Saved() {
        return restTemplate.getForEntity(url + "/getAverageCO2Saved", double.class).getBody();
    }

    /**
     * get rank of the user.
     * @param loginDetails authentication and identity of user
     * @return the rank of the user
     */
    public Integer getUserRanking(LoginDetails loginDetails) {
        return restTemplate.postForEntity(url + "/getRank",loginDetails,int.class).getBody();
    }


}
