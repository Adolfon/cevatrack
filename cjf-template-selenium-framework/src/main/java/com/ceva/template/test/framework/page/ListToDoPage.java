package com.ceva.template.test.framework.page;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.ceva.cjf.base.selenium.framework.page.ChangePasswordPage;
import com.ceva.selenium.framework.driver.Driver;
import com.ceva.selenium.framework.page.AbstractPage;

/**
 * ListToDoPage Used for ListToDoPage test.
 */
public class ListToDoPage extends AbstractPage {

  public static final ListToDoPage INSTANCE = new ListToDoPage();
  private String url = BASE_URL + "/list-todo.xhtml";
  private String title = "CJF-TEMPLATE";


  @Override
  public String getUrl() {
    return this.url;
  }

  @Override
  public String getTitle() {
    return this.title;
  }

  // ----- PRIVATE WEBELEMENT AND METHODS FOR EACH BUSINESS ELEMENT IN THIS PAGE ------ //

  @FindBy(id = "frm_searchToDo:btn_addToDo")
  private WebElement addNewButton;

  @FindBy(id = "frm_searchToDo:btn_todo_search_submit")
  private WebElement searchButton;

  @FindBy(id = "frm_searchToDo:btn_todo_search_reset")
  private WebElement resetButton;

  @FindBy(id = "frm_searchToDo:txt_todo_title")
  private WebElement searchTitleText;

  @FindBy(id = "frm_searchToDo:txt_todo_description")
  private WebElement searchDescriptionText;

  @FindBy(id = "frm_searchToDo:txt_todo_startdate_input")
  private WebElement searchStartDateText;

  @FindBy(id = "frm_searchToDo:txt_todo_enddate_input")
  private WebElement searchEndDateText;

  @FindBy(id = "frm_searchToDo:txt_todo_owner")
  private WebElement searchOwnerText;

  @FindBy(id = "frm_searchToDo:cjf_todo_ownerSearch:btn_ldapSearch_searchIcon")
  private WebElement ownerLookUp;

  @FindBy(xpath = "//*[@id='frm_searchToDo:msg_todo_startdate']/span[2]")
  private WebElement startDateValidationMsg;

  @FindBy(xpath = "//*[@id='frm_searchToDo:msg_todo_enddate']/span[2]")
  private WebElement endDateValidationMsg;

  @FindBy(xpath = "//*[@id='frm_listToDo:tbl_list_data']/tr")
  private List<WebElement> todoCount;

  @FindBy(xpath = "//*[@id='frm_searchToDo:lbl_todo_title']")
  private WebElement searchTitleLabel;

  @FindBy(xpath = "//*[@id='frm_searchToDo:lbl_todo_description']")
  private WebElement searchDescriptionLabel;

  @FindBy(xpath = "//*[@id='frm_searchToDo:lbl_todo_startdate']")
  private WebElement searchStartDateLabel;

  @FindBy(xpath = "//*[@id='frm_searchToDo:lbl_todo_enddate']")
  private WebElement searchEndDateLabel;

  @FindBy(xpath = "//*[@id='frm_searchToDo:lbl_todo_owner']")
  private WebElement searchOwnerLabel;

  @FindBy(xpath = "//*[@id='frm_searchToDo:lbl_todo_priority']")
  private WebElement searchPriorityLabel;

  @FindBy(xpath = "//*[@id='frm_searchToDo:pnl_todo_srch']/legend")
  private WebElement searchTodoTitleHeader;

  @FindBy(xpath = "//*[@id='frm_listToDo:pnl_todo_list']/legend")
  private WebElement listTodoTitleHeader;

  @FindBy(xpath = "//*[@id='frm_listToDo:tbl_list:clm_id']")
  private WebElement listIdColumnHeader;

  @FindBy(xpath = "//*[@id='frm_searchToDo:som_todo_priority']")
  private WebElement searchPriorityDropDown;

  @FindBy(xpath = "//*[@id='frm_listToDo:tbl_list:0:cd_delete:delete_confirm_yes']")
  private WebElement deleteconfirm;

  @FindBy(linkText = "Cancel")
  private WebElement deleteCancel;

