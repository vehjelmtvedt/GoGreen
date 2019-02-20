package Backend;

import Backend.data.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import com.fasterxml.jackson.databind.ObjectMapper;




@RestController
public class RequestHandler {


    @RequestMapping("/greeting")
    public String respond() {
        return "Success!";
    }

    //Handles authentication
    @RequestMapping("/login")
    public String loginController(@RequestBody LoginDetails loginDetails) {

        try {
            HttpURLConnection con =
                    (HttpURLConnection) new URL(null, "http://localhost:8090/DBauthenticate")
                            .openConnection();

            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(loginDetails);

            con.getOutputStream().write(json.getBytes(Charset.forName("UTF-8")));
            con.getOutputStream().flush();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            if(response.toString().equals("success"))
                return "auth success";
            else
                return "auth failed";
        }
        catch(Exception E){
            System.out.println(E);
            return "error try again";
        }
    }

    //Handles creating a new user
    @RequestMapping("/signup")
    public String signupController(@RequestBody User user){
        try {
            HttpURLConnection con =
                    (HttpURLConnection) new URL(null, "http://localhost:8090/DBaddUser").openConnection();

            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(user);

            con.getOutputStream().write(json.getBytes(Charset.forName("UTF-8")));
            con.getOutputStream().flush();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            if(response.toString().equals("success"))
                return "User added successfully";
            else
                return "Error existing email id";
        }
        catch (IOException e) {
            e.printStackTrace();
            return "error try again";
        }

    }
}