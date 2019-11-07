package com.ceva.template.test;

import java.util.Collection;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
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
import com.ceva.selenium.framework.page.AbstractPage;
import com.ceva.selenium.test.BaseTest;
import com.ceva.template.test.data.AddToDoPageTestData;
import com.ceva.template.test.framework.page.AddToDoPage;
import com.ceva.template.test.framework.page.HomePage;
import com.ceva.template.test.framework.page.ListToDoPage;
import com.ceva.template.test.framework.page.ToDoInfo;

/**
 * Ascending the method name in execution.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class AddToDoPageDataDrivencsvTest extends BaseTest {

  @Parameters
  public static Collection<AddToDoPageTestData> loadTestData() {
    return AddToDoPageTestData.readTestDataFromFile(
        "src\\test\\java\\com\\ceva\\template\\test\\testData\\AddToDoPageTestData.csv");
  }

  private AddToDoPageTestData toDoTestData;

  public AddToDoPageDataDrivencsvTest(AddToDoPageTestData toDoInfo) {
    this.toDoTestData = toDoInfo;
  }

  /**
   * Setup before class initialization.
   */
  @BeforeClass
  public static void setupBeforeClass() {
    openBrowser();
    LoginPage.INSTANCE.reInitializePageElementsForCurrentBrowser();
    HomePage.INSTANCE.reInitializePageElementsForCurrentBrowser();
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

  @Test
  public void a02_addTodo_ensureAdded() {
    Assume.assumeTrue(toDoTestData.getExpectedResult().equalsIgnoreCase("Success"));
    if (ListToDoPage.INSTANCE.isAt()) {
      ListToDoPage.INSTANCE.clickAddNew();
      ToDoInfo formData = toDoTestData.getToDo();
      AddToDoPage.INSTANCE.fillFormWith(formData);
      AddToDoPage.INSTANCE.clickAdd();
      if (ListToDoPage.INSTANCE.isAt()) {
        ToDoInfo recentlyAddedToDo = ListToDoPage.INSTANCE.getLatestToDo();
        Assert.assertEquals("Failed to show the newly added todo", formData, recentlyAddedToDo);
        ListToDoPage.INSTANCE.deleteLatestTodo();
      }
    }
  }

  @Disabled
  @Test
  public void a04_priorityValidate_failedMessage() {
    Assume.assumeTrue(
        toDoTestData.getExpectedResult().equalsIgnoreCase("Validation_failure_priority"));
    if (ListToDoPage.INSTANCE.isAt()) {
      ListToDoPage.INSTANCE.clickAddNew();
      if (AddToDoPage.INSTANCE.isAt()) {
        AddToDoPage.INSTANCE.enterpriority(toDoTestData.getToDo().priorityText).clickAdd();
        if (AddToDoPage.INSTANCE.isAt()) {
          Boolean hasString = AbstractPage.hasStringWithPatternOnPage("must be a number");
          Assert.assertTrue("Priority validation failed", hasString);
        }
      }
    }
  }
}
