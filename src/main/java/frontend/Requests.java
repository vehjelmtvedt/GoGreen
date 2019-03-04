package frontend;

import backend.data.LoginDetails;
import backend.data.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Requests {

    public static String signupRequest(User user) {
        String url = "http://localhost:8080/signup";

        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response;

        response = rest.postForEntity(url,user,String.class);
        System.out.println(response);
        return response.getBody();

    }

    public static String loginRequest(LoginDetails loginDetails) {
        String url = "http://localhost:8080/login";

        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response;

        response = rest.postForEntity(url, loginDetails, String.class);
        System.out.println(response);
        return response.getBody();
    }

    
}
