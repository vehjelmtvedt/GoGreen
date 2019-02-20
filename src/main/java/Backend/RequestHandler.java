package Backend;

import Backend.data.*;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Resource;


@RestController
public class RequestHandler {
    @Resource(name="DBService")
    private DBService dbService;

    @RequestMapping("/greeting")
    public String respond() {
        return "TestGreeting";
    }

    //Handles authentication
    @RequestMapping("/login")
    public String loginController(@RequestBody LoginDetails loginDetails) {

        if(dbService.grantAccess(loginDetails.getEmail(),loginDetails.getPassword()))
            return "success";
        return "failure";
    }

    //Handles creating a new user
    @RequestMapping("/signup")
    public String signupController(@RequestBody User user){
        if(dbService.getUser(user.getEmail())!=null)
            return "user exists already";
        dbService.addUser(user);
        return "success";
        //return new ResponseEntity<>("Success", HttpStatus.OK);

    }
}




/*for future reference
HttpURLConnection con =
                    (HttpURLConnection) new URL(null, "http://localhost:8080/DBauthenticate")
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
 */