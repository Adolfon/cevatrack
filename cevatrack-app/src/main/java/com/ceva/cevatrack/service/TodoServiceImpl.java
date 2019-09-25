package com.ceva.template.service;

//
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.OutputStream;
// import java.net.URI;
// import java.util.List;
// import java.util.Map;
// import java.util.Map.Entry;
// import javax.annotation.Resource;
// import javax.ejb.SessionContext;
// import javax.ejb.Stateless;
// import javax.enterprise.event.Event;
// import javax.inject.Inject;
// import org.apache.poi.hssf.usermodel.HSSFWorkbook;
// import org.apache.poi.ss.usermodel.Cell;
// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.ss.usermodel.Sheet;
// import org.apache.poi.ss.usermodel.Workbook;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import com.ceva.framework.ejb.service.property.ConfigProperty;
// import com.ceva.framework.vo.PaginatedResultVO;
// import com.ceva.framework.vo.SearchCriteriaVO;
// import com.ceva.template.domain.FileUploader;
// import com.ceva.template.domain.Todo;
// import com.ceva.template.event.Add;
// import com.ceva.template.event.Update;
// import com.ceva.template.exception.NoSuchObjectException;
// import com.ceva.template.exception.NullObjectException;
// import com.ceva.template.repository.FileUploaderRepository;
// import com.ceva.template.repository.TodoRepository;
// import com.ceva.template.transformer.FileUploaderTransformer;
// import com.ceva.template.transformer.TodoTransformer;
// import com.ceva.template.vo.FileUploaderVO;
// import com.ceva.template.vo.TodoVO;
//
/// **
// * @author mohammad.rasheed
// */
//
/// **
// * TodoServiceImpl.
// *
// * @author viswanadha.gurijala
// *
// */
// @Stateless
// public class TodoServiceImpl implements TodoService {
//
// private static final Logger LOGGER = LoggerFactory.getLogger(TodoServiceImpl.class);
// private static final String FILE_DEFAULT_LOCATION = "file.path.location";
//
// @Inject
// private TodoRepository todoRepository;
//
// @Inject
// TodoTransformer todoTransformer;
//
// @Inject
// private FileUploaderRepository fileUploaderRepository;
//
// @Inject
// FileUploaderTransformer fileUploaderTransformer;
//
// @Resource
// SessionContext sessionContext;
//
// @Inject
// @ConfigProperty(key = FILE_DEFAULT_LOCATION, defaultValue = "")
// private String filesPath;
// private static final String DOT_STRING = ".";
// private static final String UNDERSCORE_STRING = "_";
// private static final String ID_CANNOT_BE_NULL = "ID should not be null";
// private static final String TODO_ENTITY_NOT_FOUND = "Todo entity not found with the ID: ";
//
// @Inject
// @Add
// Event<TodoVO> addTodoEvent;
//
// @Inject
// @Update
// Event<TodoVO> updateTodoEvent;
//
// /**
// * createTodo.
// *
// * @see com.ceva.template.service.TodoService#createTodo(com.ceva.template.vo.TodoVO) inserts
// * TodoVO
// * @param vo TodoVO
// * @return TodoVO
// */
// public TodoVO createTodo(TodoVO vo) throws Exception {
// if (vo == null) {
// throw new NullObjectException("NULL object will not be saved.");
// }
// try {
// Todo todoEntity = todoTransformer.transformToTodo(vo);
// todoTransformer.prepareForCreate(todoEntity);
// Todo todo = todoRepository.save(todoEntity);
// TodoVO todoVO = todoTransformer.transformToVO(todo);
// Map<String, InputStream> uploadedFiles = vo.getFiles();
// processUploadedFiles(todo, uploadedFiles);
// if (vo.getFileUploaderVOList() != null) {
// for (FileUploaderVO fileUploaderVO : vo.getFileUploaderVOList()) {
// saveUploadFiles(todoVO, fileUploaderVO);
// }
// }
// addTodoEvent.fire(todoVO);
// return todoVO;
// } catch (Exception e) {
// sessionContext.setRollbackOnly();
// throw e;
// }
// }
//
// private void saveUploadFiles(TodoVO todoVO, FileUploaderVO fileUploaderVO) {
// if (fileUploaderVO != null) {
// fileUploaderVO.setToDoId(todoVO.getId());
// fileUploaderVO.setFileName(
// todoVO.getId().toString().concat(UNDERSCORE_STRING).concat(fileUploaderVO.getFileName()));
// FileUploader fileUploader = fileUploaderTransformer.transformToFileUploader(fileUploaderVO);
// if (fileUploader != null) {
// fileUploaderTransformer.prepareForCreate(fileUploader);
// fileUploaderRepository.save(fileUploader);
// }
// }
// }
//
// /**
// * process uploaded files(copy files to specific the location and if upload file is excel reading
// * excel file and writing the first 2 lines to Log file).
// */
// private void processUploadedFiles(Todo todo, Map<String, InputStream> uploadedFiles)
// throws Exception {
// if (uploadedFiles != null && !uploadedFiles.isEmpty() && uploadedFiles.size() > 0) {
// uploadFiles(uploadedFiles, todo.getId(), filesPath);
// }
// }
//
// /**
// * updateTodo.
// *
// * @see com.ceva.template.service.TodoService#updateTodo(com.ceva.template.vo.TodoVO)
// */
// public TodoVO updateTodo(TodoVO vo) throws Exception {
//
// if (vo == null) {
// LOGGER.error("Update not allowed on NULL object.");
// throw new NullObjectException("Update not allowed on NULL object.");
// }
//
// if (vo.getId() == null) {
// LOGGER.error(ID_CANNOT_BE_NULL);
// throw new IllegalArgumentException(ID_CANNOT_BE_NULL);
// }
//
// Todo objToUpdate = todoRepository.findBy(vo.getId());
// if (objToUpdate == null) {
// LOGGER.error(TODO_ENTITY_NOT_FOUND + vo.getId());
// throw new NoSuchObjectException(TODO_ENTITY_NOT_FOUND + vo.getId());
// }
//
// todoTransformer.convertToTodo(vo, objToUpdate);
// todoTransformer.prepareForUpdate(objToUpdate);
// todoRepository.save(objToUpdate);
// updateTodoEvent.fire(vo);
// return todoTransformer.transformToVO(objToUpdate);
// }
//
// /**
// * Fetch todo given its ID.
// *
// * @param id todo PK
// * @return todo
// */
// public TodoVO getTodo(final Long id) {
// Todo todo = todoRepository.findBy(id);
// return todoTransformer.transformToVO(todo);
// }
//
// /**
// * (non-Javadoc).
// *
// * @see com.ceva.template.service.TodoService#deleteTodo(com.ceva.template.vo.TodoVO)
// */
// public boolean deleteTodo(TodoVO dto) throws Exception {
// if (dto == null) {
// throw new NullObjectException("NULL object.");
// }
//
// if (dto.getId() == null) {
// throw new IllegalArgumentException(ID_CANNOT_BE_NULL);
// }
//
// Todo objToDelete = todoRepository.findBy(dto.getId());
// if (objToDelete == null) {
// throw new NoSuchObjectException(TODO_ENTITY_NOT_FOUND + dto.getId());
// }
//
// todoRepository.remove(objToDelete);
// return true;
// }
//
// /** deletes todo from datastore. */
// @Override
// public boolean deleteTodo(Long id) throws Exception {
// if (id == null) {
// throw new IllegalArgumentException(ID_CANNOT_BE_NULL);
// }
//
// Todo objToDelete = todoRepository.findBy(id);
// if (objToDelete == null) {
// throw new NoSuchObjectException(TODO_ENTITY_NOT_FOUND + id);
// }
// todoRepository.remove(objToDelete);
// return true;
// }
//
// /** gets todo repository. */
// public TodoRepository getTodoRepository() {
// return todoRepository;
// }
//
// /** sets todo repository. */
// public void setTodoRepository(TodoRepository todoRepository) {
// this.todoRepository = todoRepository;
// }
//
// /** gets todo transformer. */
// public TodoTransformer getTodoTransformer() {
// return todoTransformer;
// }
//
// /** sets todo transformer. */
// public void setTodoTransformer(TodoTransformer todoTransformer) {
// this.todoTransformer = todoTransformer;
// }
//
// /*
// *
// * @see com.ceva.template.service.TodoService#findAllTodos() returns all todos from data store
// */
// public List<TodoVO> findAllTodos() {
// List<Todo> todoList = todoRepository.findAll();
// return todoTransformer.transformToVO(todoList);
// }
//
// /*
// * (non-Javadoc)
// *
// * @see com.ceva.template.service.TodoService#searchTodosWithPagination(com.ceva. security.aaf.vo.
// * SearchCriteriaVO)
// */
// @Override
// public PaginatedResultVO<TodoVO> searchWithPagination(SearchCriteriaVO<TodoVO> criteria) {
// PaginatedResultVO<Todo> result = todoRepository.searchTodosWithPagination(criteria);
// List<TodoVO> data = todoTransformer.transformToVO(result.getData());
//
// return new PaginatedResultVO<TodoVO>(data, result.getCount());
//
// }
//
// public FileUploaderRepository getFileUploaderRepository() {
// return fileUploaderRepository;
// }
//
// public void setFileUploaderRepository(FileUploaderRepository fileUploaderRepository) {
// this.fileUploaderRepository = fileUploaderRepository;
// }
//
// public FileUploaderTransformer getFileUploaderTransformer() {
// return fileUploaderTransformer;
// }
//
// public void setFileUploaderTransformer(FileUploaderTransformer fileUploaderTransformer) {
// this.fileUploaderTransformer = fileUploaderTransformer;
// }
//
// /**
// * This function upload files to specific location the location is defined - in the properties
// * file, which is configurable.
// *
// * @throws Exception Exception Thrown
// */
// public void uploadFiles(Map<String, InputStream> uploadedFiles, Long id, String filesPath)
// throws Exception {
//
// try {
// String serverPath = System.getProperty("catalina.base").concat(filesPath);
// String uploadFolderPath = "file://".concat(serverPath);
// File serveruploadFolder = new File(new URI(uploadFolderPath));
// if (!serveruploadFolder.exists()) {
// serveruploadFolder.mkdirs();
// }
// for (Entry<String, InputStream> entry : uploadedFiles.entrySet()) {
// String fileName = entry.getKey();
// OutputStream outputStream = new FileOutputStream(
// new File(serverPath.concat(id + UNDERSCORE_STRING).concat(entry.getKey())));
// int read = 0;
// byte[] bytes = new byte[1024];
//
// while ((read = entry.getValue().read(bytes)) != -1) {
// outputStream.write(bytes, 0, read);
// }
// outputStream.close();
//
// checkFileType(id, serverPath, entry, fileName);
// }
// } catch (Exception e) {
// LOGGER.error("Error in uploadFiles todo", e);
// throw e;
// }
// }
//
// private void checkFileType(Long id, String serverPath, Entry<String, InputStream> entry,
// String fileName) throws Exception {
// if (fileName.lastIndexOf(DOT_STRING) != -1 && fileName.lastIndexOf(DOT_STRING) != 0
// && ("xlsx".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(DOT_STRING) + 1))
// || "xls".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(DOT_STRING) + 1)))) {
// readExcelData(id, serverPath, entry, fileName);
// }
// }
//
// /**
// * This function handles the task of reading excel file and writing the first 2 lines to Log file.
// *
// * @throws Exception Exception Thrown
// */
// private void readExcelData(Long id, String serverPath, Entry<String, InputStream> entry,
// String fileName) throws Exception {
// try {
// LOGGER.info("*****Excel File Info*****");
// FileInputStream inputStream = new FileInputStream(
// new File(serverPath.concat(id + UNDERSCORE_STRING).concat(entry.getKey())));
// Workbook workbook = null;
// if ("xlsx".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(DOT_STRING) + 1))) {
// workbook = new XSSFWorkbook(inputStream);
// } else if ("xls".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(DOT_STRING) + 1))) {
// workbook = new HSSFWorkbook(inputStream);
// }
// if (workbook != null) {
// Sheet sheet = workbook.getSheetAt(0);
// if (sheet != null) {
// printExcelData(workbook, sheet);
// }
// }
// inputStream.close();
// } catch (Exception e) {
// LOGGER.error("Error in reading excel data", e);
// throw e;
// }
// }
//
// private void printExcelData(Workbook workbook, Sheet sheet) throws IOException {
// for (int rowNum = 0; rowNum < 2; rowNum++) {
// if (sheet.getRow(rowNum) != null) {
// LOGGER.info(readExcelCellData(sheet, rowNum));
// }
// }
// workbook.close();
// }
//
// private String readExcelCellData(Sheet sheet, int rowNum) {
// Row row = sheet.getRow(rowNum);
// String excelData = "";
// for (int cellNum = 0; cellNum < 13; cellNum++) {
// if (row.getCell(cellNum) != null) {
// Cell cell = row.getCell(cellNum);
// excelData = excelData.concat(cell.toString()).concat("\t");
// }
// }
// return excelData;
// }
// }
