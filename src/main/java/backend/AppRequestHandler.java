package backend;

import data.Achievement;
import data.LoginDetails;
import data.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.annotation.Resource;

@RestController
public class AppRequestHandler {
    @Resource(name = "DbService")
    private DbService dbService;

    @RequestMapping("/getAllAchievements")
    public List<Achievement> getAllAchievements() {
        return dbService.getAchievements();
    }

    @RequestMapping("/getTotalUsers")
    public int getTotalUsers() {
        return dbService.getTotalUsers();
    }

    @RequestMapping("/getTotalCO2Saved")
    public double getTotalCO2Saved() {
        return dbService.getTotalCO2Saved();
    }

    @RequestMapping("/getAverageCO2Saved")
    public double getAverageCO2Saved() {
        return dbService.getAverageCO2Saved();
    }


    /**
     * Request to retrieve top users.
     * @param loginDetails for authentication
     * @param top the top n users
     * @return a list of users in ascending order of rank
     */
    @RequestMapping("/getTopUsers")
    public List<User> getTopUsers(@RequestBody LoginDetails loginDetails, @RequestParam int top) {
        if (dbService.grantAccess(loginDetails.getIdentifier(),
                loginDetails.getPassword()) != null) {
            return dbService.getTopUsers(top);
        }
        return null;
    }

    /**
     * Request to search for users.
     * @param loginDetails for authentication
     * @param keyword keyword to search
     * @return returns a list of users matching the keyword
     */
    @RequestMapping("/searchUsers")
    public List<String> userSearch(@RequestBody LoginDetails loginDetails,
                                   @RequestParam String keyword) {
        if (dbService.grantAccess(loginDetails.getIdentifier(),
                loginDetails.getPassword()) != null) {
            return dbService.getMatchingUsers(keyword);
        }
        return null;
    }
}
