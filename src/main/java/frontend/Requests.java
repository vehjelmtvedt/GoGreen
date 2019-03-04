package frontend;

import backend.data.LoginDetails;
import backend.data.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

public class Requests {

    public static String getrequest(String url) { //add parameter feature in the future.
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public static String signupRequest(User user) {
        String url = "http://localhost:8080/signup";

        ObjectMapper mapper = new ObjectMapper();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        try {
            String jsonInString = mapper.writeValueAsString(user);
            HttpEntity <String> httpEntity = new HttpEntity <String> (jsonInString, httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(url, httpEntity, String.class);
            System.out.println(response);
            return "Success";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "ERROR";
        }




    }


}
