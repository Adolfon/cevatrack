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
 * Read the CSV file for EditToDoPage.
 */
public class EditToDoPageTestData {

  DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
  LocalDate date = LocalDate.now();

  private ToDoInfo toDo;
  private ToDoInfo editToDo;
  private String expectedResult;

  // TODO write the CSV format
  // title,desc,priority,duedate,owner,newTitle,newDec,newPriority,newDueDate,newOwner,result
  // test,test,1,12/12/12,test,changeTest,test,1,12/12/12,test,success
  // test,test,1,12/12/12,test,changeTest,test,a,12/12/12,test,validation_failure_priority
  // test,test,1,12/12/12,test,changeTest,test,,12/12/12,test,validation_failure_priority
  // test,test,1,12/12/12,test,changeTest,test,?,12/12/12,test,validation_failure_priority
  // test,test,1,12/12/12,test,changeTest,test,1.1,12/12/12,test,validation_failure_priority
  // test,test,1,12/12/12,,changeTest,test,a,12/12/12,test,validation_failure_owner
  /**
   * Read the CSV File.
   * 
   * @return testDataList TestData
   */
  public static Collection<EditToDoPageTestData> readTestDataFromFile(String fileName) {
    Collection<EditToDoPageTestData> testDataList = new ArrayList<EditToDoPageTestData>();
    String record;
    BufferedReader file;
    try {
      file = new BufferedReader(new FileReader(fileName));
      while ((record = file.readLine()) != null) {
        String[] fields = record.split(",");

        ToDoInfo toDo = new ToDoInfo();
        toDo.titleText = fields[0];
        toDo.descriptionText = fields[1];
        toDo.priorityText = fields[2];
        toDo.dueDate = fields[3];;
        toDo.ownerText = fields[4];

        ToDoInfo editToDo = new ToDoInfo();
        editToDo.titleText = fields[5];
        editToDo.descriptionText = fields[6];
        editToDo.priorityText = fields[7];
        editToDo.dueDate = fields[8];
        editToDo.ownerText = fields[9];

        EditToDoPageTestData testData = new EditToDoPageTestData();
        testData.expectedResult = fields[10];
        testData.toDo = toDo;
        testData.editToDo = editToDo;
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

  public ToDoInfo getEditToDo() {
    return editToDo;
  }

  public void setEditToDo(ToDoInfo editToDo) {
    this.toDo = editToDo;
  }

  public String getExpectedResult() {
    return expectedResult;
  }

  public void setExpectedResult(String expectedResult) {
    this.expectedResult = expectedResult;
  }


}
