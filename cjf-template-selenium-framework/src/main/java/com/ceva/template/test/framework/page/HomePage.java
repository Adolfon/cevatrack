package com.ceva.template.test.framework.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.ceva.cjf.base.selenium.framework.page.BaseHomePage;
import com.ceva.selenium.framework.driver.Driver;

/**
 * HomePage has all the project(todo) specific Menu's and respective methods. If you removed or
 * moved any Menu's/elements related to aaf or cjf-base in your project, then cjf cannot identify
 * those WebElements and hence the tests wont run.
 * 
 * <p>
 * In that case, Please create your own HomePage class like this class to include your WebElements.
 * </p>
 */
public class HomePage extends BaseHomePage {

  public static final HomePage INSTANCE = new HomePage();

  private String url = BASE_URL + "/list-todo.xhtml";
  private String title = "CJF-TEMPLATE";

  // ----- PRIVATE WEBELEMENTS FOR EACH BUSINESS ELEMENT IN THIS PAGE ------ //

  @FindBy(xpath = "//*/a[span/text()='Administration']")
  private WebElement administrationMenu;

  @FindBy(xpath = "//*/a[span/text()='Configuration']")
  private WebElement configuration;

  @FindBy(xpath = "//*/a[span/text()='TODO']")
  private WebElement todoMenu;

  @FindBy(xpath = "//*[@id='frm_searchToDo:txt_todo_title']")
  private WebElement searchTitleTodo;

  // -------- PUBLIC METHODS FOR USER ACTIONS -------- //

  /**
   * To click administration menu.
   */
  public void navigateToAdministration() {
    Actions action = new Actions(Driver.getWebDriver());
    administrationMenu.click();
    wait(1.0);
    action.moveToElement(configuration).click().build().perform();
    waitForPageToLoad(PAGELOAD_TIMEOUT_IN_SECONDS);
  }

  /**
   * To navigate todo page.
   */
  public void navigateToToDoPage() {
    todoMenu.click();
    waitForPageToLoad(PAGELOAD_TIMEOUT_IN_SECONDS);
    wait.until(ExpectedConditions.elementToBeClickable(searchTitleTodo));
  }

  @Override
  public String getUrl() {
    return this.url;
  }

  @Override
  public String getTitle() {
    return this.title;
  }

}
