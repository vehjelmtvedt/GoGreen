package frontend;


import backend.RequestHandler;
import backend.Server;
import backend.data.DbService;
import backend.data.LoginDetails;
import backend.data.User;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Server.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
public class RequestsTest {

    @MockBean
    private DbService dbService;

    @InjectMocks
    @Resource
    RequestHandler requestHandler;

    private final User testUser = new User("Test", "User", 24, "test@email.com","dummy", "pwd");
    private final User testUser2 = new User("Test2", "User2", 24, "test2@email.com","dummy2", "pwd2");


    @Test
    public void testSignupRequest(){
        assertEquals("success", Requests.signupRequest(testUser));
    }

    @Test
    public void testSignupRequestUsernameExists() {
        Mockito.when(dbService.getUserByUsername(testUser.getUsername())).thenReturn(testUser);
        assertEquals("Username exists", Requests.signupRequest(testUser));
    }

    @Test
    public void testSignupRequestEmailExists() {
        Mockito.when(dbService.getUser(testUser.getUsername())).thenReturn(null);
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals("Email exists", Requests.signupRequest(testUser));
    }

    @Test
    public void loginRequest() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(true);
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals(testUser, Requests.loginRequest(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void loginRequestFail() {
        assertEquals(null, Requests.loginRequest(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void testgetUser() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals(testUser, Requests.getUserRequest(testUser.getEmail()));
    }

    @Test
    public void testgetUserfail() {
        assertEquals(null, Requests.getUserRequest(testUser.getEmail()));
    }
}
