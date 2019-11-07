package com.ceva.template.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import com.ceva.cjf.base.selenium.framework.page.LoginPage;
import com.ceva.selenium.framework.driver.Driver;
import com.ceva.selenium.framework.page.AbstractPage;
import com.ceva.selenium.test.BaseTest;
import com.ceva.template.test.framework.page.AdministrationPage;
import com.ceva.template.test.framework.page.HomePage;


/**
 * Ascending the method name in execution.
 */
@RunWith(JUnitPlatform.class)
@TestMethodOrder(Alphanumeric.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AdministrationTest extends BaseTest {

  LoginPage loginPage;
  HomePage homePage;
  AdministrationPage administrationPage;

  /**
   * Setup before class initialization.
   */
  @BeforeAll
  void setupBeforeClass() {
    openBrowser();
    loginPage = new LoginPage();
    homePage = new HomePage();
    administrationPage = new AdministrationPage();
    loginPage.reInitializePageElementsForCurrentBrowser();
    homePage.reInitializePageElementsForCurrentBrowser();
    administrationPage.reInitializePageElementsForCurrentBrowser();
    loginPage.goTo();
    loginPage.fillForm().withDefaultValues().login();
  }

  @BeforeEach
  void setupBefore() {
    if (!administrationPage.isAt()) {
      homePage.navigateToAdministration();
    }
  }

  @AfterAll
  void setupAfterClass() {
    closeBrowser();
  }

  @Test
  void a01_navigateToAdministrationPage_loadAdministrationPage() {
    boolean hasString = AbstractPage.hasStringWithPatternOnPage("Create Heap Dump");
    if (administrationPage.isAt()) {
      Assertions.assertTrue(hasString, "AdministrationPage Page doesn't loaded");
    }
  }

  @Test
  void a02_administrationServerOff_successfullyServingOff() {
    administrationPage.switchOff();
    administrationPage.confirmedYes();
    boolean confirmation = administrationPage.isDisplayedConfirmation();
    Assertions.assertTrue(confirmation, "Serving doesn't Off ");
    administrationPage.switchOff();
    administrationPage.confirmedYes();
  }
}
