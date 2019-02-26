package backend;


import backend.data.DbService;
import backend.data.LoginDetails;
import backend.data.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
public class RequestHandlerTest
{
    @MockBean
    private DbService dbService;

    @InjectMocks
    @Resource
    RequestHandler requestHandler;

    private final User testUser = new User("Test", "User", 24, "test@email.com","dummy", "pwd");

    @Test
    public void testSignupExists()
    {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals("email exists", requestHandler.signupController(testUser));
    }

    @Test
    public void testSignupDoesNotExist()
    {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(null);
        assertEquals("success", requestHandler.signupController(testUser));
    }

    @Test
    public void testLoginSuccess() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(true);
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals(testUser, requestHandler.loginController(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void testLoginFail() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(false);
        assertEquals(null, requestHandler.loginController(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void testgetUser() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals(testUser, requestHandler.getUser(testUser.getEmail()));
    }

}