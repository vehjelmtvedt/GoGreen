package Backend.data;

import Backend.RequestHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestHandlerTest {

//    @Autowired
//    MockMvc mvc;

    @Autowired
    RequestHandler requestHandler;

    @Test
    public void testSignupSuccess() {
        Assert.assertEquals("success", requestHandler.signupController(new User("T", "U", 24, "test@email.com", "pwd123")));
    }

    @Test
    public void testSignupFail() {
        requestHandler.signupController(new User("T", "U", 24, "test@email.com", "pwd123"));
        Assert.assertEquals("user exists already", requestHandler.signupController(new User("T", "U", 24, "test@email.com", "pwd123")));
    }

    @Test
    public void testLoginSuccess() {
        requestHandler.signupController(new User("T", "U", 24, "test@email.com", "pwd123"));
        Assert.assertEquals("success", requestHandler.loginController(new LoginDetails("test@email.com", "pwd123")));
    }

    @Test
    public void testLoginFail() {
        Assert.assertEquals("failure", requestHandler.loginController(new LoginDetails("test@email.com", "pwd123")));
    }


}

