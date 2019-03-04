package frontend;

import backend.data.LoginDetails;
import backend.data.User;
import com.google.gson.Gson;
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

        try{
            if (type == 1) {
                url = "http://localhost:8080/login";
            } else {
                url = "http://localhost:8080/signup";
            }

            RestTemplate rest = new RestTemplate();
            Gson gson  = new Gson();

            if (type == 1) {
                String json = gson.toJson(loginDetails);
                rest.postForEntity(url, json, String.class);
                return url;
            } else {
                String json = gson.toJson(user);
                rest.postForEntity(url, json, String.class);
                return url;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
