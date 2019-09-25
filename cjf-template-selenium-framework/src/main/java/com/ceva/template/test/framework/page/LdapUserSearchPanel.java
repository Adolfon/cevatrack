package com.ceva.template.test.framework.page;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.ceva.selenium.framework.driver.Driver;
import com.ceva.selenium.framework.page.AbstractPage;

/**
 * LDAPUserSearchPanel Used for search the Owner.
 */
public class LdapUserSearchPanel extends AbstractPage {

  public static final LdapUserSearchPanel INSTANCE = new LdapUserSearchPanel();

  public LdapUserSearchPanel() {}

  public ToDoInfo prepareFormData() {
    return new ToDoInfo();
  }

  // ----- PRIVATE WEBELEMENT AND METHODS FOR EACH BUSINESS ELEMENT IN THIS PAGE ------ //

  private WebElement searchLdapUserText(String formName) {
    return getWebDriver()
        .findElement(By.xpath("//*[@id='frm_" + formName + ":cjf_todo_ownerSearch:txt_prefix']"));
  }

  private WebElement ldapUserSearchButton(String formName) {
    return getWebDriver()
        .findElement(By.xpath("//*[@id='frm_" + formName + ":cjf_todo_ownerSearch:btn_search']"));
  }

  private WebElement searchLdapUserResetButton(String formName) {
    return getWebDriver()
        .findElement(By.xpath("//*[@id='frm_" + formName + ":cjf_todo_ownerSearch:btn_reset']"));
  }

  private List<WebElement> searchLdapUserCount(String formName) {
    return getWebDriver().findElements(
        By.xpath("//*[@id='frm_" + formName + ":cjf_todo_ownerSearch:tbl_list_user_data']/tr"));
  }

  private WebElement searchLdapUserFormDisplay(String formName) {
    return getWebDriver().findElement(
        By.xpath("//*[@id='frm_" + formName + ":cjf_todo_ownerSearch:dlg_search_ldap']"));
  }

  private WebElement searchLdapUserFormClose(String formName) {
    return getWebDriver().findElement(
        By.xpath("//*[@id='frm_" + formName + ":cjf_todo_ownerSearch:dlg_search_ldap']/div[1]/a"));
  }

  private WebElement getSearchLdapUserRowAt(String formName, int rowNumber) {
    return getWebDriver().findElement(By.xpath("// *[@id='frm_" + formName
        + ":cjf_todo_ownerSearch:tbl_list_user_data']/tr[" + rowNumber + "]"));
  }

  private WebElement searchLdapUserDataTableName(String formName, int rowNumber) {
    return getWebDriver().findElement(By.xpath("// *[@id='frm_" + formName
        + ":cjf_todo_ownerSearch:tbl_list_user_data']/tr[" + rowNumber + "]/td[1]/a/span"));
  }

  private WebElement searchLdapUserDataTableNoRecordsMessage(String formName) {
    return getWebDriver().findElement(By.xpath(
        "// *[@id='frm_" + formName + ":cjf_todo_ownerSearch:tbl_list_user_data']/tr[1]/td[1]"));
  }

  // -------- PUBLIC METHODS FOR USER ACTIONS -------- //

  public void clickLdapUserSearch(String formName) {
    ldapUserSearchButton(formName).click();
    waitForAjaxToComplete();
  }

  public void clickLdapUserReset(String formName) {
    searchLdapUserResetButton(formName).click();
    waitForAjaxToComplete();
  }

  /**
   * Click the searched user name in data table.
   */
  public void clickDataTableLdapUserName(String formName, int rowNumber) {
    searchLdapUserDataTableName(formName, rowNumber).click();
    wait(1.0);
    waitForAjaxToComplete();
  }


  /**
   * Close the Ldap search poup panel.
   */
  public void closeLdapSearchPopupPanel(String formName) {
    searchLdapUserFormClose(formName).click();
    waitForAjaxToComplete();
  }


  // ----- PUBLIC METHODS FOR HELPER ------ //

  /**
   * Enter the search text value in the LDAP user search panel.
   */
  public LdapUserSearchPanel enterLdapUserSearch(String formName, String searchText) {
    clearAndEnterTextAndWaitForAjaxComplete(searchLdapUserText(formName), searchText);
    return this;
  }

  /**
   * Fill the search text value in the LDAP user search panel.
   */
  public LdapUserSearchPanel fillFormWithSearch(String formName, ToDoInfo todo) {
    enterLdapUserSearch(formName, todo.searchText);
    return this;
  }

  /**
   * Read search text value from the LDAP user search panel.
   * 
   * @return pageInfo
   */
  public ToDoInfo populateSearchLdapPageInfo(String formName) {
    ToDoInfo pageInfo = new ToDoInfo();
    pageInfo.searchText = searchLdapUserText(formName).getText();
    return pageInfo;
  }

  private ToDoInfo extractSearchLdapUser(WebElement row, int rowNum) {
    ToDoInfo pageInfo = new ToDoInfo();
    pageInfo.searchText = (row.findElement(By.xpath("td[1]/a/span")).getText());
    return pageInfo;
  }

  public ToDoInfo getSearchLdapUserInfoAt(String formName, int rowNum) {
    WebElement searchLdapUser = getSearchLdapUserRowAt(formName, rowNum);
    return extractSearchLdapUser(searchLdapUser, rowNum);
  }

  public int getSearchLdapUserCount(String formName) {
    return searchLdapUserCount(formName).size();
  }

  public boolean hasAnySearchLdapUser(String formName) {
    return getSearchLdapUserCount(formName) > 0;
  }

  /**
   * Get the aria-hidden attribute value from LDAP user search panel.
   */
  public String getSearchLdapUserFormAreaHidden(String frameName) {
    return searchLdapUserFormDisplay(frameName).getAttribute("aria-hidden");
  }

  public String getSearchTableNoRecordsMessage(String frameName) {
    return searchLdapUserDataTableNoRecordsMessage(frameName).getText();
  }

  @Override
  public String getUrl() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getTitle() {
    // TODO Auto-generated method stub
    return null;
  }
}
