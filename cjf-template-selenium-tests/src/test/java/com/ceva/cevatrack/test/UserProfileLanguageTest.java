package com.ceva.template.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import com.ceva.cjf.base.selenium.framework.page.LoginPage;
import com.ceva.selenium.test.BaseTest;
import com.ceva.template.test.framework.page.HomePage;
import com.ceva.template.test.framework.page.ListToDoPage;

/**
 * Ascending the method name in execution.
 */
@RunWith(JUnitPlatform.class)
@TestMethodOrder(Alphanumeric.class)
public class UserProfileLanguageTest extends BaseTest {

  /**
   * Setup before class initialization.
   */
  @BeforeAll
  static void setupBeforeClass() {
    openBrowser();
    HomePage.INSTANCE.reInitializePageElementsForCurrentBrowser();
    LoginPage.INSTANCE.reInitializePageElementsForCurrentBrowser();
    ListToDoPage.INSTANCE.reInitializePageElementsForCurrentBrowser();
    LoginPage.INSTANCE.goTo();
    LoginPage.INSTANCE.fillForm().withDefaultValues().login();
  }

  @AfterAll
  static void setupAfterClass() {
    closeBrowser();
  }

  @Disabled
  @Test
  void a01_selectUserLanguageAsDeutsche_ensureTodosPageHasDeutsche() {
    HomePage.INSTANCE.clickMenuBarLanguage("Deutsche");
    Assertions.assertEquals("de_Search Todo", ListToDoPage.INSTANCE.getSearchTodoTitleHeaderName(),
        "Language changes were not successfully applied");
    Assertions.assertEquals("Language changes were not successfully applied", "de_Title",
        ListToDoPage.INSTANCE.getSearchTitleLabelName());
  }

  @Disabled
  @Test
  void a02_selectUserProfileLanguageAsHindi_ensureTodosPageHasHindi() {
    HomePage.INSTANCE.clickMenuBarLanguage("हिंदी");
    Assertions.assertEquals("hi_Search", ListToDoPage.INSTANCE.getSearchButtonName(),
        "Language changes were not successfully applied");
    Assertions.assertEquals("hi_Reset", ListToDoPage.INSTANCE.getResetButtonName(),
        "Language changes were not successfully applied");
  }

  @Disabled
  @Test
  void a03_selectUserProfileLanguageAsChinese_ensureTodosPageHasChinese() {
    HomePage.INSTANCE.clickMenuBarLanguage("中文");
    Assertions.assertEquals("zh_List Todos", ListToDoPage.INSTANCE.getListTodoTitleHeaderName(),
        "Language changes were not successfully applied");
    Assertions.assertEquals("zh_ID", ListToDoPage.INSTANCE.getListIdColumnHeaderName(),
        "Language changes were not successfully applied");
  }

  @Disabled
  @Test
  void a04_selectUserProfileLanguageAsEnglish_ensureTodosPageHasEnglish() {
    HomePage.INSTANCE.clickMenuBarLanguage("English");
    Assertions.assertEquals("Select Priority",
        ListToDoPage.INSTANCE.getSearchPriorityDropDownItem("Select Priority").getText(),
        "Language changes were not successfully applied");
    Assertions.assertEquals("Add Todo", ListToDoPage.INSTANCE.getAddTodoButtonText(),
        "Language changes were not successfully applied");
  }
}
