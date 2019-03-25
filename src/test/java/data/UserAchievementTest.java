package data;

import data.UserAchievement;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class UserAchievementTest {

    private UserAchievement test = new UserAchievement(0 , true , new Date(1 , 1, 1));


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
    public void constructor(){
        UserAchievement test1 = new UserAchievement(0 , false , test.getDate());

        Assert.assertNotNull(test1);
    }

    @Test
    public void setId() {

        UserAchievement test1 = new UserAchievement(0 , false , test.getDate());

        test1.setId(1);

        Assert.assertEquals(1 , test1.getId());
    }


    @Test
    public void setDate() {

        Date testdate = new Date(1 , 1 ,1 );

        test.setDate(testdate);

        Assert.assertTrue(testdate.equals(test.getDate()));

    }
}