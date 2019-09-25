package com.ceva.template.test.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import com.ceva.template.test.framework.page.ToDoInfo;

/**
 * Read the CSV file for AddToDoPage.
 */
public class AddToDoPageTestData {

  DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
  LocalDate date = LocalDate.now();

  private ToDoInfo toDo;
  private String expectedResult;

  // TODO write the CSV format
  // title,desc,priority,duedate,owner,result
  /**
   * Read the CSV File.
   * 
   * @return testDataList TestData
   */
  public static Collection<AddToDoPageTestData> readTestDataFromFile(String fileName) {
    Collection<AddToDoPageTestData> testDataList = new ArrayList<AddToDoPageTestData>();
    String record;
    BufferedReader file;
    try {
      file = new BufferedReader(new FileReader(fileName));
      while ((record = file.readLine()) != null) {
        String[] fields = record.split(",");
        // TODO use
        // https://commons.apache.org/proper/commons-csv/user-guide.html
        ToDoInfo toDo = new ToDoInfo();
        toDo.titleText = fields[0];
        toDo.descriptionText = fields[1];
        toDo.priorityText = fields[2];
        toDo.dueDate = fields[3];;
        toDo.ownerText = fields[4];

        AddToDoPageTestData testData = new AddToDoPageTestData();
        testData.expectedResult = fields[5];
        testData.toDo = toDo;
        testDataList.add(testData);
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return testDataList;
  }

  public ToDoInfo getToDo() {
    return toDo;
  }

  public void setToDo(ToDoInfo toDo) {
    this.toDo = toDo;
  }

  public String getExpectedResult() {
    return expectedResult;
  }

  public void setExpectedResult(String expectedResult) {
    this.expectedResult = expectedResult;
  }


}
