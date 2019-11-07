package com.ceva.template.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import com.ceva.cjf.base.selenium.framework.page.BaseHomePage;
import com.ceva.cjf.base.selenium.framework.page.ChangePasswordPage;
import com.ceva.cjf.base.selenium.framework.page.LoginPage;
import com.ceva.cjf.base.selenium.framework.page.UserProfilePage;
import com.ceva.cjf.base.selenium.framework.page.UserSignupInfo;
import com.ceva.cjf.base.selenium.framework.page.UserSignupPage;
import com.ceva.common.test.TestTags;
import com.ceva.common.test.extension.CleanSql;
import com.ceva.common.test.extension.DatabaseConnectionExtension;
import com.ceva.common.test.extension.PrepareSql;
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
public class ListToDoPageTest extends BaseTest {

  static String ldapPanelName = "searchToDo";
  static String random = AbstractPage.getRandomString();
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
  DateTimeFormatter dateAndTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
  static String startDate = "06/25/2018";
  static String endDate = "07/27/2018";
  static String ownerName = "Mahendran";
  LoginPage loginPage;
  HomePage homePage;
  ListToDoPage listToDoPage;
  AddToDoPage addToDoPage;
  BaseHomePage baseHomePage;
  UserProfilePage userProfilePage;
  LdapUserSearchPanel ldapUserSearchPanel;
  UserSignupPage userSignupPage;
  ChangePasswordPage changePasswordPage;

