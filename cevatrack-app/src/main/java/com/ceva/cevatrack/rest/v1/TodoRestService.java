package com.ceva.cevatrack.rest.v1;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.ceva.framework.rest.exception.RestServiceException;
import com.ceva.cevatrack.vo.TodoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

/**
 * Todo REST resource. https://hostname:port/cjf-template/swagger/api-docs?url=/cjf-template/rest/api/swagger.json
 * 
 */
@Api(value = "Todo")
@SwaggerDefinition(info = @Info(description = "Todo REST Service", version = "v1", title = "Todo REST Service", contact = @Contact(name = "Ceva", email = "cjf@ceva.com")))
@Path("/v1/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TodoRestService {

    /**
     * Get all todos.
     * 
     * @return todos
     * @throws RestServiceException
     *             when we have a issue while retrieving todo
     */
    @GET
    @ApiOperation(value = "Get all todos", response = TodoVO.class, responseContainer = "List")
    public List<TodoVO> getTodos() throws RestServiceException, Exception;

    /**
     * Search all todos by FIQL filter.
     * 
     * @return todos
     * @throws RestServiceException
     *             when we have a issue while retrieving todo
     */
    @GET
    @Path("/search")
    @ApiOperation(value = "Search with FIQL expression filter.", response = TodoVO.class, responseContainer = "List")
    public List<TodoVO> searchWithExpressionFilter(
            @ApiParam(value = "_s", required = true) @QueryParam("_s") String searchExpression)
            throws RestServiceException, Exception;

    /**
     * Get a specific todo item.
     * 
     * @param id
     *            todo ID
     * @return todo if found
     * @throws RestServiceException
     *             when we have a issue while retrieving todo
     */
    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get a todo item", response = TodoVO.class)
    /*
     * @ApiResponses annotation is used for swagger documentation. This shows all possible status codes this API can
     * return along with a detailed message describing about the status code. This annotation is not mandatory. Use this
     * annotation if you want to explain about the status in detail. Below is the demonstration on when we need
     * ApiResponse and how to make it clear.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The requested todo id is found and the object is returned back in response."),
            @ApiResponse(code = 401, message = "Not Authorized to access this API. To gain access to system, please use login api and use the token on every request."),
            @ApiResponse(code = 403, message = "To get access contact support and request them to assign ToDo Management role."),
            @ApiResponse(code = 404, message = "Either the todo you are tryiing to access does not exist or it may be deleted by someone else.") })
    public TodoVO getTodo(@ApiParam(value = "name", required = true) @PathParam("id") final Long id)
            throws RestServiceException, Exception;

    /**
     * Create a todo item.
     * 
     * @param uriInfo
     *            inject uri of rest endpoint
     * @param todoVO
     *            new todo
     * @return null but http header contains location uri
     * @throws RestServiceException
     *             when we have a issue with todo creation
     */
    @POST
    @ApiOperation(value = "Create a todo item")
    public Response createTodo(@Context final UriInfo uriInfo,
            @ApiParam(value = "todo", required = true) final TodoVO todoVO) throws RestServiceException, Exception;

    /**
     * Update a todo item.
     * 
     * @param id
     *            todo ID
     * @param todoVO
     *            todo to be updated
     * @throws RestServiceException
     *             todo update exception
     */
    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Create a todo item", response = TodoVO.class)
    public void updateTodo(@PathParam("id") final Long id, TodoVO todoVO) throws RestServiceException, Exception;

    /**
     * Delete a todo item.
     * 
     * @param id
     *            todo ID
     * @throws RestServiceException
     *             delete throws an exception
     */
    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete a todo item")
    public void deleteTodo(@PathParam("id") final Long id) throws RestServiceException, Exception;
}
