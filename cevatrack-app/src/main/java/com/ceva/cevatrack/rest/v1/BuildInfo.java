package com.ceva.cevatrack.rest.v1;

import java.io.IOException;
import java.util.jar.Attributes;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

/**
 * BuildInfo REST resource.
 */
@Api(value = "BuildInfo")
@SwaggerDefinition(info = @Info(description = "BuildInfo as Util services for application info",
    version = "2.0.0-SNAPSHOT", title = "BuildInfo REST Service",
    contact = @Contact(name = "Ceva", email = "cjf@ceva.com")))
@Path("/v1/buildInfo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BuildInfo {

  /**
   * Get application build information.
   *
   * @param request servlet request context
   * @return Attributes of info
   * @throws IOException when un-able to read file
   */
  @GET
  @ApiOperation(value = "Get application build information", response = Attributes.class,
      responseContainer = "Attributes")
  @ApiResponses(
      value = {@ApiResponse(code = 204, message = "unable to read the file /META-INF/MANIFEST.MF")})
  public Response getApplicationInfo(@Context HttpServletRequest request) throws IOException;

}
