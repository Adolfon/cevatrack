package com.ceva.template.test.framework.page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * Data Holder for ToDo Page.
 */
public class ToDoInfo {

  private static final String DEFAULT_TITLE = "primary";
  private static final String DEFAULT_DESCRIPTION = "Product Code";
  private static final String DEFAULT_PRIORITY = "1";
  private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
  private static final String DEFAULT_OWNER = "Manager";
  private static final String TEST_DATE = "02/22/2018";
  LocalDate date = LocalDate.now();
  public String id = "";
  public String titleText = "";
  public String descriptionText = "";
  public String priorityText = "";
  public String dueDate = "";
  public String searchText = "";
  public String ownerText = "";
  public String startDateText = "";
  public String endDateText = "";

  /**
   * ToDoInfo Constructor.
   */
  public ToDoInfo() {
    /**
     * Zero Parameter Constructor
     */
  }

  /**
   * Create the ToDoInfo constructor.
   * 
   * @param toDoInfo ToDoInfo object
   */
  public ToDoInfo(ToDoInfo toDoInfo) {
    super();
    this.id = toDoInfo.id;
    this.titleText = toDoInfo.titleText;
    this.descriptionText = toDoInfo.descriptionText;
    this.priorityText = toDoInfo.priorityText;
    this.dueDate = toDoInfo.dueDate;
    this.searchText = toDoInfo.searchText;
    this.ownerText = toDoInfo.ownerText;
    this.searchText = toDoInfo.startDateText;
    this.endDateText = toDoInfo.endDateText;
  }

  @Override
  public boolean equals(Object other) {
    boolean result = false;
    if (other instanceof ToDoInfo) {
      ToDoInfo that = (ToDoInfo) other;
      if (this.titleText != null && that.titleText != null) {
        result = this.titleText.equals(that.titleText);
      }
      if (result && this.descriptionText != null && that.descriptionText != null) {
        result = this.descriptionText.equals(that.descriptionText);
      }
      if (result && this.priorityText != null && that.priorityText != null) {
        result = this.priorityText.equals(that.priorityText);
      }
      if (result && this.ownerText != null && that.ownerText != null) {
        result = this.ownerText.equals(that.ownerText);
      }
    }
    return result;
  }

  /**
   * Read the Parameterized value.
   * 
   * @return Arrays
   */
  public static Collection<Object[]> data() {

    return Arrays.asList(new Object[][] {
        {"primary", "My Title Test", "My Description Test", "3", TEST_DATE, "Superman Test"},
        {"primary123", "My Title Test123", "My Description Test123", "4", TEST_DATE,
            "Superman Test123"},
        {"primary321", "My Title Test321", "My Description Test321", "5", TEST_DATE,
            "Superman Test321"}});
  }

  public ToDoInfo withId(String id) {
    this.id = id;
    return this;
  }

  public ToDoInfo withTitle(String title) {
    this.titleText = title;
    return this;
  }

  public ToDoInfo withDescription(String description) {
    this.descriptionText = description;
    return this;
  }

  public ToDoInfo withPriority(String priority) {
    this.priorityText = priority;
    return this;
  }

  public ToDoInfo withDueDate(String date) {
    this.dueDate = date;
    return this;
  }

  public ToDoInfo withSearchText(String search) {
    this.searchText = search;
    return this;
  }

  public ToDoInfo withOwner(String owner) {
    this.ownerText = owner;
    return this;
  }

  public ToDoInfo withStartDate(String startDate) {
    this.startDateText = startDate;
    return this;
  }

  public ToDoInfo withEndDate(String endDate) {
    this.endDateText = endDate;
    return this;
  }

  /**
   * Get the all text Default values.
   * 
   * @return object
   */
  public ToDoInfo withDefaultValues() {
    this.titleText = DEFAULT_TITLE;
    this.descriptionText = DEFAULT_DESCRIPTION;
    this.priorityText = DEFAULT_PRIORITY;
    this.dueDate = dateFormat.format(date);
    this.ownerText = DEFAULT_OWNER;
    return this;
  }

  public boolean isEmpty() {
    ToDoInfo emptyTodoInfo = new ToDoInfo();
    return emptyTodoInfo.equals(this);
  }

  @Override
  public int hashCode() {
    return Objects.hash(titleText, descriptionText, priorityText, ownerText);
  }
}
