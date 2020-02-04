package com.ceva.cevatrack.integrationtest.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import org.apache.johnzon.jaxrs.JohnzonProvider;
import org.junit.Test;
import com.ceva.cevatrack.vo.TodoVO;

/**
 * STANDARDS driven approach <br>
 * Test jaxrs rest client for Todo service using JaxRs 2.0 standards API
 */
public class TodoRestClientUsingJaxRsClientIntegrationTest {
    private static final String SOAPUI_MOCK_SERVER_URL = "http://localhost:8010/cjf-template/rest/api/";

    /**
     * Using JaxRs apis consume the mock rest endpoint ( see test/resources/soapui/*.xml )
     *
     * @throws Exception
     *             something failed miserably
     */
    //@Test
    public void testUsingJaxRsClient() throws Exception {
        // Create JaxRs client and register Johnzon as the json mapper
        Client client = ClientBuilder.newBuilder().newClient().register(new JohnzonProvider<>());
        // point the client to the right endpoint, in this case todo
        WebTarget target = client.target(SOAPUI_MOCK_SERVER_URL + "/v1/todo");
        Invocation.Builder builder = target.request();

        // using generic type marshal the response as a list of todos
        List<TodoVO> todos = builder.get(new GenericType<List<TodoVO>>() {
        });

        // now assert
        //assertNotNull("Todo was not fetched correctly from soapui mock endpoint", todos);
        //assertEquals("We did not get back 3 todo items", 3, todos.size());

    }

}
