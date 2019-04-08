package data;

import data.Achievement;
import org.junit.Assert;
import org.junit.Test;

public class AchievementTest {

    Achievement achievement;
    Achievement test = new Achievement(2 , "test1" , 100);


    @Test
    public void constructorTest() {

        achievement = new Achievement(1 , "test" , 10);
        Assert.assertNotNull(achievement);
    }

    @Test
    public void testid(){

        int id = 2;
        Assert.assertEquals(2 , test.getId());
    }

    @Test
    public void testname(){
        String name = "test1";

        Assert.assertTrue(test.getName().equals(name));
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
        Assert.assertNotEquals(test,new String());
    }

    @Test
    public void equals() {

        Achievement test1 = new Achievement(1, "test" , 10);

        Assert.assertFalse(test1.equals(test));

    }

    @Test
    public void equals1() {

        Achievement test2 = new Achievement(2 , "test1" , 100);

        Assert.assertTrue(test2.equals(test));

    }

    @Test
    public void equals2() {
        Achievement test2 = new Achievement(2 , "test12" , 100);

        Assert.assertFalse(test2.equals(test));

    }

    @Test
    public void equals3() {
        Achievement test2 = new Achievement(2 , "test1" , 1000);

        Assert.assertFalse(test2.equals(test));

    }
}