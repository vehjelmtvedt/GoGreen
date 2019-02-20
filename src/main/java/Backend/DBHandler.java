package Backend;

import Backend.data.*;
import Backend.data.User;
import Backend.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.*;

@RestController
public class DBHandler {
    @Resource(name="DBService")
    private DBService dbService;

    @ResponseBody
    @RequestMapping("/DBaddUser")
    public String DBaddUserHandler(@RequestBody User user){
        if (dbService.getUser(user.getEmail()) != null)
            return "fail";
        dbService.addUser(user);
        return "success";
        //return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @RequestMapping("/DBauthenticate")
    public String DBauthHandler(@RequestBody LoginDetails loginDetails){
        if(dbService.grantAccess(loginDetails.getEmail(),loginDetails.getPassword()))
            return "success";
        return "failure";
    }

    //Adds a friend given by their email.
    @RequestMapping("/addfriend")
    public String addFriend(@RequestParam String myEmail, @RequestParam String friendEmail) {
        if (dbService.getUser(friendEmail) != null) {
            dbService.getUser(myEmail).addFriend(friendEmail);
            return "success";
        }
        else {
            return "fail";
        }
    }

    //Returns all friends of a user to be displayed in friends page/dashboard.
    @RequestMapping("/getallfriends")
    public ResponseEntity<String> getallfriends(@RequestParam String myEmail) {
        String allfriends = dbService.getFriends(myEmail).get(0).toString();
        return new ResponseEntity<String>(allfriends, HttpStatus.OK);
    }
}
