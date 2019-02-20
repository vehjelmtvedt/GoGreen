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
import java.util.*;

@RestController
public class DBHandler {
    @Resource(name="DBService")
    private DBService dbService;

    @ResponseBody
    @RequestMapping("/DBaddUser")
    public String DBaddUserHandler(@RequestBody User user){
        if(dbService.getUser(user.getEmail())!=null)
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
    public ResponseEntity addFriend(@RequestParam String myEmail, @RequestParam String friendEmail) {
        dbService.getUser(myEmail).addFriend(friendEmail);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // Returns all friends of a user to be displayed in friends page/dashboard.
//    @RequestMapping("/getallfriends")
//    public ResponseEntity<List<User>> getallfriends(@RequestParam String myEmail) {
//        List<User> allfriends = dbService.getFriends(myEmail);
//        return new ResponseEntity<>(allfriends, HttpStatus.OK);
//    }
}
