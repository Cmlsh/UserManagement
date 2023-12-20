import com.hsbc.usermanagement.main.Main;
import com.hsbc.usermanagement.models.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RoleTest {

    @Test
    public void testCreateRole() {
        Main main = new Main();
        try {
            main.createRole("Admin");
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail("Role creation failed");
        }
    }

    @Test
    public void testCreateDuplicateRole() {
        Main main = new Main();
        try {
            main.createRole("Admin");
            main.createRole("Admin");
            Assert.fail("Duplicate Role created");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testDeleteRole() {
        Main main = new Main();
        try {
            main.createRole("Admin");
            main.createRole("SecAdmin");
            main.createRole("Auditor");

            main.deleteRole("SecAdmin");

            if(main.checkUserExists("SecAdmin")) {
                Assert.fail("Role deletion failed");
            } else {
                Assert.assertTrue(true);
            }

        } catch (Exception e) {
            Assert.fail("Role deletion failed");
        }
    }

    @Test
    public void testDeleteUser_NonExistentUser() {
        Main main = new Main();
        try {

            main.createRole("Admin");
            main.createRole("SecAdmin");
            main.createRole("Auditor");

            main.deleteRole("SecOpAdmin");
            Assert.fail("Deleted user which does not exists");

        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testAddUserRole() {

        Main main = new Main();
        try {

            main.createRole("Admin");
            main.createRole("SecAdmin");
            main.createRole("Auditor");

            main.createUser("User1", "User@123");

            main.addRoleToUser("User1", "Admin");

           User user = main.getUser("User1");
           Boolean isRoleFound = user.getRoles().stream().anyMatch(x-> x.equals("Admin"));

           if(isRoleFound) {
               Assert.assertTrue(true);
           } else {
               Assert.fail("Failed to assign role");
           }

        } catch (Exception e) {
            Assert.fail("Failed to assign role");

        }
    }

    @Test
    public void testAssignIncorrectRole() {

        Main main = new Main();
        try {

            main.createRole("Admin");
            main.createRole("SecAdmin");
            main.createRole("Auditor");

            main.createUser("User1", "User@123");

            main.addRoleToUser("User1", "DevAdmin");

            User user = main.getUser("User1");
            Boolean isRoleFound = user.getRoles().stream().anyMatch(x-> x.equals("Admin"));

            if(isRoleFound) {
                Assert.fail("Assigned incorrect role");
            } else {
                Assert.assertTrue(true);
            }

        } catch (Exception e) {
            Assert.fail("Failed to assign role");
        }
    }

    @Test
    public void testCheckRole() {

        Main main = new Main();
        try {

            main.createRole("Admin");
            main.createRole("SecAdmin");
            main.createRole("Auditor");

            main.createUser("User1", "User@123");

            main.addRoleToUser("User1", "Admin");
            main.addRoleToUser("User1", "SecAdmin");

            String token = main.authenticate("User1", "User@123");

            Boolean roleExists = main.checkRole("SecAdmin", token);

            if(roleExists) {
                Assert.assertTrue(true);
            } else {
                Assert.fail("Role not found");
            }

        } catch (Exception e) {
            Assert.fail("Role not found");
        }
    }

    @Test
    public void testCheckInCorrectRole() {

        Main main = new Main();
        try {

            main.createRole("Admin");
            main.createRole("SecAdmin");
            main.createRole("Auditor");

            main.createUser("User1", "User@123");

            main.addRoleToUser("User1", "Admin");
            main.addRoleToUser("User1", "SecAdmin");

            String token = main.authenticate("User1", "User@123");

            Boolean roleExists = main.checkRole("Auditor", token);

            if(roleExists) {
                Assert.fail("Role not found");
            } else {
                Assert.assertTrue(true);
            }

        } catch (Exception e) {
            Assert.fail("Error occured");
        }
    }

    @Test
    public void testGetAllRoles() {

        Main main = new Main();
        try {

            main.createRole("Admin");
            main.createRole("SecAdmin");
            main.createRole("Auditor");

            main.createUser("User1", "User@123");

            main.addRoleToUser("User1", "Admin");
            main.addRoleToUser("User1", "SecAdmin");

            String token = main.authenticate("User1", "User@123");

            List<String> roles = main.getAllRoles(token);

            if(roles.isEmpty()) {
                Assert.fail("Get all roles failed");
            } else {
                Assert.assertTrue(true);
            }


        } catch (Exception e) {
            Assert.fail("Error occurred");
        }
    }

}
