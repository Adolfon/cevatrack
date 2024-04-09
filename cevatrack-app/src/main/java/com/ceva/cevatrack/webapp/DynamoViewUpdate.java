package com.ceva.cevatrack.webapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
// import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.ceva.cevatrack.vo.DynamoBuildingRegisterTable;
import com.ceva.cevatrack.vo.DynamoFireRegistryTestTable;
import com.ceva.cevatrack.wsmicrolise.WsMicroliseEnvironment;


// @SuppressWarnings("serial")
@ManagedBean(name = "dynamoViewUpdate")
@ViewScoped
public class DynamoViewUpdate {

  private static final Logger log = LoggerFactory.getLogger(DynamoViewUpdate.class);
  private static DynamoDB dynamoDB;
  // private static final String FIRE_TABLE = "BuildingRegister";
  private static final String FIRE_TABLE = "BuildingRegister";
  // private Integer lastID;


  @ManagedProperty(value = "#{tableFire}")
  private List<DynamoBuildingRegisterTable> tableFire;
  private List<DynamoBuildingRegisterTable> tableFireFilter;
  // dummyTable(tableFire);
  // this.dummyTable(tableFire);

  private Map<String, String> params;
  private String defaultLocation;
  private String defaultPoint;


  public String getDefaultPoint() {
    return defaultPoint;
  }


  public void setDefaultPoint(String defaultPoint) {
    this.defaultPoint = defaultPoint;
  }


  public String getDefaultLocation() {
    return defaultLocation;
  }


  public void setDefaultLocation(String defaultLocation) {
    this.defaultLocation = defaultLocation;
  }


  public Map<String, String> getParams() {
    return params;
  }


  public void setParams(Map<String, String> params) {
    this.params = params;
  }


  public List<DynamoBuildingRegisterTable> getTableFireFilter() {
    return tableFireFilter;
  }


  public void setTableFireFilter(List<DynamoBuildingRegisterTable> tableFireFilter) {
    this.tableFireFilter = tableFireFilter;
  }



  private List<String> fireAssemblyElements; // Different Assemby elements to filter with

  public List<String> getFireAssemblyElements() {
    return fireAssemblyElements;
  }


  public void setFireAssemblyElements(List<String> fireAssemblyElements) {
    this.fireAssemblyElements = fireAssemblyElements;
  }


  private List<String> cevaLocations;


  public List<String> getCevaLocations() {
    return cevaLocations;
  }


  public void setCevaLocations(List<String> cevaLocations) {
    this.cevaLocations = cevaLocations;
  }



  private String option = "ChkIN";
  private String inputText = "";

  public String getInputText() {
    return inputText;
  }


  public void setInputText(String inputText) {
    this.inputText = inputText;
  }


  public String getOption() {
    return option;
  }


  public void setOption(String option) {
    this.option = option;
  }


  public List<DynamoBuildingRegisterTable> getTableFire() {
    return tableFire;
  }


  public void setTableFire(List<DynamoBuildingRegisterTable> tableFire) {
    this.tableFire = tableFire;
  }


  @PostConstruct
  public void init() {
    tableFire = new ArrayList<>();
    makeConnectionDynamo();
    // dummyTable(tableFire);
    // log.info(" -- Dummy table created --");
    fillOutputTable();
    // tableFireFilter = new ArrayList<>();
    // this.tableFireFilter.addAll(tableFire);
    log.info(" ----- End of initialization ------");
    // Table table = dynamoDB.getTable(FIRE_TABLE);
    // if (addUserToTable("12345678",table)){
    // addUserToTable("12345678",table); // Test if I can add it twice
    // fillOutputTable();
    // removeUserFromTable("12345678", table);
    // };
    // removeUserFromTable("12345678", table); // Test if I can remove it twice
  }



