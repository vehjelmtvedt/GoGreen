package frontend;

import backend.AppRequestHandler;
import backend.DbService;
import backend.UserRequestHandler;
import backend.Server;
import data.User;
import data.UserAchievement;
import frontend.gui.ProfilePageLogic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Server.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)




public class ProfilePageLogicTest {


    @MockBean
    private DbService dbService;

    @InjectMocks
    @Resource
    UserRequestHandler userRequestHandler;

    @InjectMocks
    @Resource
    AppRequestHandler appRequestHandler;

    private User user = new User("Active", "User", 20, "active_user@email.com", "active_user", "pwd123");
    private UserAchievement userAchievement = new UserAchievement(0, true, new Date(11, 11, 11));

    @Before
    public void setup() {

        user.getProgress().getAchievements().add(userAchievement);
        dbService.addUser(user);
    }

    @Test
    public void getAchievements() {

        Assert.assertNotNull(user.getProgress().getAchievements().get(0));
    }

//    @Test
//    public void getNameString() {
//        dbService.getAchievements();
//
//
//        String test = "Saved your first CO2";
//
//      //  System.out.println(ProfilePageLogic.getNameString(user.getProgress().getAchievements().get(0)));
//
//        Assert.assertTrue(test.equals(ProfilePageLogic.getNameString(userAchievement)));
//
//    }

    @Test
    public void getDateString() {


        String test = new Date(11, 11, 11).toString();

        Assert.assertTrue(test.equals(ProfilePageLogic.getDateString(userAchievement)));

    }

}