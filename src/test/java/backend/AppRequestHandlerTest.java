package backend;

import data.Achievement;
import data.LoginDetails;
import data.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
public class AppRequestHandlerTest {
    @MockBean
    private DbService dbService;

    @InjectMocks
    @Resource
    AppRequestHandler appRequestHandler;

    private final User testUser = new User("Test", "User", 24, "test@email.com","dummy", "pwd");

    @Test
    public void testUserSearch() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        List<String> testList = new ArrayList();
        testList.add(testUser.getUsername());
        Mockito.when(dbService.getMatchingUsers(testUser.getUsername())).thenReturn(testList);
        assertEquals(testList, appRequestHandler.userSearch(new LoginDetails(testUser.getUsername(),
                testUser.getPassword()),testUser.getUsername()));

    }

    @Test
    public void testUserSearchNull() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(null);
        assertEquals(null, appRequestHandler.userSearch(new LoginDetails(testUser.getUsername(),
                testUser.getPassword()),testUser.getUsername()));
    }

    @Test
    public void retrieveTopUsers() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        List<User> testList = new ArrayList();
        testList.add(testUser);
        Mockito.when(dbService.getTopUsers(1)).thenReturn(testList);
        assertEquals(testList, appRequestHandler.getTopUsers(new LoginDetails(testUser.getUsername(),
                testUser.getPassword()),1));
    }

    @Test
    public void retrieveTopUsersNull() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(null);
        assertEquals(null, appRequestHandler.getTopUsers(new LoginDetails(testUser.getUsername(),
                testUser.getPassword()),1));
    }

    @Test
    public void getAllAchievements() {
        List<Achievement> testList = new ArrayList();
        testList.add(new Achievement());
        Mockito.when(dbService.getAchievements()).thenReturn(testList);
        assertEquals(testList, appRequestHandler.getAllAchievements());
    }

    @Test
    public void getTotalCO2Saved() {
        Mockito.when(dbService.getTotalCO2Saved()).thenReturn(100.5);
        assertEquals(appRequestHandler.getTotalCO2Saved(),100.5, 0.1);
    }

    @Test
    public void getTotalUsers() {
        Mockito.when(dbService.getTotalUsers()).thenReturn(10);
        assertEquals(appRequestHandler.getTotalUsers(),10);
    }

    @Test
    public void getAverageCO2Saved() {
        Mockito.when(dbService.getAverageCO2Saved()).thenReturn(25.2);
        assertEquals(appRequestHandler.getAverageCO2Saved(),25.2, 0.1);
    }
}