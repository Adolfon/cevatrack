package com.ceva.cevatrack.rest.v1;

public interface NikeRestInterface {

}
/*
 * Copyright (c) 2018 CEVA Logistics, Inc. All Rights Reserved.
 */
//package com.ceva.wms.portal.rest.v1;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DefaultValue;
//import javax.ws.rs.HeaderParam;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.HttpHeaders;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import com.ceva.wms.portal.rest.domain.WMSPortalConfigurationRequest;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;

/**
 * BuildInfo REST resource
 */
//@Api(value = "Configuration")
//@Path("/v1/configuration")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//public interface SiteClientConfigRestService {
//

  /**
   * Validates and creates a new WMS Configuration
   */
//  @POST
//  @ApiOperation(value = "Creates or validates a WMS Configuration", response = Response.class)
//  @ApiResponses(value = {@ApiResponse(code = 401, message = "Invalid credentials"),
//      @ApiResponse(code = 400, message = "Invalid request"),
//      @ApiResponse(code = 200, message = "OK"),
//      @ApiResponse(code = 500, message = "Internal server error"),
//      @ApiResponse(code = 403, message = "Invalid User role")})
//  public Response createWMSPortalConfiguration(
//      @ApiParam(value = "Details of the configuration",
//          required = false) WMSPortalConfigurationRequest configuration,
//      @QueryParam("onlyValidate") @DefaultValue("false") @ApiParam(
//          value = "Set to true if only want to do validation, for creation do not set or set it to false",
//          required = false) boolean onlyValidate,
//      @HeaderParam(HttpHeaders.AUTHORIZATION) @ApiParam(
//          value = "Contains a user:password encoded string to be able to consume the API",
//          required = true) String authString);
//
//}