  private void makeConnectionDynamo() {

    // WsMicroliseEnvironment env = new WsMicroliseEnvironment();

    Map<String, String> parameters = null;

    // parameters = env.loadCevaResponsiveXmlProps(); //The environment should provide this once its
    // in the cloud

    if (parameters == null) {
      log.info(" -- there is no properties file so we apply the cloud context");
      parameters = FacesContext.getCurrentInstance().getExternalContext().getInitParameterMap();
      log.info(" -- Cevatrack parameters: " + parameters);
      // log.info(" -- Parameter proxy4.amazon.host: " + parameters.get("proxy4.amazon.host"));
    }

    // AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder
    // .standard()
    // .withRegion(Regions.EU_WEST_1)
    // .withCredentials(new DefaultAWSCredentialsProviderChain())
    // .build();

    // dynamoDB = new DynamoDB(dynamoDBClient); //it works out of Ceva network


    ClientConfiguration clientCfg = new ClientConfiguration();
    clientCfg.withProxyHost("10.245.12.19").setProxyPort(3128);
    clientCfg.withProxyUsername("ES-SVR-TR-USER").withProxyPassword("rFMc7kyW");
    // String proxy = (String) parameters.get("proxy4.amazon.host").trim();
    // int port = Integer.valueOf((String) parameters.get("proxy4.amazon.port").trim());
    // String user = (String) parameters.get("proxy4.amazon.user").trim();
    // String pass = (String) parameters.get("proxy4.amazon.psw").trim();
    //
    // log.info(" -- Environment parameters recovered proxy: " + proxy + ", port: " + port + ",
    // user: " + user + ", p: " + pass);

    // // It is not working even though the properties are correct
    // ClientConfiguration clientCfgProperties = new ClientConfiguration();
    // clientCfg.withProxyHost(proxy.trim()).setProxyPort(port);
    // clientCfg.withProxyUsername(user).withProxyPassword(pass);

    AmazonDynamoDB dynamoDBClientProxy = AmazonDynamoDBClientBuilder.standard()
        .withCredentials(new DefaultAWSCredentialsProviderChain()).withRegion(Regions.EU_WEST_1)
        .withClientConfiguration(clientCfg).build();

    dynamoDB = new DynamoDB(dynamoDBClientProxy);

  }

  private void fillOutputTable() {
    // Java System Properties - aws.accessKeyId and aws.secretKey
    log.info(" -- Recovering the Dynamotable for output --");
    // this.lastID = 0;
    System.getProperties().setProperty("aws.accessKeyId", "AKIASUBMQ5H6LWCHEZUG");
    System.getProperties().setProperty("aws.secretKey", "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");

    log.info(" -- System properties: " + System.getProperties());

    Table table = dynamoDB.getTable(FIRE_TABLE);

    HashSet<String> fireAssemblyPoint = new HashSet<>();
    fireAssemblyElements = new ArrayList();
    cevaLocations = new ArrayList();

    log.info("-- Recovered table: " + table.getTableName());

    try {
      this.tableFire.clear();
      fireAssemblyPoint.clear();
      fireAssemblyElements.clear();
      cevaLocations.clear();
      Iterator<Item> iterator = scanAllElements(table);
      log.info(" -- All the elements in the table are: ");

      DynamoBuildingRegisterTable element = null;
      while (iterator.hasNext()) {
        Item item = iterator.next();
        element = new DynamoBuildingRegisterTable();
        element.setId(item.getString("ID"));
        log.info(" -- Element Id: " + item.getString("ID"));

        element.setId(item.getString("ID"));
        element.setCevaLocation(item.getString("CEVALocation"));
        element.setEntryDate(item.getString("EntryDate"));
        element.setEntryTime(item.getString("EntryTime"));
        element.setExitDate(item.getString("ExitDate"));
        element.setExitTime(item.getString("ExitTime"));
        element.setFireAssemblyPoint(item.getString("FireAssemblyPoint") == null ? "no value"
            : item.getString("FireAssemblyPoint"));
        log.info(" Assembly point: " + item.getString("FireAssemblyPoint"));

        try {
          element.setIsAppointment(item.getBoolean("IsAppointment"));
        } catch (Exception e) {
          log.info(" -- IsAppointment is null: " + e.getMessage());
          // element.setIsAppointment(false); //default value
        }
        element.setEmpFirstName(item.getString("empFirstName"));
        element.setEmpLastTime(item.getString("empLastName"));
        element.setCevaDeviceId(item.getString("CEVADeviceID"));
        element.setFireGroup(item.getString("FireGroup"));

        log.info(" row information: \n" + element);
        if (!fireAssemblyPoint.contains(
            element.getFireAssemblyPoint() == null ? "no value" : element.getFireAssemblyPoint())) {
          fireAssemblyPoint.add(
              element.getFireAssemblyPoint() == null ? "no value" : element.getFireAssemblyPoint());
          log.info(" --adding element to the selection: " + element.getFireAssemblyPoint());
          fireAssemblyElements.add(
              element.getFireAssemblyPoint() == null ? "no value" : element.getFireAssemblyPoint());
        }
        if (!fireAssemblyPoint
            .contains(element.getCevaLocation() == null ? "no value" : element.getCevaLocation())) {
          fireAssemblyPoint
              .add(element.getCevaLocation() == null ? "no value" : element.getCevaLocation());
          log.info(
              " --adding element to the Ceva Location box filter: " + element.getCevaLocation());
          cevaLocations
              .add(element.getCevaLocation() == null ? "no value" : element.getCevaLocation());
        }
        // Only people who did not check out. // is empty but it is now working...
        if ((element.getExitDate() == null || element.getExitDate().trim().equals(""))
            && (element.getExitTime() == null || element.getExitTime().trim().equals(""))) {
          this.tableFire.add(element);
        }
      }
      this.params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      if (params.containsKey("FireAssemblyPoint")) {
        log.info("FireAssembyPoint parameter: " + params.get("FireAssemblyPoint"));
        if (params.get("FireAssemblyPoint") == null
            || !fireAssemblyElements.contains(params.get("FireAssemblyPoint"))) {
          // FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("FireAssemblyPoint",
          // "");
          this.defaultPoint = "";
        } else {
          this.defaultPoint = params.get("FireAssemblyPoint");
        }
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('fireTable').filter();");
      }
      if (params.containsKey("CEVALocation")) {
        log.info("CEVALocation parameter: " + params.get("CEVALocation"));
        if (params.get("CEVALocation") == null
            || !cevaLocations.contains(params.get("CEVALocation"))) {
          // FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("CEVALocation",
          // "");
          this.defaultLocation = "";
        } else {
          this.defaultLocation = params.get("CEVALocation");
        }
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('fireTable').filter();");
      }

    } catch (Exception e) {
      log.error(" -- Error while I was scanning the dynamoDB table .. " + e);
    }

    // fireAssemblyElements.addAll(fireAssemblyPoint); //For the dropdown box filter
  }

