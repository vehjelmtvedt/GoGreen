package data;
import data.LoginDetails;
import org.junit.*;

import static org.junit.Assert.*;

public class LoginDetailsTest {

    private final LoginDetails detailOne = new LoginDetails("test","pass");


    @Test
    public void getEmailSuccess(){
        assertEquals(detailOne.getIdentifier(),"test");
    }

    @Test
    public void getEmailFail(){
        assertNotEquals(detailOne.getIdentifier(),"pass");
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
    public void toStringFail() {  assertNotEquals(detailOne.toString(),"");}

    @Test
    public void testConstructor(){
        new LoginDetails("", "");
    }

    @Test
    public void testEmptyConstructor() {
        new LoginDetails();
    }
}
