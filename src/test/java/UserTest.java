import com.hsbc.usermanagement.main.Main;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testCreateUser() {
        Main main = new Main();
        try {
            main.createUser("User", "@&#@#*&@#^$");
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail("User creation failed");
        }
    }

    @Test
    public void testDeleteUser() {
        Main main = new Main();
        try {
            main.createUser("User1", "User@123");
            main.createUser("User2", "User@123");
            main.createUser("User3", "User@123");

            main.deleteUser("User1");

            if(main.checkUserExists("user1")) {
                Assert.fail("User deletion failed");
            } else {
                Assert.assertTrue(true);
            }

        } catch (Exception e) {
            Assert.fail("User deletion failed");
        }
    }

    @Test
    public void testDeleteUser_NonExistentUser() {
        Main main = new Main();
        try {
            main.createUser("User1", "User@123");
            main.createUser("User2", "User@123");
            main.createUser("User3", "User@123");

            main.deleteUser("User4");
            Assert.fail("Deleted user which does not exists");

        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testCreateEmptyUser() {
        Main main = new Main();
        try {
            main.createUser("", "@&#@#*&@#^$");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue("User creation failed", true);
        }
    }

    @Test
    public void testCreateUserEmptyPassword() {
        Main main = new Main();
        try {
            main.createUser("User", "");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue("User creation failed", true);
        }
    }

    @Test
    public void testDuplicateUser() {
        Main main = new Main();
        try {
            main.createUser("UserDup", "*&*%&^&^%");
            main.createUser("UserDup", "*&*%&^&^%");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue("Duplicate user creation failed", true);
        }
    }

    @Test
    public void testEmptyUserParams() {
        Main main = new Main();
        try {
            main.createUser("", "");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue("Empty user creation failed", true);
        }
    }

    @Test
    public void testAuthentication() {
        Main main = new Main();
        try {
            main.createUser("User", "User@123");
            main.authenticate("User", "User@123");
            Assert.assertTrue("Authentication success", true);
        } catch (Exception e) {
            Assert.fail("Authentication failed");
        }
    }

    @Test
    public void testAuthenticationFailure() {
        Main main = new Main();
        try {
            main.createUser("User", "User@123");
            main.authenticate("User", "User@1234");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue("Authentication failed", true);
        }
    }

    @Test
    public void testTokenInvalidation() {
        Main main = new Main();
        try {
            main.createUser("User", "User@123");
            String token = main.authenticate("User", "User@123");
            main.invalidateToken(token);
            boolean isValid = main.isTokenValid(token);
            if(isValid) {
                Assert.fail("Token invalidation failed");
            } else {
                Assert.assertTrue("Token invalidated", true);
            }

        } catch (Exception e) {
            Assert.fail("Token invalidation failed");
        }
    }

    @Test
    public void testTokenInvalidationWrongToken() {
        Main main = new Main();
        try {
            main.createUser("User", "User@123");
            String token = main.authenticate("User", "User@123");
            main.invalidateToken(token);
            main.invalidateToken(token);

            Assert.fail("Token invalidated");


        } catch (Exception e) {
            Assert.assertTrue("Token invalidation failed", true);
        }
    }
}