  private Integer getIDKey(Table table) {
    Integer id = 0;
    try {
      Iterator<Item> iterator = scanAllElements(table);
      while (iterator.hasNext()) {
        Item item = iterator.next();
        if (Integer.parseInt(item.getString("ID")) > id) {
          id = Integer.parseInt(item.getString("ID"));
          log.info(" -- id becomes: " + id);
        }
      }
    } catch (Exception e) {
      log.error(" -- Error while getting the last ID " + e);
    }
    return id + 1;
  }

  private Iterator<Item> scanAllElements(Table table) throws Exception {

    try {
      Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
      expressionAttributeValues.put(":pr", "0");

      ItemCollection<ScanOutcome> items = table.scan("ID >= :pr", // FilterExpression
          "ID, CEVALocation ,EntryDate, EntryTime, ExitDate, ExitTime, FireAssemblyPoint, IsAppointment, empFirstName, empLastName, CEVADeviceID, FireGroup ", // ProjectionExpression,
                                                                                                                                                               // Mapped
                                                                                                                                                               // result
                                                                                                                                                               // columns
          null, // No ExpressionAttributeNames
          expressionAttributeValues);
      return items.iterator();

    } catch (Exception e) {
      log.error(" -- There was an error in the query: " + e);
    }
    return null;
  }

  private Iterator<Item> scanEmployeeID(String empId, Table table) throws Exception {

    try {
      Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
      expressionAttributeValues.put(":pr", empId);

      ItemCollection<ScanOutcome> items = table.scan("EmployeeID = :pr", // FilterExpression
          "ID, EntryTime, EmployeeID", // ProjectionExpression, Mapped result columns
          null, // No ExpressionAttributeNames
          expressionAttributeValues);
      return items.iterator();

    } catch (Exception e) {
      log.error(" -- There was an error in the query: " + e);
    }
    return null;
  }


  private Boolean addUserToTable(String employeeId, Table table) {
    // if the user is not found
    if (returnEmployeeId(employeeId, table).equals("000")) {
      try {
        Integer idKey = getIDKey(table);
        GregorianCalendar gcal = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setCalendar(gcal);
        String timeFormatted = sdf.format(gcal.getTime());
        log.info(" -- Time formatted: " + timeFormatted);
        log.info(" -- New ID: " + idKey);
        // Add user
        // Integer IDfield = lastID + 1;
        log.info(" -- ID converted to String: " + String.format("%03d", idKey).trim());
        Item item = new Item().withPrimaryKey("ID", String.format("%03d", idKey).trim())
            .withString("EntryTime", timeFormatted.trim())
            .withString("EmployeeID", employeeId.trim());
        table.putItem(item);
        log.info(" -- the user: " + employeeId + ", was successfully added ");
        return true;
      } catch (Exception e) {
        log.error(" -- Error adding the userId: " + employeeId + ", to the table: "
            + table.getTableName());
        return false;
      }
    } else {
      return false;
    }
  }

  private String returnEmployeeId(String employeeId, Table table) {
    log.info(" -- Returning table Id for EmployeeID: " + employeeId);
    String id = "000";
    try {
      Iterator<Item> iterator = scanEmployeeID(employeeId, table);

      while (iterator.hasNext()) {
        Item item = iterator.next();
        id = item.getString("ID");
      }
    } catch (Exception e) {
      log.error(" -- it was not possible to return the Id for employeeId: " + employeeId);
      return "000";
    }
    return id;
  }

