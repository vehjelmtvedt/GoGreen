package Frontend;

import Backend.RequestHandler;
import Backend.Server;
import Backend.data.DbService;
import Backend.data.LoginDetails;
import Backend.data.User;
import org.junit.Test;
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
    private final LoginDetails testUserDetails = new LoginDetails("alex@email.com", "123456AAaa@@$$");

    @Test
    public void testType1(){
        Requests requests = new Requests();
        requests.sendRequest(1, testUserDetails, testUser);
        assertEquals("http://localhost:8080/login", requests.getUrl().toString());
    }

    @Test
    public void testType2(){
        Requests requests = new Requests();
        requests.sendRequest(2, testUserDetails, testUser);
        assertEquals("http://localhost:8080/signup", requests.getUrl().toString());
    }
}
