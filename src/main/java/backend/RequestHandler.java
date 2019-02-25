package backend;

import backend.data.DbService;

import backend.data.LoginDetails;
import backend.data.User;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RequestHandler {
    @Resource(name = "DbService")
    private DbService dbService;

    /*    @RequestMapping("/greeting")
        public String respond() {
            return "TestGreeting";
        }*/

    /**
     * .
     * Login REST Method
     */
    @RequestMapping("/login")
    public String loginController(@RequestBody LoginDetails loginDetails) {

        if (dbService.grantAccess(loginDetails.getEmail(), loginDetails.getPassword())) {
            return "success";
        }
        return "failure";
    }

    /**
     * .
     * Sign-up REST Method
     */
    @RequestMapping("/signup")
    public String signupController(@RequestBody User user) {
        System.out.println(user);
        if (dbService.getUser(user.getEmail()) != null) {
            return "user exists already";
        }
        dbService.addUser(user);
        return "success";
        //return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    // Temporarily commented out to see real code coverage
    // This code will be revised on next Sprint
    /*    @RequestMapping("/addfriend")
        public String addFriend(@RequestParam String myEmail, @RequestParam String friendEmail) {
            //TODO: Add friend request feature in the future
            //POSSIBLE SOLUTION: store friend request in db, send to that user when accessing
            //friends page.
            //Keep it like this for now for testing.
            if (dbService.getUser(friendEmail) != null) {
                User currUser = dbService.getUser(myEmail);
                currUser.addFriend(friendEmail);
                dbService.addUser(currUser);
                return "Success";
            } else {
                return "fail";
            }
        }

        @RequestMapping("/getallfriends")
        public ArrayList<String> getAllFriends(@RequestParam String myEmail) {
            return dbService.getUser(myEmail).getFriends();
        }*/
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