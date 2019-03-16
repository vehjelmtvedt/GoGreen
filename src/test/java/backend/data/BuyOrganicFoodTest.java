package backend.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BuyOrganicFoodTest {

    @Test
    public void testConstructor() {
        BuyOrganicFood food = new BuyOrganicFood();
        assertEquals("Food", food.getCategory());
        assertEquals("Buy organic food", food.getName());
    }


}
