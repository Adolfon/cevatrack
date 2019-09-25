package com.ceva.cevatrack.rest.v1.impl;

//
// import java.io.ByteArrayInputStream;
// import java.io.InputStream;
// import java.util.Base64;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import javax.inject.Inject;
// import javax.ws.rs.core.Response;
// import javax.ws.rs.core.Response.Status;
// import javax.ws.rs.core.UriInfo;
// import org.apache.commons.codec.net.URLCodec;
// import org.apache.commons.collections4.MapUtils;
// import org.apache.commons.lang3.StringUtils;
// import org.apache.cxf.jaxrs.ext.search.SearchCondition;
// import org.apache.cxf.jaxrs.ext.search.fiql.FiqlParser;
// import org.apache.shiro.SecurityUtils;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import com.ceva.framework.rest.exception.RestServiceException;
// import com.ceva.framework.rest.exception.RestServiceExceptionDetails;
// import com.ceva.template.exception.NoSuchObjectException;
// import com.ceva.template.exception.NullObjectException;
// import com.ceva.template.rest.v1.TodoRestService;
// import com.ceva.template.service.TodoService;
// import com.ceva.template.util.CJFPermissionConstants;
// import com.ceva.template.vo.TodoVO;
//
/// **
// * Todo REST resource.
// */
// public class TodoRestServiceImpl implements TodoRestService {
//
// private static final Logger LOG = LoggerFactory.getLogger(TodoRestServiceImpl.class);
//
// /** The max file size value per file expressed in kilobytes 5000kb = 5mb. */
// private static final double MAX_SIZE_PER_FILE = 5000;
//
// /** The max file size value collective files expressed in kilobytes 10000kb = 10mb. */
// private static final double MAX_SIZE_COLLECTIVE_FILE = 10000;
//
// @Inject
// private TodoService todoService;
//
// private static final String KBYTES = " KB.";
//
// /**
// * Get all todos.
// *
// * @return todos
// */
// // TODO will remove Exception once we activate the application exception
// public List<TodoVO> getTodos() throws RestServiceException, Exception {
// checkPermission(CJFPermissionConstants.TODO_VIEW);
// return todoService.findAllTodos();
// }
//
// /**
// * Search Todos by FIQL Expression filter.
// */
// @Override
// public List<TodoVO> searchWithExpressionFilter(String searchExpression)
// throws RestServiceException, Exception {
// checkPermission(CJFPermissionConstants.TODO_VIEW);
// if (searchExpression == null) {
// throw new RestServiceException(new RestServiceExceptionDetails(
// "Todo search expression cannot be null.", Status.BAD_REQUEST));
// }
// List<TodoVO> todos = todoService.findAllTodos();
// SearchCondition<TodoVO> filter =
// new FiqlParser<TodoVO>(TodoVO.class).parse(new URLCodec().decode(searchExpression));
// return filter.findAll(todos);
// }
//
// /**
// * Get a specific todo item.
// *
// * @param id todo ID
// * @return todo if found
// */
// public TodoVO getTodo(final Long id) throws RestServiceException, Exception {
// checkPermission(CJFPermissionConstants.TODO_VIEW);
// if (id == null) {
// throw new RestServiceException(
// new RestServiceExceptionDetails("Todo id cannot be null.", Status.BAD_REQUEST));
// }
// TodoVO todoVO = todoService.getTodo(id);
// if (todoVO != null) {
// return todoVO;
// } else {
// throw new RestServiceException(
// new RestServiceExceptionDetails("Passed todo id is not found.", Status.NOT_FOUND));
// }
// }
//
// /**
// * Create a todo item.
// *
// * @param uriInfo inject uri of rest endpoint
// * @param todoVO new todo
// * @return null but http header contains location uri
// * @throws RestServiceException when we have a issue with todo creation
// */
// public Response createTodo(final UriInfo uriInfo, final TodoVO todoVO)
// throws RestServiceException, Exception {
// checkPermission(CJFPermissionConstants.TODO_CREATE);
// // new todo, so lets set ID to null so that the backend can assign one
// todoVO.setId(null);
// try {
// if (todoVO.getFilesBase64() != null && MapUtils.isNotEmpty(todoVO.getFilesBase64())) {
//
// Map<String, InputStream> filesToUpload = new HashMap<>();
// double totalSize = 0;
// for (Map.Entry<String, String> entry : todoVO.getFilesBase64().entrySet()) {
// double fileSize = calculateBase64SizeInKBytes(entry.getValue());
// if (fileSize > MAX_SIZE_PER_FILE) {
// throw new RestServiceException(new RestServiceExceptionDetails(
// "File attachment size cannot be higher than " + MAX_SIZE_PER_FILE + KBYTES,
// Status.BAD_REQUEST));
// }
// byte[] arrayBytes = Base64.getDecoder().decode(entry.getValue());
// InputStream initialStream = new ByteArrayInputStream(arrayBytes);
// filesToUpload.put(entry.getKey(), initialStream);
// totalSize += fileSize;
// if (totalSize > MAX_SIZE_COLLECTIVE_FILE) {
// throw new RestServiceException(new RestServiceExceptionDetails(
// "Total of File attachments size cannot be higher than " + MAX_SIZE_COLLECTIVE_FILE
// + KBYTES,
// Status.BAD_REQUEST));
// }
// }
// todoVO.setFiles(filesToUpload);
// }
// TodoVO todoVOResponse = todoService.createTodo(todoVO);
// return Response
// .created(uriInfo.getBaseUriBuilder().path("/v1/todos/" + todoVOResponse.getId()).build())
// .build();
// } catch (NullObjectException nullObjectException) {
// throw new RestServiceException(
// new RestServiceExceptionDetails("Todo object cannot be null.", Status.BAD_REQUEST));
// }
// }
//
// /**
// * Method to calculate the size of a base64 file in kilobytes.
// *
// * @param base64String file expressed in base64.
// * @return size of file in kilobytes.
// */
// private double calculateBase64SizeInKBytes(String base64String) {
// Double result = -1.0;
// if (StringUtils.isNotEmpty(base64String)) {
// Integer padding = 0;
// if (base64String.endsWith("==")) {
// padding = 2;
// } else {
// if (base64String.endsWith("=")) {
// padding = 1;
// }
// }
// result = (Math.ceil(base64String.length() / 4) * 3) - padding;
// }
// return result / 1000;
// }
//
// /**
// * Update a todo item.
// *
// * @param id todo ID
// * @param todoVO todo to be updated
// * @throws RestServiceException todo update exception
// */
// public void updateTodo(final Long id, TodoVO todoVO) throws RestServiceException, Exception {
// checkPermission(CJFPermissionConstants.TODO_CREATE);
// todoVO.setId(id);
// try {
// todoService.updateTodo(todoVO);
// } catch (NullObjectException nullObjectException) {
// throw new RestServiceException(
// new RestServiceExceptionDetails("Todo object cannot be null.", Status.BAD_REQUEST));
// } catch (IllegalArgumentException illegalArgumentException) {
// throw new RestServiceException(
// new RestServiceExceptionDetails("Todo id cannot be null.", Status.BAD_REQUEST));
// } catch (NoSuchObjectException noSuchObjectException) {
// throw new RestServiceException(
// new RestServiceExceptionDetails("Passed todo id is not found.", Status.NOT_FOUND));
// }
// }
//
// /**
// * Delete a todo item.
// *
// * @param id todo ID
// * @throws RestServiceException delete throws an exception
// */
// public void deleteTodo(final Long id) throws RestServiceException, Exception {
// checkPermission(CJFPermissionConstants.TODO_DELETE);
// try {
// todoService.deleteTodo(id);
// } catch (IllegalArgumentException illegalArgumentException) {
// throw new RestServiceException(
// new RestServiceExceptionDetails("Todo id cannot be null.", Status.BAD_REQUEST));
// } catch (NoSuchObjectException noSuchObjectException) {
// throw new RestServiceException(
// new RestServiceExceptionDetails("Passed todo id is not found.", Status.NOT_FOUND));
// }
// }
//
// // TODO move this method to common-web-jar
// /**
// * Raise an {@link javax.ws.rs.NotAuthorizedException} when permission is absent. x
// *
// * @param permisssion to check
// * @return true or raises an exception
// */
// private boolean checkPermission(String permisssion) throws RestServiceException {
// // Not authenticated
// if (!SecurityUtils.getSubject().isAuthenticated()) {
// LOG.debug("Found no authenticated user");
// throw new RestServiceException(
// new RestServiceExceptionDetails("Authentication is required.", Status.UNAUTHORIZED));
// }
// // Not authorized
// if (!SecurityUtils.getSubject().isPermitted(permisssion)) {
// LOG.debug("{} user does NOT have {} ", SecurityUtils.getSubject().getPrincipal(),
// permisssion);
// throw new RestServiceException(
// new RestServiceExceptionDetails("Required permisssion is missing.", Status.FORBIDDEN));
// }
// // Allowed
// LOG.debug("{} user has {} ", SecurityUtils.getSubject().getPrincipal(), permisssion);
// return true;
// }
//
// }
