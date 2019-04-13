package data;

import data.UserAchievement;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

@SuppressWarnings("deprecation")
public class UserAchievementTest {

    private final UserAchievement test = new UserAchievement(0 , true , new Date(1 , 1, 1));


    @Test
    public void getId() {

        Assert.assertEquals(0 , test.getId());

    }

    @Test
    public void getDate() {

        Date date = new Date(1 ,1,1);
        Assert.assertEquals(date, test.getDate());

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

        Date testDate = new Date(1 , 1 ,1 );

        test.setDate(testDate);

        Assert.assertEquals(testDate, test.getDate());

    }
}