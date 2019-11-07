package com.ceva.template.test.framework.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.ceva.selenium.framework.page.AbstractPage;

/**
 * AddToDoPage Used for AddToDoPage test.
 */
public class AddToDoPage extends AbstractPage {

  public static final AddToDoPage INSTANCE = new AddToDoPage();
  private String url = BASE_URL + "/add-todo.xhtml";
  private String title = "CJF-TEMPLATE";

  public AddToDoPage() {}

  public ToDoInfo prepareFormData() {
    return new ToDoInfo();
  }

  /**
   * Read the ToDoInfo text values.
   * 
   * @return toDoInfo
   */
  public ToDoInfo readValues() {
    ToDoInfo toDoInfo = new ToDoInfo();
    populatePageInfo(toDoInfo);
    return toDoInfo;
  }

  @Override
  public String getUrl() {
    return this.url;
  }

  @Override
  public String getTitle() {
    return this.title;
  }

  // ----- PRIVATE WEBELEMENT FOR EACH BUSINESS ELEMENT IN THIS PAGE ------ //

  @FindBy(xpath = "//*[@id='frm_addToDo:txt_title']")
  private WebElement titleText;

  @FindBy(xpath = "//*[@id='frm_addToDo:txt_description']")
  private WebElement descriptionText;

  @FindBy(xpath = "//*[@id='frm_addToDo:txt_priority']")
  private WebElement priorityText;

  @FindBy(xpath = "//*[@id='frm_addToDo:dte_dueDate_input']")
  private WebElement duedate;

  @FindBy(xpath = "//*[@id='frm_addToDo:txt__owner']")
  private WebElement ownerText;

  @FindBy(xpath = "//*[@id='frm_addToDo:cjf_todo_ownerSearch:btn_ldapSearch_searchIcon']")
  private WebElement searchLdapUserLookUp;

  @FindBy(xpath = "//*[@id='frm_addToDo:btn_add']")
  private WebElement addButton;

  @FindBy(xpath = "//*[@id='frm_addToDo:btn_reset']")
  private WebElement resetButton;

  @FindBy(xpath = "//*[@id='frm_addToDo:btn_save']")
  private WebElement saveButton;

  @FindBy(xpath = "//*[@id='frm_addToDo:btn_cancel']")
  private WebElement cancelButton;

  // -------- PUBLIC METHODS FOR USER ACTIONS -------- //
  /**
   * Clicks the add button.
   */
  public void clickAdd() {
    addButton.click();
    wait(1.0);
  }

  public void clickReset() {
    resetButton.click();
    waitForPageToLoad(10);
  }

  public void clickSave() {
    saveButton.click();
    waitForAjaxToComplete();
  }

  public void clickCancel() {
    cancelButton.click();
    waitForAjaxToComplete();
  }

  /**
   * Click the owner text search lookup.
   */
  public void clickLdapUserSearchLookUp() {
    searchLdapUserLookUp.click();
    wait(1.0);
    waitForAjaxToComplete();
  }

  // ----- PUBLIC METHODS FOR HELPER ------ //

  public AddToDoPage enterTitle(String title) {
    clearAndEnterTextAndWaitForAjaxComplete(titleText, title);
    return this;
  }

  public AddToDoPage enterDescription(String description) {
    clearAndEnterTextAndWaitForAjaxComplete(descriptionText, description);
    return this;
  }

  public AddToDoPage enterpriority(String priority) {
    clearAndEnterTextAndWaitForAjaxComplete(priorityText, priority);
    return this;
  }

  public AddToDoPage enterduedate(String duedates) {
    clearAndEnterTextAndWaitForAjaxComplete(duedate, duedates);
    return this;
  }

  public AddToDoPage enterowner(String owner) {
    clearAndEnterTextAndWaitForAjaxComplete(ownerText, owner);
    return this;
  }

  /**
   * Fill value in all text.
   * 
   * @return todo
   */
  public AddToDoPage fillFormWith(ToDoInfo todo) {
    enterTitle(todo.titleText);
    enterDescription(todo.descriptionText);
    enterpriority(todo.priorityText);
    enterduedate(todo.dueDate);
    enterowner(todo.ownerText);
    return this;
  }

  /**
   * Read the value in all text.
   * 
   * @return toDoInfo
   */
  public AddToDoPage populatePageInfo(ToDoInfo toDoInfo) {
    toDoInfo.titleText = titleText.getAttribute("value");
    toDoInfo.descriptionText = descriptionText.getAttribute("value");
    toDoInfo.priorityText = priorityText.getAttribute("value");
    toDoInfo.dueDate = duedate.getAttribute("value");
    toDoInfo.ownerText = ownerText.getAttribute("value");
    return this;
  }

  /**
   * Add ToDo row.
   */
  public void createToDo(ToDoInfo toDoInfo) {
    fillFormWith(toDoInfo);
    clickAdd();
  }
}
