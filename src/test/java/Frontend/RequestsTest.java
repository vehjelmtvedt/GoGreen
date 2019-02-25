package Frontend;


import Backend.RequestHandler;
import Backend.Server;
import Backend.data.DbService;
import Backend.data.LoginDetails;
import Backend.data.User;
import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Server.class)
public class RequestsTest {

    @MockBean
    private DbService dbService;

    @InjectMocks
    @Resource
    RequestHandler requestHandler;

    private final User testUser = new User("Test", "User", 24, "test@email.com", "pwd");
    private final LoginDetails testUserDetials = new LoginDetails("test@email.com", "pwd");

//    @Test
//    public void testLoginSuccess() {
//        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(true);
//        assertEquals("success", requestHandler.loginController(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
//    }
//
//    @Test
//    public void testLoginFail() {
//        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(false);
//        assertEquals("failure", requestHandler.loginController(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
//    }

    @Test
    public void testType1() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(true);
        System.out.println(Requests.getURL().toString());
//        assertEquals("http://localhost:8080/login", Requests.getURL().toString());
    }

}
