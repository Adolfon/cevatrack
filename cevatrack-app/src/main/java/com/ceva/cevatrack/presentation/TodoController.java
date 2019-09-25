package com.ceva.cevatrack.presentation;

//
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Random;
// import java.util.ResourceBundle;
// import javax.annotation.PostConstruct;
// import javax.ejb.EJB;
// import javax.enterprise.context.SessionScoped;
// import javax.faces.application.FacesMessage;
// import javax.faces.component.UIComponent;
// import javax.faces.context.FacesContext;
// import javax.faces.model.SelectItem;
// import javax.faces.validator.ValidatorException;
// import javax.inject.Inject;
// import javax.inject.Named;
// import org.primefaces.event.FileUploadEvent;
// import org.primefaces.model.LazyDataModel;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import com.ceva.cjf.base.web.jsf.CJFBaseBean;
// import com.ceva.cjf.base.web.resource.LdapUserVO;
// import com.ceva.framework.ejb.service.property.ConfigProperty;
// import com.ceva.framework.jsf.model.BaseLazyDataModel;
// import com.ceva.framework.service.CommonServiceException;
// import com.ceva.framework.service.interceptor.Logged;
// import com.ceva.framework.service.interceptor.Timed;
// import com.ceva.framework.service.ldap.LDAPService;
// import com.ceva.framework.vo.SearchCriteriaVO;
//// import com.ceva.template.service.TodoService;
// import com.ceva.template.vo.FileUploaderVO;
// import com.ceva.template.vo.TodoVO;
//
/// **
// * TodoController.
// *
// * @author mohammad.rasheed
// *
// */
// @Named
// @SessionScoped
// public class TodoController extends CJFBaseBean {
// private static final Logger LOGGER = LoggerFactory.getLogger(TodoController.class);
// private static final long serialVersionUID = 1L;
// public static final String DEFAULT_SORT_COLUMN = "title";
//
// protected ResourceBundle msgs = ResourceBundle.getBundle("messages",
// FacesContext.getCurrentInstance().getViewRoot().getLocale());
//
// private static final String TO_DO_PAGE_REDIRECT = "list-todo?faces-redirect=true";
//
// private static final String TO_DO_EDIT_PAGE_REDIRECT = "add-todo?faces-redirect=true";
// private static final String ADD_TO_DO_PAGE = "add-todo";
//
// private static final String[] REQUIRED_ATTRIBUTES =
// {LDAPService.SAM_ACCCOUNT_NAME, LDAPService.DISPLAY_NAME, LDAPService.USER_PRINCIPAL_NAME};
//
// private static final String FILE_MAX_SIZE = "max.file.upload";
// private static final String CONFIRM_DIALOG_HEIGHT = "default.confirmDialog.height";
// private static final String CONFIRM_DIALOG_WIDTH = "default.confirmDialog.width";
// private TodoVO todo = new TodoVO();
// private LdapUserVO ldapuserVO;
//
// protected LazyDataModel<TodoVO> todoLazyDataModel;
//
// private String searchPrefix;
// private List<LdapUserVO> ldapUsers = new ArrayList<LdapUserVO>();
//
//
// private SearchCriteriaVO<TodoVO> searchCriteria;
//
// private List<FileUploaderVO> fileUploaderVOList = new ArrayList<>();
// private Map<String, InputStream> files = new HashMap<>();
// private FileUploaderVO fileUploaderVO;
// private static final String DOT_STRING = ".";
// private static final String UNDERSCORE_STRING = "_";
//
//// @EJB
//// TodoService todoService;
//
// @Inject
// LDAPService ldapService;
//
// @Inject
// @ConfigProperty(key = FILE_MAX_SIZE, defaultValue = "100000")
// private int maxFileUploadSize;
//
// /**
// * initilizes vo.
// */
// @PostConstruct
// public void init() {
// LOGGER.info("Info log");
// LOGGER.warn("warn log");
// LOGGER.debug("debug log");
// LOGGER.trace("trace log");
// LOGGER.error("error log");
// this.ldapuserVO = new LdapUserVO();
// this.todo = new TodoVO();
// this.todo.setLdapUser(new LdapUserVO());
// this.files = new HashMap<String, InputStream>();
// this.fileUploaderVOList = new ArrayList<>();
// this.initializeDataModel();
// }
//
// /**
// * initialize DataModel with custom VO and service object.
// */
// private void initializeDataModel() {
// searchCriteria = new SearchCriteriaVO<TodoVO>();
// searchCriteria.setFilter(todo);
//
// this.todoLazyDataModel =
// new BaseLazyDataModel(searchCriteria, todoService, DEFAULT_SORT_COLUMN);
// }
//
//
// /**
// * to select the user action.
// */
// public void selectUser() {
// LOGGER.debug("In Selection User Action of TodoController" + ldapuserVO.getPrincipalName());
// this.todo.setLdapUser(ldapuserVO);
// this.todo.setOwner(ldapuserVO.getDisplayName());
// }
//
//
// /**
// * This function return list of todos with search criteria.
// */
// public void searchTodos() {
// initializeDataModel();
// }
//
//
// /**
// * This function returns priorities.
// */
// public List<SelectItem> getPriorities() {
// List<SelectItem> list = new ArrayList<SelectItem>();
// list.add(new SelectItem(1, "High"));
// list.add(new SelectItem(2, "Medium"));
// list.add(new SelectItem(3, "Low"));
// return list;
// }
//
// /**
// * creates a todo.
// *
// * @return string
// */
// @Timed
// @Logged
// public String add() {
// try {
// todo.setFileUploaderVOList(fileUploaderVOList);
// todo.setFiles(files);
// this.todoService.createTodo(todo);
// todo = new TodoVO();
// return TO_DO_PAGE_REDIRECT;
// } catch (Exception e) {
// addErrorMessage(msgs.getString("todo.add.error"));
// LOGGER.error("Error in Save todo", e);
// return ADD_TO_DO_PAGE;
// } finally {
// files = new HashMap<String, InputStream>();
// fileUploaderVOList = new ArrayList<>();
// }
// }
//
// /** resets todo values. */
// public void resetAdd() {
// todo = new TodoVO();
// }
//
// /**
// * edits todo.
// */
//
// public String edit(TodoVO todo) {
// this.todo = todo;
// try {
// todoService.updateTodo(todo);
// addInfoMessage(msgs.getString("todo.update.success"));
// } catch (Exception e) {
// LOGGER.error("Error in Edit todo", e);
// addErrorMessage(msgs.getString("todo.update.error"));
// }
// this.todo = new TodoVO();
// return TO_DO_PAGE_REDIRECT;
// }
//
// /**
// * This function relates to list todos page.
// */
//
// public String editLstTodos(TodoVO todo) {
// this.todo = todo;
// return TO_DO_EDIT_PAGE_REDIRECT;
// }
//
// /**
// * To cancel edit.
// *
// * @return Redirects to list-todo page.
// */
// public String cancelEdit() {
// this.todo = new TodoVO();
// return TO_DO_PAGE_REDIRECT;
//
// }
//
// /**
// * save edit.
// *
// * @return Redirects to list-todo page.
// */
// public String saveEdit() {
//
// try {
// this.todoService.updateTodo(this.todo);
// } catch (Exception e) {
// LOGGER.error("Error in save Edit" + e.getLocalizedMessage(), e);
// }
// this.todo = new TodoVO();
// return TO_DO_PAGE_REDIRECT;
// }
//
// /**
// * delete.
// *
// * @param todo the todo object
// */
// public void delete(TodoVO todo) {
//
// try {
// this.todoService.deleteTodo(todo);
// addInfoMessage(msgs.getString("todo.delete.success"));
// } catch (Exception e) {
// LOGGER.error("Error in delete todo", e);
// addErrorMessage(msgs.getString("todo.delete.error"));
// }
//
// }
//
//
// /**
// * returns itemLabel from itemValue.
// *
// * @return label
// */
//
// public String selectedLabel(TodoVO to) {
// String lbl = null;
//
// for (SelectItem item : getPriorities()) {
// if (to != null && to.getPriority() == Integer.valueOf(item.getValue().toString())) {
// lbl = item.getLabel().toString();
// break;
// }
// }
//
// return lbl;
// }
//
// /**
// * get selected todo.
// *
// * @return todo
// */
// public TodoVO getTodo() {
// return this.todo;
// }
//
// /**
// * returns todolazydatamodel.
// *
// * @return lazyModel
// */
// public LazyDataModel<TodoVO> getTodoLazyDataModel() {
// return todoLazyDataModel;
// }
//
// /** assigns todolazydatamodel to a variable. */
// public void setTodoLazyDataModel(LazyDataModel<TodoVO> todoLazyDataModel) {
// this.todoLazyDataModel = todoLazyDataModel;
// }
//
//
// /**
// * Search User from LDAP by prefix.
// *
// */
// public void searchLdapUsers() throws CommonServiceException {
// setLdapUsers(transormListOfLdapUsersToUserVoList(
// ldapService.searchWild(getSearchPrefix(), REQUIRED_ATTRIBUTES)));
// }
//
// /**
// * Transform from List of listOFMapUsers to List of LDAPVO user.
// *
// * @param listOfMapUsers list of Map LDAP users
// * @return List of LDAPUserVO
// */
// private List<LdapUserVO> transormListOfLdapUsersToUserVoList(
// List<Map<String, String>> listOfMapUsers) {
// List<LdapUserVO> listOfldapUserVO = new ArrayList<LdapUserVO>();
// LdapUserVO ldapUserVO;
//
// for (Map<String, String> ldapUserMap : listOfMapUsers) {
// ldapUserVO = new LdapUserVO();
// ldapUserVO.setAccountName(ldapUserMap.get(LDAPService.SAM_ACCCOUNT_NAME));
// ldapUserVO.setDisplayName(ldapUserMap.get(LDAPService.DISPLAY_NAME));
// ldapUserVO.setPrincipalName(ldapUserMap.get(LDAPService.USER_PRINCIPAL_NAME));
//
// listOfldapUserVO.add(ldapUserVO);
// }
//
// return listOfldapUserVO;
// }
//
// /**
// * This Mehtod is used to Raise an exception for testing the submit error report using non ajax
// * call.
// *
// * @return String page to be redirected
// */
// public String addTodoNONAJAX() {
// LOGGER.debug("Info In Todo Controller NON ajax Call exception raiser");
// throw new RuntimeException("Custom Exception Created by NOn ajax Call");
// }
//
// /**
// * This Method is used to raise an exception to test submit error report using ajax call.
// */
// public void addTodoAJAX() {
// LOGGER.debug("Info In Todo Controller ajax Call exception raiser");
// throw new RuntimeException("Custom Exception Created by ajax Call");
// }
//
// /**
// * Rest dialog values because this backing bean is session scope.
// */
// public void restSearchLdapUserAction() {
// getLdapUsers().clear();
// setSearchPrefix(null);
//
// }
//
// public List<LdapUserVO> getLdapUsers() {
// return ldapUsers;
// }
//
//
// public void setLdapUsers(List<LdapUserVO> ldapUsers) {
// this.ldapUsers = ldapUsers;
// }
//
// public String getSearchPrefix() {
// return searchPrefix;
// }
//
// public void setSearchPrefix(String searchPrefix) {
// this.searchPrefix = searchPrefix;
// }
//
// public LdapUserVO getLdapuserVO() {
// return ldapuserVO;
// }
//
// public void setLdapuserVO(LdapUserVO ldapuserVO) {
// this.ldapuserVO = ldapuserVO;
// }
//
// /**
// * getter method.
// *
// * @return fileUploaderVOList
// */
// public List<FileUploaderVO> getFileUploaderVOList() {
// return fileUploaderVOList;
// }
//
// /**
// * This Method is used to set fileUploaderVOList.
// *
// * @param fileUploaderVOList list of vo objects
// */
// public void setFileUploaderVOList(List<FileUploaderVO> fileUploaderVOList) {
// this.fileUploaderVOList = fileUploaderVOList;
// }
//
// /**
// * This function set the file related information to VO to propagate to DB and. stores The
// * uploaded files to Map Object to copy to server location.
// */
// public void handleFileUpload(FileUploadEvent event) {
//
// String extension = null;
// String uploadFileName = event.getFile().getFileName();
// if (uploadFileName != null) {
// if (uploadFileName.lastIndexOf(DOT_STRING) != -1
// && uploadFileName.lastIndexOf(DOT_STRING) != 0) {
// extension = uploadFileName.substring(uploadFileName.lastIndexOf(DOT_STRING) + 1);
// }
// if (extension != null) {
// try {
// uploadFileName = uploadFileName.substring(0, uploadFileName.lastIndexOf(DOT_STRING));
// uploadFileName = uploadFileName.concat(UNDERSCORE_STRING)
// .concat(String.valueOf(new Random().nextInt(1000))).concat(DOT_STRING)
// .concat(extension);
// files.put(uploadFileName, event.getFile().getInputstream());
// fileUploaderVO = new FileUploaderVO();
// fileUploaderVO.setFileName(uploadFileName);
// fileUploaderVO.setContentType(extension);
// fileUploaderVOList.add(fileUploaderVO);
// addInfoMessage(msgs.getString("todo.addFile.success"));
// } catch (IOException e) {
// addErrorMessage(msgs.getString("todo.addFile.error"));
// LOGGER.error("Error in fileupload", e);
// }
// } else {
// addErrorMessage(msgs.getString("todo.uploadValid.error"));
// }
// } else {
// addErrorMessage(msgs.getString("todo.addFile.error"));
// }
// }
//
// /**
// * this Method is used to navigate to Add todo Page by resetting the default values.
// *
// * @return String page name to redirect
// */
// public String addTodo() {
// this.ldapuserVO = new LdapUserVO();
// this.todo = new TodoVO();
// this.todo.setLdapUser(new LdapUserVO());
// this.files = new HashMap<String, InputStream>();
// this.fileUploaderVOList = new ArrayList<>();
// return TO_DO_EDIT_PAGE_REDIRECT;
// }
//
// public int getMaxFileUploadSize() {
// return maxFileUploadSize;
// }
//
// public void setMaxFileUploadSize(int maxFileUploadSize) {
// this.maxFileUploadSize = maxFileUploadSize;
// }
//
// /**
// * this Method is used to validate due date format.
// *
// */
// public void validateDate(FacesContext context, UIComponent component, Date dueDate)
// throws ValidatorException {
// if (dueDate != null) {
// if (!(dueDate.getYear() + 1900 > 999)) {
// throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
// "Please Enter Valid Date Format MM/DD/YYYY", null));
// }
// }
// }
//
// }
