package com.ceva.template.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import com.ceva.cjf.base.selenium.framework.page.BaseHomePage;
import com.ceva.cjf.base.selenium.framework.page.LoginPage;
import com.ceva.cjf.base.selenium.framework.page.UserProfilePage;
import com.ceva.common.test.TestTags;
import com.ceva.selenium.framework.page.AbstractPage;
import com.ceva.selenium.test.BaseTest;
import com.ceva.template.test.framework.page.AddToDoPage;
import com.ceva.template.test.framework.page.HomePage;
import com.ceva.template.test.framework.page.LdapUserSearchPanel;
import com.ceva.template.test.framework.page.ListToDoPage;
import com.ceva.template.test.framework.page.ToDoInfo;

/**
 * Ascending the method name in execution.
 */
@RunWith(JUnitPlatform.class)
@TestMethodOrder(Alphanumeric.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AddToDoPageTest extends BaseTest {

  static String ldapPanelName = "addToDo";
  static String ldapPanelSearchName = "Mahendran";
  LoginPage loginPage;
  HomePage homePage;
  ListToDoPage listToDoPage;
  AddToDoPage addToDoPage;
  BaseHomePage baseHomePage;
  UserProfilePage userProfilePage;
  LdapUserSearchPanel ldapUserSearchPanel;

  /**
   * It will be run before the current class.
   */
  @BeforeAll
  void setupBeforeClass() {
    openBrowser();
    loginPage = new LoginPage();
    homePage = new HomePage();
    listToDoPage = new ListToDoPage();
    addToDoPage = new AddToDoPage();
    baseHomePage = new BaseHomePage();
    userProfilePage = new UserProfilePage();
    ldapUserSearchPanel = new LdapUserSearchPanel();

    homePage.reInitializePageElementsForCurrentBrowser();
    loginPage.reInitializePageElementsForCurrentBrowser();
    listToDoPage.reInitializePageElementsForCurrentBrowser();
    addToDoPage.reInitializePageElementsForCurrentBrowser();
    baseHomePage.reInitializePageElementsForCurrentBrowser();
    userProfilePage.reInitializePageElementsForCurrentBrowser();
    ldapUserSearchPanel.reInitializePageElementsForCurrentBrowser();
    loginPage.goTo();
    loginPage.fillForm().withDefaultValues().login();
    baseHomePage.navigateToUserProfilePage();
    userProfilePage.enterDateFormat("", "MM/dd/yyyy").update("");;
  }

  @BeforeEach
  void setupBefore() {
    if (!listToDoPage.isAt()) {
      homePage.navigateToToDoPage();
    }
  }

  @AfterAll
  void setupAfterClass() {
    closeBrowser();
  }

  @Test
  @Tag(TestTags.TARGETED)
  void a01_visit_loadAddToDoPage() {
    if (listToDoPage.isAt()) {
      listToDoPage.clickAddNew();
      boolean hasString = AbstractPage.hasStringWithPatternOnPage("Add Todo");
      if (addToDoPage.isAt()) {
        Assertions.assertTrue(hasString, "Add Todo Page doesn't loaded");
      }
    }
  }

  @Test
  void a02_reset_allInputEmpty() {
    if (listToDoPage.isAt()) {
      listToDoPage.clickAddNew();
      if (addToDoPage.isAt()) {
        addToDoPage.enterTitle("Test").enterDescription("My Description").enterpriority("2")
            .enterduedate("11/17/2017").enterowner("Superman");
        addToDoPage.clickReset();
        ToDoInfo toDoInfo = addToDoPage.readValues();
        Assertions.assertTrue(toDoInfo.isEmpty(), "Reset button doesn't work in Add Todo page");
      }
    }
  }


  @Disabled("Bug - Kanboard task_6856 created")
  @Test
  void a03_cancelAtEdit_goesToListToDoPage() {
    String random = AbstractPage.getRandomString();
    ToDoInfo toDoForTest = addToDoPage.prepareFormData().withTitle("My Title Test" + random)
        .withDescription("My Description Test" + random).withPriority("3").withDueDate("03/07/2018")
        .withOwner("Superman Test" + random);
    listToDoPage.clickAddNew();
    addToDoPage.fillFormWith(toDoForTest);
    addToDoPage.clickAdd();
    if (listToDoPage.isAt()) {
      boolean hasToDo = listToDoPage.hasAnyToDo();
      if (hasToDo) {
        listToDoPage.clickEditAt(1);
        if (addToDoPage.isAt()) {
          addToDoPage.clickCancel();
          AbstractPage.waitUntilLoaded();
          Assertions.assertTrue(listToDoPage.isAt(),
              "This is a known issue (task_6856) which will be resolved in release 2.2");
        }
      }
    }
    listToDoPage.sortToDoById();
    boolean hasToDo = listToDoPage.hasAnyToDo();
    if (hasToDo) {
      listToDoPage.clickDeleteAt(1);
    }
  }

  @Test
  @Tag(TestTags.BUG_REPORTED)
  void a04_priorityValidate_failedMessage() {
    if (listToDoPage.isAt()) {
      listToDoPage.clickAddNew();
      if (addToDoPage.isAt()) {
        addToDoPage.enterpriority("Test").clickAdd();
        if (addToDoPage.isAt()) {
          Boolean hasString = AbstractPage.hasStringWithPatternOnPage("must be a number");
          Assertions.assertTrue(hasString, "Priority validation failed");
        }
      }
    }
  }

  @Test
  void a05_clickSearchLdapUserLookup_shownLdapSearchPopup() {
    listToDoPage.clickAddNew();
    addToDoPage.clickLdapUserSearchLookUp();
    String ariahidden = ldapUserSearchPanel.getSearchLdapUserFormAreaHidden("addToDo");
    Assertions.assertEquals(ariahidden, "false", "Owner Text search LDAP lookup didn't work");
    ldapUserSearchPanel.closeLdapSearchPopupPanel("addToDo");
  }

  @Test
  @Tag(TestTags.TARGETED)
  void a06_searchLdapUser_ensureSearchInDataTable() {
    listToDoPage.clickAddNew();
    addToDoPage.clickLdapUserSearchLookUp();
    ldapUserSearchPanel.enterLdapUserSearch(ldapPanelName, ldapPanelSearchName);
    ldapUserSearchPanel.clickLdapUserSearch(ldapPanelName);
    if (ldapUserSearchPanel.hasAnySearchLdapUser(ldapPanelName)) {
      ToDoInfo searchUser = ldapUserSearchPanel.getSearchLdapUserInfoAt(ldapPanelName, 1);
      Assertions.assertTrue(searchUser.searchText.contains("Mahendran"), "LDAP User Search failed");
    }
    ldapUserSearchPanel.closeLdapSearchPopupPanel("addToDo");
  }

  @Test
  void a07_searchLdapUserNameClick_namePlacedInOwnerText() {
    listToDoPage.clickAddNew();
    addToDoPage.clickLdapUserSearchLookUp();
    ldapUserSearchPanel.enterLdapUserSearch(ldapPanelName, ldapPanelSearchName);
    ldapUserSearchPanel.clickLdapUserSearch(ldapPanelName);
    if (ldapUserSearchPanel.hasAnySearchLdapUser(ldapPanelName)) {
      ToDoInfo searchUser = ldapUserSearchPanel.getSearchLdapUserInfoAt(ldapPanelName, 1);
      ldapUserSearchPanel.clickDataTableLdapUserName(ldapPanelName, 1);
      ToDoInfo toDoInfo = addToDoPage.readValues();
      Assertions.assertEquals(toDoInfo.ownerText, searchUser.searchText,
          "LDAP search user name doesn't placed the owner text");
    }
  }

  @Test
  void a08_searchLdapUserResetClick_dataTableShownNoRecords() {
    listToDoPage.clickAddNew();
    addToDoPage.clickLdapUserSearchLookUp();
    ldapUserSearchPanel.enterLdapUserSearch(ldapPanelName, ldapPanelSearchName);
    ldapUserSearchPanel.clickLdapUserSearch(ldapPanelName);
    if (ldapUserSearchPanel.hasAnySearchLdapUser(ldapPanelName)) {
      ldapUserSearchPanel.clickLdapUserReset(ldapPanelName);
      String searchData = ldapUserSearchPanel.getSearchTableNoRecordsMessage(ldapPanelName);
      ToDoInfo toDoInfo = ldapUserSearchPanel.populateSearchLdapPageInfo(ldapPanelName);
      Assertions.assertAll(
          () -> Assertions.assertEquals("No records found.", searchData,
              "LDAP User Search Reset failed in data table"),
          () -> Assertions.assertEquals("", toDoInfo.searchText,
              "LDAP User Search Reset failed in search text"));
    }
    ldapUserSearchPanel.closeLdapSearchPopupPanel("addToDo");
  }
}
