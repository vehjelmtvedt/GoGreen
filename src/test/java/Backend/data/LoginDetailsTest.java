package Backend.data;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

public class LoginDetailsTest {

    LoginDetails detailOne = new LoginDetails("test","pass");


    @Test
    public void getEmailSuccess(){
        assertEquals(detailOne.getEmail(),"test");
    }

    @Test
    public void getEmailFail(){
        assertNotEquals(detailOne.getEmail(),"pass");
    }

    @Test
    public void getPasswordFail(){
        assertNotEquals(detailOne.getPassword(),"sad");
    }

    @Test
    public void getPasswordSuccess(){
        assertEquals(detailOne.getPassword(),"pass");
    }

    @Test
    public void toStringSuccess() {  assertEquals(detailOne.toString(),"Email: test\nPassword: pass\n");}

    @Test
    public void toStrinFailuccess() {  assertNotEquals(detailOne.toString(),"");}

    @Test
    public void testConstructor(){
        assertNotNull(new LoginDetails("",""));
    }
}
