package frontend;

import backend.data.LoginDetails;
import backend.data.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Requests {

    /**.
     * Send request to server
     * @param type type of post request
     * @param loginDetails user login details
     * @param user user details for sign up
     * @return String response from server
     */
    public static String sendRequest(int type, LoginDetails loginDetails, User user) {

        String url;
        if (type == 1) {
            url = "http://localhost:8080/login";
        } else {
            url = "http://localhost:8080/signup";
        }

        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response;

        if (type == 1) {
            response = rest.postForEntity(url, loginDetails, String.class);
        } else {
            response = rest.postForEntity(url,user,String.class);
        }
        System.out.println(response);
        return response.getBody();
    }

    /**.
     * Send a request to the server to verify user input
     * @param type type of input to be verified
     * @param verify string containing the type to be verified
     * @return returns a boolean from server if the given input is not already being used
     */
    public static boolean requestValidate(int type, String verify) {
        String url;
        if (type == 1) {
            url = "http://localhost:8080/validateUsername";
        } else {
            url = "http://localhost:8080/validateEmail";
        }

        RestTemplate rest = new RestTemplate();
        ResponseEntity<Boolean> response;

        response = rest.postForEntity(url, verify, Boolean.class);

        System.out.println(response);
        if (response.hasBody()) {
            return response.getBody();
        } else {
            return false;
        }
    }
}
