package com.ceva.template.test;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;
import com.ceva.cjf.base.selenium.test.ChangePasswordPageTest;
import com.ceva.cjf.base.selenium.test.LoginPageTest;
import com.ceva.cjf.base.selenium.test.PasswordStrengthTest;
import com.ceva.cjf.base.selenium.test.SendErrorReportTest;
import com.ceva.cjf.base.selenium.test.UserProfileTest;
import com.ceva.cjf.base.selenium.test.UserSignupTest;
import com.ceva.cjf.base.selenium.test.aaf.AssignedUsersRolePanelTest;
import com.ceva.cjf.base.selenium.test.aaf.PermissionTest;
import com.ceva.cjf.base.selenium.test.aaf.RoleMaintenanceTest;
import com.ceva.cjf.base.selenium.test.aaf.RolePermissionTest;
import com.ceva.cjf.base.selenium.test.aaf.UserMaintenancePageTest;

/*
 * To save development time, comment unwanted test classes from the below list. But before commit
 * uncomment all the classes and test once. Formatter turned off to make line (//) and block (/*)
 * comment during development
 */


/**
 * Test suite for all test cases.
 */
@RunWith(JUnitPlatform.class)
// @SelectPackages({"com.ceva.cjf.base.selenium.test","com.ceva.template.test"})
@SelectClasses({LoginPageTest.class, AdministrationTest.class, ListToDoPageTest.class,
    AddToDoPageTest.class, RoleMaintenanceTest.class, AssignedUsersRolePanelTest.class,
    PermissionTest.class, RolePermissionTest.class, UserMaintenancePageTest.class,
    UserProfileTest.class, UserSignupTest.class, ChangePasswordPageTest.class,
    PasswordStrengthTest.class, SendErrorReportTest.class})
//@formatter:on  
public class AllTests {

}