  private WebElement editButtonAt(int rowNumber) {
    return getWebDriver()
        .findElement(By.xpath("//*[@id='frm_listToDo:tbl_list:" + (rowNumber - 1) + ":btn_edit']"));
  }

  private WebElement deleteButtonAt(int rowNumber) {
    return getWebDriver()
        .findElement(By.id("frm_listToDo:tbl_list:" + (rowNumber - 1) + ":btn_delete"));
  }

  private WebElement getToDoRowAt(int rowNumber) {
    return getWebDriver()
        .findElement(By.xpath("//*[@id='frm_listToDo:tbl_list_data']/tr[" + rowNumber + "]"));
  }

  private WebElement getToDoIdSortableRow() {
    WebElement element = null;
    while (element == null) {
      // TODO Need to Fix Id
      element = getWebDriver().findElement(By.xpath("//*[@id='frm_listToDo:tbl_list:clm_id']"));
      try {
        element.isDisplayed();
      } catch (StaleElementReferenceException e) {
        element = null;
      }
    }
    return element;
  }

  private WebElement getToDoRowByToDoId(String toDoId) {
    return getWebDriver().findElement(
        By.xpath("//*[@id='frm_listToDo:tbl_list_data']/tr[td[1]/span[text()='" + toDoId + "']]"));
  }

  /**
   * Retrieves the WebElement for a given option in the search priority dropdown menu.
   * 
   * @param option option in the dropdown menu
   * @return WebElement representation of the retrieved option
   */
  public WebElement getSearchPriorityDropDownItem(String option) {
    searchPriorityDropDown.click();
    wait(1.0);
    return getWebDriver().findElement(
        By.xpath("//*[@id='frm_searchToDo:som_todo_priority_items']/li[text()='" + option + "']"));
  }

  // -------- PUBLIC METHODS FOR USER ACTIONS -------- //

  public String getSearchButtonName() {
    return searchButton.getText();
  }

  public String getResetButtonName() {
    return resetButton.getText();
  }

  public void clickSearchPriority(String option) {
    getSearchPriorityDropDownItem(option).click();
    waitForAjaxToComplete();
  }

  public String getListTodoTitleHeaderName() {
    return listTodoTitleHeader.getText();
  }

  public String getListIdColumnHeaderName() {
    return listIdColumnHeader.getText();
  }

  public String getSearchTodoTitleHeaderName() {
    return searchTodoTitleHeader.getText();
  }

  public String getSearchTitleLabelName() {
    return searchTitleLabel.getText();
  }

  public String getAddTodoButtonText() {
    return addNewButton.getText();
  }

  /**
   * Click the add todo button and wait for 1 seconds to load the todo page.
   */
  public void clickAddNew() {
    addNewButton.click();
    wait(1.0);
    waitForAjaxToComplete();
  }

  public void clickEditAt(int rowNumber) {
    editButtonAt(rowNumber).click();
    waitForAjaxToComplete();
  }

  public void search() {
    searchButton.click();
    waitForAjaxToComplete();
  }

  public void reset() {
    resetButton.click();
    waitForAjaxToComplete();
  }

  /**
   * Click the Owner lookup and wait for 1 seconds to load the LDAP search panel.
   */
  public void clickOwnerLookup() {
    ownerLookUp.click();
    wait(1.0);
    waitForAjaxToComplete();
  }

  // ----- PUBLIC METHODS FOR HELPER ------ //

  /**
   * Enter the search title in search panel.
   */
  private void enterSearchTitle(String title) {
    clearAndEnterTextAndWaitForAjaxComplete(searchTitleText, title);
  }

  /**
   * Enter the search description in search panel.
   */
  private void enterSearchDescription(String description) {
    clearAndEnterTextAndWaitForAjaxComplete(searchDescriptionText, description);
  }

  /**
   * Enter the search start date in search panel.
   */
  public void enterSearchStartDate(String startDate) {
    clearAndEnterTextAndWaitForAjaxComplete(searchStartDateText, startDate);
  }

