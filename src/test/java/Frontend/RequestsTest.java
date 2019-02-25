package Frontend;

import Backend.RequestHandler;
import Backend.Server;
import Backend.data.DbService;
import Backend.data.LoginDetails;
import Backend.data.User;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Server.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RequestsTest {

    @MockBean
    private DbService dbService;

    @InjectMocks
    @Resource
    RequestHandler requestHandler;

    private final User testUser = new User("Test", "User", 24, "test@email.com", "pwd");
    private final LoginDetails testUserDetails = new LoginDetails("alex@email.com", "123456AAaa@@$$");

    @Test
    public void testType1(){
        String response = Requests.sendRequest(1, testUserDetails, testUser);
        assertNotEquals("", response);
    }

    @Test
    public void testType2(){
        String response = Requests.sendRequest(2, testUserDetails, testUser);
        assertNotEquals("", response);
    }
}