  // Refactor this method and next if I have time..
  private String returnTimeForEmployeeId(String employeeId, Table table) {
    log.info(" -- Recovering time for EmployeeID: " + employeeId);
    String time = "000";
    try {
      Iterator<Item> iterator = scanEmployeeID(employeeId, table);

      while (iterator.hasNext()) {
        Item item = iterator.next();
        time = item.getString("EntryTime");
      }
    } catch (Exception e) {
      log.error(" -- it was not possible to return the Id for employeeId: " + employeeId);
      return "000";
    }
    return time;
  }

  private Boolean removeUserFromTable(String employeeId, Table table) {
    log.info(" -- Removing EmployeeID: " + employeeId);
    String id = "000", time = "000";
    try {
      id = returnEmployeeId(employeeId, table);
      time = returnTimeForEmployeeId(employeeId, table);

      if (id.equals("000") || time.equals("000")) {
        log.error(" -- The employeeID: " + employeeId + " was not found ");
        return false;
      }
      log.info(" -- The EmployeeID: " + employeeId + ", has as a table ID: " + id + ", and time: "
          + time);
    } catch (Exception e) {
      log.error(" -- Error while I was scanning the dynamoDB table .. " + e);
    }


    // I think the table was created with 2 PKs -->
    // Scan: [Table] FireRegistryTest: ID, EntryTime ( I saw this in the Amazon interface)
    DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
        // .withPrimaryKey(new PrimaryKey("ID", id.trim(), "EmployeeID", employeeId));
        .withPrimaryKey(new PrimaryKey("ID", id.trim(), "EntryTime", time.trim())); // It worked!!
    // .withConditionExpression("EmployeeID = :val")
    // .withValueMap(new ValueMap().withString(":val", employeeId));

    try {
      table.deleteItem(deleteItemSpec);
    } catch (Exception e) {
      log.error(" --It was not possible to erase Employee: " + employeeId + ", with ID: " + id);
    }
    log.info(" -- Successfully erased Employee: " + employeeId + ", with ID: " + id);
    return true;
  }

  private void dummyTable(List<DynamoFireRegistryTestTable> table) {
    DynamoFireRegistryTestTable ele = new DynamoFireRegistryTestTable();
    ele.setId("7");
    ele.setEntryTime("00:01:01");
    ele.setEmployeeId("71");
    table.add(ele);
    ele = new DynamoFireRegistryTestTable();
    ele.setId("8");
    ele.setEntryTime("00:01:02");
    ele.setEmployeeId("715");
    table.add(ele);
  }


  public void buttonAction() {

    // String msg = "";

    if (this.option.equals("ChkIN") && !this.inputText.isEmpty()) {
      Table table = dynamoDB.getTable(FIRE_TABLE);

      Long entryNumber = Long.parseLong(this.inputText);
      if (entryNumber > Integer.MAX_VALUE) {
        addMessage(" Your employee id is too big ", FacesMessage.SEVERITY_ERROR);
        this.inputText = "";
        return;
      }
      if (addUserToTable(this.inputText, table)) {
        addMessage(" The employee id: " + this.inputText + " checked in successfully",
            FacesMessage.SEVERITY_INFO);
        fillOutputTable();
        this.inputText = "";
      } else {
        addMessage(
            " The employee id: " + this.inputText
                + " it is already checked in or there is a problem ..",
            FacesMessage.SEVERITY_ERROR);
      }
    }

    if (this.option.equals("ChkOUT") && !this.inputText.isEmpty()) {
      Table table = dynamoDB.getTable(FIRE_TABLE);
      Long entryNumber = Long.parseLong(this.inputText);
      if (entryNumber > Integer.MAX_VALUE) {
        addMessage(" Your employee id is too big ", FacesMessage.SEVERITY_ERROR);
        this.inputText = "";
        return;
      }

      if (removeUserFromTable(this.inputText, table)) {
        addMessage(" The employee id: " + this.inputText + " checked out successfully",
            FacesMessage.SEVERITY_INFO);
        fillOutputTable();
        this.inputText = "";
      } else {
        addMessage(
            " The employee id: " + this.inputText + " already checked out or there is a problem ..",
            FacesMessage.SEVERITY_ERROR);
      }
    }

    // addMessage("Selected action: " + this.option + ", inputBox: " + this.inputText);

  }



  public void addMessage(String summary, Severity severity) {
    // FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
    FacesMessage message = new FacesMessage(severity, summary, null);
    FacesContext.getCurrentInstance().addMessage(null, message);
    // fillOutputTable();
  }

}


