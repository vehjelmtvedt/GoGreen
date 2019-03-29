package backend;

import data.Activity;
import data.EatVegetarianMeal;
import data.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
public class DbServiceStatsTest {
    @Autowired
    private DbService dbService;

    public static ArrayList<User> users = new ArrayList<User>();

    @Before
    public void setup() {

        if (users.size() == 0) {
            for (int i = 0; i < 10; ++i) {
                User user = new User("active", "user", 20, "active_user" + i + "@email.com",
                        "active_user" + i, "123");

                dbService.addUser(user);

                for (int j = 0; j < 25; ++j) {
                    Activity activity = new EatVegetarianMeal();

                    double co2 = (i+1)*i*10;
                    activity.setCarbonSaved(co2);

                    user = dbService.addActivityToUser(user.getUsername(), activity);
                }

                users.add(user);
            }
        }
    }

    @Test
    public void testTotalCO2Saved() {
        double expected = users.stream().mapToDouble(User::getTotalCarbonSaved).sum();
        double result = dbService.getTotalCO2Saved();

        Assert.assertEquals(expected, result, 0.01);
    }

    @Test
    public void testTotalUsers() {
        int expected = dbService.getAllUsers().size();
        int result = dbService.getTotalUsers();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testAverageCO2Saved() {
        double expected = users.stream().mapToDouble(User::getTotalCarbonSaved).average().getAsDouble();
        double result = dbService.getAverageCO2Saved();

        Assert.assertEquals(expected, result, 0.01);
    }
}