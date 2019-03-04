package frontend;

import backend.data.LoginDetails;
import backend.data.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    public static User getUserRequest(String identifier) {
        String url = "http://localhost:8080/getUser";

        RestTemplate rest = new RestTemplate();
        ResponseEntity<User> response;

        response = rest.getForEntity(url, User.class);
        System.out.println(response);
        return response.getBody();
    }

    public static String sendFriendRequest(String sender, String receiver) {

        String url = "http://localhost:8080/friendrequest";

        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders requestHeaders = new HttpHeaders();

        //request entity is created with request headers
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);

        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sender", sender)
                .queryParam("receiver", receiver);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    public static String acceptFriendRequest(String sender, String receiver) {

        String url = "http://localhost:8080/friendrequest";

        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders requestHeaders = new HttpHeaders();

        //request entity is created with request headers
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);

        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sender", sender)
                .queryParam("receiver", receiver);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }
}
