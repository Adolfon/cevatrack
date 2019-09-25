package com.ceva.template.test;

import java.util.Collection;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import com.ceva.cjf.base.selenium.framework.page.LoginPage;
import com.ceva.selenium.test.BaseTest;
import com.ceva.template.test.framework.page.HomePage;
import com.ceva.template.test.framework.page.ListToDoPage;
import com.ceva.template.test.framework.page.ToDoInfo;

/**
 * Ascending the method name in execution.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class AddToDoPageParameterizedTest extends BaseTest {

  @Parameters
  public static Collection<Object[]> datas() {
    return ToDoInfo.data();
  }

  public String id;
  public String titleText;
  public String descriptionText;
  public String priorityText;
  public String duedate;
  public String ownerText;

  /**
   * Create the constructor for AddToDoPageParameterizedTest.
   * 
   * @param id Id
   * @param titleText Title
   * @param descriptionText Description
   * @param priorityText Priority
   * @param duedate Date
   * @param ownerText Owner
   */
  public AddToDoPageParameterizedTest(String id, String titleText, String descriptionText,
      String priorityText, String duedate, String ownerText) {
    this.id = id;
    this.titleText = titleText;
    this.descriptionText = descriptionText;
    this.priorityText = priorityText;
    this.duedate = duedate;
    this.ownerText = ownerText;

  }

  /**
   * Setup before class initialization.
   */
  @BeforeClass
  public static void setupBeforeClass() {
    openBrowser();
    LoginPage.INSTANCE.reInitializePageElementsForCurrentBrowser();
    LoginPage.INSTANCE.goTo();
    LoginPage.INSTANCE.fillForm().withDefaultValues().login();
  }

  @Before
  public void setupBefore() {
    HomePage.INSTANCE.navigateToToDoPage();
  }

  @AfterClass
  public static void setupAfterClass() {
    closeBrowser();
  }

  @Disabled
  @Test()
  public void a02_addToDoDefaultValues_addSuccessfully() {
    if (ListToDoPage.INSTANCE.isAt()) {
      ListToDoPage.INSTANCE.clickAddNew();
      // AddToDoPage.INSTANCE.fillFormWith(todo).clickAdd();
      Assert.assertTrue(ListToDoPage.INSTANCE.isAt());
      ListToDoPage.INSTANCE.deleteLatestTodo();
    }
  }
}
