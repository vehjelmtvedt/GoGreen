package Backend;

import Backend.data.*;
import Backend.data.User;
import Backend.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
public class DBHandler {
    @Resource(name="DBService")
    private DBService dbService;

    @RequestMapping("/DBaddUser")
    public String DBaddUserHandler(@RequestBody User user){
        dbService.addUser(user);
        return "success";
    }

    @RequestMapping("/DBauthenticate")
    public boolean DBauthHandler(@RequestBody LoginDetails loginDetails){
        return dbService.grantAccess(loginDetails.getEmail(),loginDetails.getPassword());
    }
}