  /**
   * Setup before class initialization.
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
    userSignupPage = new UserSignupPage();
    changePasswordPage = new ChangePasswordPage();
    ldapUserSearchPanel = new LdapUserSearchPanel();
    loginPage.reInitializePageElementsForCurrentBrowser();
    homePage.reInitializePageElementsForCurrentBrowser();
    listToDoPage.reInitializePageElementsForCurrentBrowser();
    addToDoPage.reInitializePageElementsForCurrentBrowser();
    userSignupPage.reInitializePageElementsForCurrentBrowser();
    changePasswordPage.reInitializePageElementsForCurrentBrowser();
    ldapUserSearchPanel.reInitializePageElementsForCurrentBrowser();
    loginPage.goTo();
    loginPage.fillForm().withDefaultValues().login();
    baseHomePage.reInitializePageElementsForCurrentBrowser();
    userProfilePage.reInitializePageElementsForCurrentBrowser();
    baseHomePage.navigateToUserProfilePage();
    userProfilePage.enterDateFormat("", "MM/dd/yyyy").update("");
    userProfilePage.enterTimeFormat("", "HH:mm:ss").update("");
    listToDoPage.goTo();
    ToDoInfo toDoForTest = addToDoPage.prepareFormData().withTitle("My Title Test" + random)
        .withDescription("My Description Test" + random).withPriority("3")
        .withDueDate("07/25/2018 04:05:30").withOwner("Superman Test" + random);
    listToDoPage.clickAddNew();
    addToDoPage.createToDo(toDoForTest);
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

  private ToDoInfo getTemplateTodoInfo() {
    return addToDoPage.prepareFormData().withTitle("My Title Test" + random)
        .withDescription("My Description Test" + random).withPriority("3")
        .withDueDate("07/25/2018 04:17:23").withOwner("Superman Test" + random);
  }

  @Test
  void a01_navigateToListTodoPage_loadListToDoPage() {
    boolean hasString = AbstractPage.hasStringWithPatternOnPage("List Todos");
    if (listToDoPage.isAt()) {
      Assertions.assertTrue(hasString, "ListToDo Page doesn't loaded");
    }
  }

  @Test
  @Tag(TestTags.BUG_REPORTED)
  void a02_clickAddTodo_navigateToToDoPage() {
    listToDoPage.clickAddNew();
    Assertions.assertTrue(addToDoPage.isAt());
  }

  @Test
  void a03_addToDo_ensureAdded() {
    listToDoPage.clickAddNew();
    ToDoInfo formData = getTemplateTodoInfo();
    addToDoPage.fillFormWith(formData).clickAdd();
    ToDoInfo recentlyAddedToDo = listToDoPage.getLatestToDo();
    Assertions.assertEquals(formData, recentlyAddedToDo, "Failed to show the newly added todo");
    listToDoPage.deleteLatestTodo();
    listToDoPage.deleteConfirm();
  }

  @Test
  @Tag(TestTags.TARGETED)
  void a04_clickEdit_loadEditedDataInToDoPage() {
    if (listToDoPage.hasAnyToDo()) {
      ToDoInfo currentToDo = listToDoPage.getToDoAt(1);
      listToDoPage.clickEditAt(1);
      ToDoInfo formValues = addToDoPage.readValues();
      Assertions.assertEquals(currentToDo, formValues,
          "Edit data doesn't load properly in todo page");
    }
  }

  @Disabled("Bug - Kanboard Task #6856 created")
  @Test
  void a05_editToDo_ensureEditChanged() {
    if (listToDoPage.hasAnyToDo()) {
      listToDoPage.sortToDoById();
      final ToDoInfo currentToDo = listToDoPage.getToDoAt(1);
      listToDoPage.clickEditAt(1);
      ToDoInfo editValues = getTemplateTodoInfo().withDescription("EditDescriptionText" + random)
          .withTitle("EditTitleText" + random);
      addToDoPage.fillFormWith(editValues).clickSave();
      listToDoPage.sortToDoById();
      ToDoInfo changedToDo = listToDoPage.getToDoById(currentToDo.id);
      Assertions.assertEquals(editValues, changedToDo,
          "Mismatch the edit todo (This is a known issue (task_6856) "
              + "which will be resolved in release 2.2)");
    }
  }

  @Test
  void a06_searchStartDateValidation_showValidationMessage() {
    listToDoPage.enterSearchStartDate("23/07/2018");
    listToDoPage.search();
    String startDateValidateMsg = listToDoPage.getStartDateValidationMessage();
    Assertions.assertTrue(
        startDateValidateMsg
            .contains("Start Date: '23/07/2018' could not be understood as a date. Example:"),
        "Doesn't validated the start date");
  }

  @Test
  void a07_searchEndDateValidation_showValidationMessage() {
    listToDoPage.enterSearchEndDate("23/07/2018");
    listToDoPage.search();
    String endDateValidateMsg = listToDoPage.getEndDateValidationMessage();
    Assertions.assertTrue(
        endDateValidateMsg
            .contains("End Date: '23/07/2018' could not be understood as a date. Example:"),
        "Doesn't validated the start date");
  }

  @Disabled("Bug - Kanboard task_7613 created")
  @Test
  void a08_searchListTodo_loadSearchedData() {
    ToDoInfo formdata =
        getTemplateTodoInfo().withStartDate(startDate).withEndDate(endDate).withOwner(ownerName);
    listToDoPage.clickAddNew();
    addToDoPage.createToDo(formdata);
    listToDoPage.fillSearchForm(formdata).search();
    if (listToDoPage.hasAnyToDo()) {
      ToDoInfo searchedData = listToDoPage.getToDoAt(1);
      Assertions.assertEquals(searchedData, formdata, "List Todo search failed");
    }
  }

  @Test
  void a09_resetSearchForm_resetSuccessfully() {
    ToDoInfo formdata =
        getTemplateTodoInfo().withStartDate(startDate).withEndDate(endDate).withOwner(ownerName);
    listToDoPage.fillSearchForm(formdata).reset();
    ToDoInfo readFormData = listToDoPage.getSearchFormValues();
    Assertions.assertTrue(readFormData.isEmpty(), "Search Reset button doesn't work");
  }

  @Test
  void a10_clickSearchLdapUserLookup_ShownLdapSearchPopup() {
    listToDoPage.clickOwnerLookup();
    String ariahidden = ldapUserSearchPanel.getSearchLdapUserFormAreaHidden(ldapPanelName);
    Assertions.assertEquals(ariahidden, "false", "Owner Text search LDAP lookup didn't work");
    ldapUserSearchPanel.closeLdapSearchPopupPanel(ldapPanelName);
  }

  @Test
  void a11_searchLdapUserNameClick_namePlacedInListTodoOwnerText() {
    listToDoPage.clickOwnerLookup();
    ldapUserSearchPanel.enterLdapUserSearch(ldapPanelName, ownerName);
    ldapUserSearchPanel.clickLdapUserSearch(ldapPanelName);
    if (ldapUserSearchPanel.hasAnySearchLdapUser("ldapPanel")) {
      ToDoInfo searchUser = ldapUserSearchPanel.getSearchLdapUserInfoAt(ldapPanelName, 1);
      ldapUserSearchPanel.clickDataTableLdapUserName(ldapPanelName, 1);
      ToDoInfo toDoInfo = listToDoPage.getSearchFormValues();
      Assertions.assertTrue(toDoInfo.ownerText.contains(searchUser.searchText),
          "LDAP search user name doesn't placed the search list todo owner text");
    }
    ldapUserSearchPanel.closeLdapSearchPopupPanel(ldapPanelName);
  }

  @Test
  void a12_searchDueDate_ensureDueDateBetweenTheStartAndEndDate() {
    listToDoPage.enterSearchStartDate("07/21/2018");
    listToDoPage.enterSearchEndDate("07/27/2018");
    listToDoPage.search();
    ToDoInfo toDoInfo = listToDoPage.getSearchFormValues();
    ToDoInfo searchData = listToDoPage.getToDoAt(1);
    LocalDate dueDate = LocalDate.parse(searchData.dueDate, dateAndTimeFormat);
    LocalDate startDate = LocalDate.parse(toDoInfo.startDateText, formatter);
    LocalDate endDate = LocalDate.parse(toDoInfo.endDateText, formatter);
    Assertions.assertTrue((!dueDate.isBefore(startDate) && !dueDate.isAfter(endDate)),
        "LDAP search user name doesn't placed the owner text");
  }

  @Test
  @ExtendWith(DatabaseConnectionExtension.class)
  @PrepareSql("preparescript.sql")
  @CleanSql("cleanupscript.sql")
  void a13_deleteToDoAddedByRule_loadListToDoPage() {
    if (listToDoPage.isAt()) {
      listToDoPage.sortToDoById();
      listToDoPage.clickDeleteAt(1);
      listToDoPage.clickDeletePopup();
      listToDoPage.reset();
      ToDoInfo formdata = addToDoPage.prepareFormData().withTitle("test db");
      listToDoPage.fillSearchForm(formdata).search();
      listToDoPage.sortToDoById();
      listToDoPage.clickDeleteAt(1);
      listToDoPage.clickDeletePopup();
      Assertions.assertTrue(listToDoPage.isAt());
    }
  }

  @Test
  void a14_addToDo_ensureAddedAfterChangeDateAndTimeFormat() {
    baseHomePage.navigateToUserProfilePage();
    userProfilePage.enterDateFormat("", "dd/MM/yyyy").update("");
    userProfilePage.enterTimeFormat("", "hh:mm:ss a").update("");
    listToDoPage.goTo();
    listToDoPage.clickAddNew();
    ToDoInfo formData = getTemplateTodoInfo();
    formData.withDueDate("25/07/2018 11:09:50 AM");
    addToDoPage.fillFormWith(formData).clickAdd();
    ToDoInfo recentlyAddedToDo = listToDoPage.getLatestToDo();
    Assertions.assertEquals(formData, recentlyAddedToDo, "Failed to show the newly added todo");
    listToDoPage.deleteLatestTodo();
    listToDoPage.deleteConfirm();
  }

  @Test
  void a15_searchStartDateValidationAfterChangeDateFormat_showValidationMessage() {
    listToDoPage.enterSearchStartDate("07/23/2018");
    listToDoPage.search();
    String startDateValidateMsg = listToDoPage.getStartDateValidationMessage();
    Assertions.assertTrue(
        startDateValidateMsg
            .contains("Start Date: '07/23/2018' could not be understood as a date. Example:"),
        "Doesn't validated the start date");
  }

  @Test
  void a16_searchEndDateValidationAfterChangeDateFormat_showValidationMessage() {
    listToDoPage.enterSearchEndDate("07/23/2018");
    listToDoPage.search();
    String endDateValidateMsg = listToDoPage.getEndDateValidationMessage();
    Assertions.assertTrue(
        endDateValidateMsg
            .contains("End Date: '07/23/2018' could not be understood as a date. Example:"),
        "Doesn't validated the start date");
  }

  @Test
  void a17_accessApplicationAfterSignup_canViewToDoWithoutPermissions() {
    loginPage.logout();
    userSignupPage.goTo();
    UserSignupInfo userSignupInfo = userSignupPage.prepareFormData()
        .withPassingValues("example" + randomString + "@gmail.com", "Example", "User", "Tempor@ry");
    userSignupPage.fillFormWith(userSignupInfo).clickAdd();
    Assertions.assertEquals("You have registered successfully.", loginPage.getMessage(),
        "User was not able to register");
    loginPage.fillForm().withloginText(userSignupInfo.getEmailId())
        .withPasswordText(userSignupInfo.getPassword()).login();
    changePasswordPage.changePassword("Tempor@ry1");
    loginPage.fillForm().withloginText(userSignupInfo.getEmailId()).withPasswordText("Tempor@ry1")
        .login();
    Assertions.assertEquals("Search Todo", listToDoPage.getSearchTodoTitleHeaderName(),
        "Page element(s) not visible");
    loginPage.logout();
    loginPage.fillForm().withDefaultValues().login();
  }

  @Test
  void a18_addToDo_ensureAddedAfterChangeTimeFormat() {
    baseHomePage.navigateToUserProfilePage();
    userProfilePage.enterDateFormat("", "dd/MM/yyyy").update("");
    userProfilePage.enterTimeFormat("", "hh:mm:ss.s a").update("");
    listToDoPage.goTo();
    listToDoPage.clickAddNew();
    ToDoInfo formData = getTemplateTodoInfo().withDueDate("25/07/2018 11:09:50.50 AM");
    addToDoPage.fillFormWith(formData).clickAdd();
    ToDoInfo recentlyAddedToDo = listToDoPage.getLatestToDo();
    Assertions.assertEquals(formData, recentlyAddedToDo, "Failed to show the newly added todo");
    listToDoPage.deleteLatestTodo();
    listToDoPage.deleteConfirm();
  }

  @Test
  void a19_addToDo_ensureAddedAfterChangeTimeFormat() {
    baseHomePage.navigateToUserProfilePage();
    userProfilePage.enterTimeFormat("", "HH:mm").update("");
    listToDoPage.goTo();
    listToDoPage.clickAddNew();
    ToDoInfo formData = getTemplateTodoInfo().withDueDate("25/07/2018 20:09");
    addToDoPage.fillFormWith(formData).clickAdd();
    ToDoInfo recentlyAddedToDo = listToDoPage.getLatestToDo();
    Assertions.assertEquals(formData, recentlyAddedToDo, "Failed to show the newly added todo");
    listToDoPage.deleteLatestTodo();
    listToDoPage.deleteConfirm();
  }

  @Test
  void a20_addToDo_ensureAddedAfterChangeTimeFormat() {
    baseHomePage.navigateToUserProfilePage();
    userProfilePage.enterTimeFormat("", "HH:mm:ss.s").update("");
    listToDoPage.goTo();
    listToDoPage.clickAddNew();
    ToDoInfo formData = getTemplateTodoInfo().withDueDate("25/07/2018 11:09:50.25");
    addToDoPage.fillFormWith(formData).clickAdd();
    ToDoInfo recentlyAddedToDo = listToDoPage.getLatestToDo();
    Assertions.assertEquals(formData, recentlyAddedToDo, "Failed to show the newly added todo");
    listToDoPage.deleteLatestTodo();
    listToDoPage.deleteConfirm();
  }

  @Test
  void a21_addToDo_ensureAddedAfterChangeTimeFormat() {
    baseHomePage.navigateToUserProfilePage();
    userProfilePage.enterTimeFormat("", "hh:mm a").update("");
    listToDoPage.goTo();
    listToDoPage.clickAddNew();
    ToDoInfo formData = getTemplateTodoInfo().withDueDate("25/07/2018 11:09 AM");;
    addToDoPage.fillFormWith(formData).clickAdd();
    ToDoInfo recentlyAddedToDo = listToDoPage.getLatestToDo();
    Assertions.assertEquals(formData, recentlyAddedToDo, "Failed to show the newly added todo");
    listToDoPage.deleteLatestTodo();
    listToDoPage.deleteConfirm();
  }
}
