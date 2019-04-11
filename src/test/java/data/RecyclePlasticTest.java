package data;

import org.junit.Assert;
import org.junit.Test;
import tools.Requests;

import static junit.framework.TestCase.assertEquals;

public class RecyclePlasticTest {
    RecyclePlastic plastic = new RecyclePlastic();
    User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");

    @Test
    public void testConstructor() {
        assertEquals("Household", plastic.getCategory());
        assertEquals("Recycle Plastic", plastic.getName());
    }

    @Test
    public void testCalculateCarbonSaved(){
        assertEquals((int) (140.0/365.0), (int) plastic.calculateCarbonSaved(user));
    }

    @Test
    public void testPerformActivity() {
        try {
            plastic.performActivity(user, Requests.instance);
        } catch (Exception e) {
        }
        Assert.assertEquals(1, user.getSimilarActivities(new RecyclePlastic()).size());

    }

}