  /**
   * Enter the search end date in search panel.
   */
  public void enterSearchEndDate(String endDate) {
    clearAndEnterTextAndWaitForAjaxComplete(searchEndDateText, endDate);
  }

  /**
   * Enter the search owner in search panel.
   */
  private void enterSearchOwner(String owner) {
    clearAndEnterTextAndWaitForAjaxComplete(searchOwnerText, owner);
  }

  /**
   * Fill the search data in search list todo form.
   */
  public ListToDoPage fillSearchForm(ToDoInfo todoInfo) {
    enterSearchTitle(todoInfo.titleText);
    enterSearchDescription(todoInfo.descriptionText);
    enterSearchOwner(todoInfo.ownerText);
    enterSearchStartDate(todoInfo.startDateText);
    enterSearchEndDate(todoInfo.endDateText);
    return this;
  }

  private ToDoInfo extractToDo(WebElement row) {
    ToDoInfo pageInfo = new ToDoInfo();
    pageInfo.id = (row.findElement(By.xpath("td[1]")).getText());
    pageInfo.titleText = (row.findElement(By.xpath("td[2]")).getText());
    pageInfo.descriptionText = (row.findElement(By.xpath("td[3]")).getText());
    pageInfo.priorityText = (row.findElement(By.xpath("td[4]")).getText());
    pageInfo.dueDate = (row.findElement(By.xpath("td[5]")).getText());
    pageInfo.ownerText = (row.findElement(By.xpath("td[6]")).getText());
    return pageInfo;
  }

  /**
   * Get the values from the search.
   * 
   * @return todoInfo
   */
  public ToDoInfo getSearchFormValues() {
    ToDoInfo todoInfo = new ToDoInfo();
    todoInfo.titleText = searchTitleText.getAttribute("value");
    todoInfo.descriptionText = searchDescriptionText.getAttribute("value");
    todoInfo.startDateText = searchStartDateText.getAttribute("value");
    todoInfo.endDateText = searchEndDateText.getAttribute("value");
    todoInfo.ownerText = searchOwnerText.getAttribute("value");
    return todoInfo;
  }

  /**
   * Get the latest ToDo row return extractToDo.
   */
  public ToDoInfo getLatestToDo() {
    sortToDoById();
    WebElement toDoRow = getToDoRowAt(1);
    return extractToDo(toDoRow);
  }

  public ToDoInfo getToDoAt(int rowNum) {
    WebElement toDoRow = getToDoRowAt(rowNum);
    return extractToDo(toDoRow);
  }

  public ToDoInfo getToDoById(String toDoId) {
    WebElement toDoRow = getToDoRowByToDoId(toDoId);
    return extractToDo(toDoRow);
  }

  public boolean hasAnyToDo() {
    return getToDoCount() > 0;
  }

  public int getToDoCount() {
    return todoCount.size();
  }

  /**
   * Sorted ToDo row by Id.
   */
  public void sortToDoById() {
    WebElement idHeader = getToDoIdSortableRow();
    idHeader.click();
    waitForAjaxToComplete();
  }

  /**
   * Delete latest ToDo row.
   */
  public void deleteLatestTodo() {
    sortToDoById();
    clickDeleteAt(1);
    waitForAjaxToComplete();
  }

  /**
   * Get the start date validation message.
   */
  public String getStartDateValidationMessage() {
    return startDateValidationMsg.getText();
  }

  /**
   * Get the end date validation message.
   */
  public String getEndDateValidationMessage() {
    return endDateValidationMsg.getText();
  }

  /**
   * Click Delete button.
   */
  public void clickDeleteAt(int rowNumber) {
    ((JavascriptExecutor) Driver.getWebDriver()).executeScript("return arguments[0].click();",
        deleteButtonAt(rowNumber));
    waitForAjaxToComplete();
  }

  /**
   * Click Delete popup.
   */
  public void clickDeletePopup() {
    ((JavascriptExecutor) Driver.getWebDriver()).executeScript("return arguments[0].click();",
        deleteconfirm);
    wait(1.0);
    waitForAjaxToComplete();
  }

  public void deleteConfirm() {
    clickDeletePopup();
    waitForAjaxToComplete();
  }

}
