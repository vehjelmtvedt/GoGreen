package frontend;


import backend.RequestHandler;
import backend.Server;
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
import static org.junit.Assert.*;


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
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(testUser);
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

    @Test
    public void testSendFriendRequestValid() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.sendFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testSendFriendRequestInvalid() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.addFriendRequest("invalid", testUser2.getUsername())).thenReturn(null);
        assertEquals(null, Requests.sendFriendRequest("invalid", testUser2.getUsername()));
    }

    @Test
    public void testAcceptFriendRequest() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.sendFriendRequest(testUser.getUsername(), testUser2.getUsername()));
        Mockito.when(dbService.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testRejectFriendRequest() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.sendFriendRequest(testUser.getUsername(), testUser2.getUsername()));
        Mockito.when(dbService.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testValidateUserRequestEmail() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertTrue(Requests.validateUserRequest(testUser.getEmail()));
    }

    @Test
    public void testValidateUserRequestUsername() {
        Mockito.when(dbService.getUserByUsername(testUser.getUsername())).thenReturn(testUser);
        assertTrue(Requests.validateUserRequest(testUser.getUsername()));
    }

    @Test
    public void testValidateUserRequestInvalid() {
        assertFalse(Requests.validateUserRequest("Invalid"));
    }

//    @Test
//    public void testRequestValidate1() {
//        boolean response = Requests.requestValidate(1, testUser.getUsername());
//        assertFalse(!response);
//    }
//
//    @Test
//    public void testRequestValidate2() {
//        boolean response = Requests.requestValidate(2, testUser.getEmail());
//        assertFalse(!response);
//    }
}
