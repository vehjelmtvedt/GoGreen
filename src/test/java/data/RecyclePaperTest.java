package data;

import org.junit.Assert;
import org.junit.Test;
import tools.Requests;

import static junit.framework.TestCase.assertEquals;

public class RecyclePaperTest {
    private final RecyclePaper paper = new RecyclePaper();
    private final User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");

    @Test
    public void testConstructor() {
        assertEquals("Household", paper.getCategory());
        assertEquals("Recycle Paper", paper.getName());
    }

    @Test
    public void testCalculateCarbonSaved(){
        assertEquals((int) (70.0/365.0), (int) paper.calculateCarbonSaved(user));
    }

    @Test
    public void testPerformActivity() {
        try {
            paper.performActivity(user, Requests.instance);
        } catch (Exception ignored) {
        }
        Assert.assertEquals(1, user.getSimilarActivities(new RecyclePaper()).size());

    }

}
