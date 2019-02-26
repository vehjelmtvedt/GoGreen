package backend;


import backend.data.DbService;
import backend.data.LoginDetails;
import backend.data.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestHandlerTest
{
    @MockBean
    private DbService dbService;

    @InjectMocks
    @Resource
    RequestHandler requestHandler;

    private final User testUser = new User("Test", "User", 24, "test@email.com", "pwd", "user1");
    private final User testUser2 = new User("Test2", "User2", 22, "test2@email.com", "pwd2", "user2");

    @Test
    public void testSignupExists()
    {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals("user exists already", requestHandler.signupController(testUser));
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
        assertEquals("success", requestHandler.loginController(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void testLoginFail() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(false);
        assertEquals("failure", requestHandler.loginController(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }
}