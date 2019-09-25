package com.ceva.template.test.framework.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.ceva.selenium.framework.page.AbstractPage;

/**
 * AdministrationPage Used for AdministrationPage test.
 */
public class AdministrationPage extends AbstractPage {

  public static final AdministrationPage INSTANCE = new AdministrationPage();
  private String url = BASE_URL + "/admin.xhtml";
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

  @FindBy(xpath = "//*[@id='frm_admin:swt_healthStatus']")
  private WebElement inputSwitch;

  @FindBy(xpath = "//*[@id='frm_admin:btn_healthYes']")
  private WebElement confirmYes;

  // -------- PUBLIC METHODS FOR USER ACTIONS -------- //

  public void switchOff() {
    inputSwitch.click();
    waitForAjaxToComplete();
  }

  /**
   * Click the confirmation yes.
   */
  public void confirmedYes() {
    confirmYes.click();
    wait(1.0);
    waitForAjaxToComplete();
  }

  public boolean isDisplayedConfirmation() {
    return (!inputSwitch.getAttribute("class").contains("checked"));
  }

}
