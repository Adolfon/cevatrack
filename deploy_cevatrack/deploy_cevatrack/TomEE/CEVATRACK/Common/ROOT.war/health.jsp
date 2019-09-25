<%@page import="java.io.*" %>
<%@page import="java.net.*" %>
<%@page import="javax.ws.rs.client.Client" %>
<%@page import="javax.ws.rs.client.ClientBuilder" %>
<%@page import="javax.ws.rs.core.MediaType" %>
<%@page import="javax.ws.rs.core.Response" %>
<%@page import="javax.json.*" %>

<!DOCTYPE html>
<html>
   <head>
      <title>Health Check</title>
   </head>
   
   <body>
   <%
   		Client client = ClientBuilder.newClient();
  	 	String url = "http://localhost:8080/"+System.getProperty("app.context")+"/rest/api/v1/health/";
   		
   		Response resp = client.target(url).request(MediaType.APPLICATION_JSON).get();
   		
   		if(resp.getStatus() != Response.Status.OK.getStatusCode()) {
   		  throw new RuntimeException("Unable to fulfill the request: HTTP status: " + resp.getStatus());
   		} else {
   		  String body = resp.readEntity(String.class);
   		  JsonReader reader = Json.createReader(new StringReader(body));
   		  JsonObject json = reader.readObject();
   		  reader.close();
   		  
   		  // Check health status and return 500 if not OK.
   		  String result = json.getString("result");
   		  if ("Not OK".equals(result)) {
   		    throw new RuntimeException("Something is not OK with the application");
   		  }
   		}
   %>		
   </body>
</html>