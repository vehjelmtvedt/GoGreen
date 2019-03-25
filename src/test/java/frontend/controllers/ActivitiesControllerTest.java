package frontend.controllers;

import backend.data.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActivitiesControllerTest {

    private final User testUser = new User("Test", "User", 24, "test@email.com","dummy", "pwd");
    private final User testUser2 = new User("Test2", "User2", 24, "test2@email.com","dummy2", "pwd2");

    @Test
    public void initializeTest() {
    }

    @Test
    public void setUserTest() {
        ActivitiesController.setUser(testUser);
        assertEquals(testUser, ActivitiesController.getUser());
    }

    @Test
    public void setUserNull() {
        ActivitiesController.setUser(null);
        assertNull(ActivitiesController.getUser());
    }
}