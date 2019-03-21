package data;

import data.UserAchievement;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class UserAchievementTest {

    UserAchievement test = new UserAchievement(0 , true , new Date(1 , 1, 1));


    @Test
    public void getId() {

        Assert.assertEquals(0 , test.getId());

    }

    @Test
    public void getDate() {

        Date date = new Date(1 ,1,1);
        Assert.assertTrue(date.equals(test.getDate()));

    }

    @Test
    public void toStringtest() {

        Assert.assertNotNull(test.toString());

    }

    @Test
    public void constructortest(){
        UserAchievement test1 = new UserAchievement(0 , false , test.getDate());

        Assert.assertNotNull(test1);
    }
}