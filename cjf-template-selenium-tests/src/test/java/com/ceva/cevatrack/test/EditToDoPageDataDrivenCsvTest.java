package com.ceva.template.test;

import java.util.Collection;
import org.junit.After;
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
import com.ceva.selenium.test.BaseTest;
import com.ceva.template.test.data.EditToDoPageTestData;
import com.ceva.template.test.framework.page.AddToDoPage;
import com.ceva.template.test.framework.page.HomePage;
import com.ceva.template.test.framework.page.ListToDoPage;
import com.ceva.template.test.framework.page.ToDoInfo;

/**
 * Ascending the method name in execution.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class EditToDoPageDataDrivenCsvTest extends BaseTest {

  @Parameters
  public static Collection<EditToDoPageTestData> loadTestData() {
    return EditToDoPageTestData.readTestDataFromFile(
        "src\\test\\java\\com\\ceva\\template\\test\\testData\\EditToDoPageTestData.csv");
  }

  private EditToDoPageTestData toDoTestData;

  public EditToDoPageDataDrivenCsvTest(EditToDoPageTestData toDoInfo) {
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

  /**
   * Setup before tests.
   */
  @Before
  public void setupBefore() {
    HomePage.INSTANCE.navigateToToDoPage();
    ListToDoPage.INSTANCE.clickAddNew();
    ToDoInfo formData = toDoTestData.getToDo();
    AddToDoPage.INSTANCE.fillFormWith(formData);
    AddToDoPage.INSTANCE.clickAdd();
  }

  /**
   * Setup after tests.
   */
  @After
  public void setupAfter() {
    HomePage.INSTANCE.navigateToToDoPage();
    ListToDoPage.INSTANCE.sortToDoById();
    boolean hasToDo = ListToDoPage.INSTANCE.hasAnyToDo();
    if (hasToDo) {
      ListToDoPage.INSTANCE.clickDeleteAt(1);
    }
  }

  @AfterClass
  public static void setupAfterClass() {
    closeBrowser();
  }

  @Disabled
  @Test
  public void a01_editToDo_ensureEditChanged() {
    Assume.assumeTrue(toDoTestData.getExpectedResult().equalsIgnoreCase("Success"));
    ListToDoPage.INSTANCE.sortToDoById();
    ToDoInfo currentToDo = ListToDoPage.INSTANCE.getToDoAt(1);
    ListToDoPage.INSTANCE.clickEditAt(1);
    ToDoInfo formValues = AddToDoPage.INSTANCE.readValues();
    if (currentToDo.equals(formValues)) {
      ToDoInfo editValues = toDoTestData.getEditToDo();
      AddToDoPage.INSTANCE.fillFormWith(editValues).clickSave();
      ListToDoPage.INSTANCE.sortToDoById();
      ToDoInfo changedToDo = ListToDoPage.INSTANCE.getToDoById(currentToDo.id);
      Assert.assertEquals("Mismatch the edit todo", editValues, changedToDo);
    }
  }

  @Test
  public void a02_editToDo_ensureEditChanged() {
    Assume.assumeTrue(toDoTestData.getExpectedResult().contains("Edited"));
    ListToDoPage.INSTANCE.sortToDoById();
    ToDoInfo currentToDo = ListToDoPage.INSTANCE.getToDoAt(1);
    ListToDoPage.INSTANCE.clickEditAt(1);
    ToDoInfo formValues = AddToDoPage.INSTANCE.readValues();
    if (currentToDo.equals(formValues)) {
      ToDoInfo editValues = toDoTestData.getEditToDo();
      String expectedResult = toDoTestData.getExpectedResult();
      if (expectedResult.equalsIgnoreCase("Title Edited")) {
        AddToDoPage.INSTANCE.enterTitle(editValues.titleText);
      } else if (expectedResult.equalsIgnoreCase("Description Edited")) {
        AddToDoPage.INSTANCE.enterDescription(editValues.descriptionText);
      } else if (expectedResult.equalsIgnoreCase("Priority Edited")) {
        AddToDoPage.INSTANCE.enterpriority(editValues.priorityText);
      } else if (expectedResult.equalsIgnoreCase("DueDate Edited")) {
        AddToDoPage.INSTANCE.enterduedate(editValues.dueDate);
      } else if (expectedResult.equalsIgnoreCase("Owner Edited")) {
        AddToDoPage.INSTANCE.enterowner(editValues.ownerText);
      }
      ToDoInfo editedValues = AddToDoPage.INSTANCE.readValues();
      AddToDoPage.INSTANCE.clickSave();
      ListToDoPage.INSTANCE.sortToDoById();
      ToDoInfo changedToDo = ListToDoPage.INSTANCE.getToDoById(currentToDo.id);
      Assert.assertEquals("Mismatch the edit todo", editedValues, changedToDo);
    }
  }
}
