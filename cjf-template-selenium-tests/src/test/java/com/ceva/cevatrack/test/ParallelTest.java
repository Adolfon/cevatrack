package com.ceva.template.test;

import org.junit.Assert;
import org.junit.experimental.ParallelComputer;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
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


/**
 * Test suite for all test cases.
 */
@RunWith(JUnitPlatform.class)
public class ParallelTest {
  @Test
  public void runAllTests() {
    Result result = JUnitCore.runClasses(ParallelComputer.classes(), LoginPageTest.class,
        AdministrationTest.class, ListToDoPageTest.class, AddToDoPageTest.class,
        RoleMaintenanceTest.class, AssignedUsersRolePanelTest.class, PermissionTest.class,
        RolePermissionTest.class, UserMaintenancePageTest.class, UserProfileTest.class,
        UserSignupTest.class, ChangePasswordPageTest.class, PasswordStrengthTest.class,
        SendErrorReportTest.class);
    System.out.println(
        "================================= Selenium Test Result =================================");
    System.out
        .println("Test Ran: " + result.getRunCount() + " Failure: " + result.getFailureCount());
    System.out.println(
        "======================================== Failure ========================================");
    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
      System.out.println(
          "======================================== Test Case Failure ========================================");
    }
    Assert.assertTrue(result.wasSuccessful());
  }
}
