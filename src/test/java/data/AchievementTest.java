package data;

import data.Achievement;
import org.junit.Assert;
import org.junit.Test;

public class AchievementTest {

    private final Achievement test = new Achievement(2 , "test1" , 100);


    @Test
    public void constructorTest() {

        Achievement achievement = new Achievement(1, "test", 10);
        Assert.assertNotNull(achievement);
    }

    @Test
    public void testId(){
        Assert.assertEquals(2 , test.getId());
    }

    @Test
    public void testname(){
        String name = "test1";

        Assert.assertEquals(test.getName(), name);
    }

    @Test
    public void testbonus(){
        int num = 100;
        Assert.assertEquals(num , test.getBonus());

    }

    @Test
    public void testEquals() {
        Achievement test1 = new Achievement();
        test1.setBonus(100);
        test1.setId(2);
        test1.setName("test1");
        Assert.assertEquals(test,test1);
    }

    @Test
    public void testEqualsItself() {
        Assert.assertEquals(test,test);
    }

    @Test
    public void testNotEquals() {
        Assert.assertNotEquals(test, "");
    }

    @Test
    public void equals() {

        Achievement test1 = new Achievement(1, "test" , 10);

        Assert.assertNotEquals(test1, test);

    }

    @Test
    public void equals1() {

        Achievement test2 = new Achievement(2 , "test1" , 100);

        Assert.assertEquals(test2, test);

    }

    @Test
    public void equals2() {
        Achievement test2 = new Achievement(2 , "test12" , 100);

        Assert.assertNotEquals(test2, test);

    }

    @Test
    public void equals3() {
        Achievement test2 = new Achievement(2 , "test1" , 1000);

        Assert.assertNotEquals(test2, test);

    }
}