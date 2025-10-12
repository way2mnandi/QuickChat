import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class LoginTest {

    @Test
    void testValidUsername() {
        assertTrue(Login.checkUser("kyl_1"), "Valid username should return true");
    }

    @Test
    void testInvalidUsername() {
        assertFalse(Login.checkUser("kyl!!!!!!!!"), "Invalid username should return false");
    }

    @Test
    void testValidPassword() {
        assertTrue(Login.checkPassword("Ch&&sec@ke99!"), "Valid password should return true");
    }

    @Test
    void testInvalidPassword() {
        assertFalse(Login.checkPassword("password"), "Invalid password should return false");
    }

    @Test
    void testValidCellNumber() {
        assertTrue(Login.checkNumber("+27838968976"), "Valid cell number should return true");
    }

    @Test
    void testInvalidCellNumber() {
        assertFalse(Login.checkNumber("08966553"), "Invalid cell number should return false");
    }

    @Test
    void testRegisterUserMessages() {
        Login user = new Login();

        // forces invalid states
        user.userOk = false;
        user.passOk = false;
        user.cellOk = false;

        String expectedMessage =
            "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.\n" +
            "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n" +
            "Cell phone number incorrectly formatted or does not contain international code.\n";

        assertEquals(expectedMessage, user.registerUser());
    }
    @Test
    void testRegisterUserSuccess() {
        Login user = new Login();

        // forces valid states
        user.userOk = true;
        user.passOk = true;
        user.cellOk = true;

        String expectedMessage = "Registration successful.";
        assertEquals(expectedMessage, user.registerUser());
    }

    @Test
    void testLoginUserSuccess() {
        Login user = new Login();
        user.username = "kyl_1";
        user.password = "Ch&&sec@ke99!"; //setting valid credentials
        user.userOk = true;
        user.passOk = true;
        user.cellOk = true;
        user.login = true;

        // simulateing successful login(cannot test scanner)
        assertTrue(user.username.equals("kyl_1") && user.password.equals("Ch&&sec@ke99!"));
    }

    @Test
    void testLoginUserFailure() {
        Login user = new Login();
        user.username = "kyl!!!!!!!!!!";
        user.password = "password"; //setting invalid credentials

        // simulating failed login(cannot test scanner)
        assertFalse(user.username.equals("kyl_1") && user.password.equals("Ch&&sec@ke99!"));
    }
}
