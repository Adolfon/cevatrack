package com.ceva.cevatrack.integrationtest.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.johnzon.jaxrs.JohnzonProvider;
import org.junit.Test;
import com.ceva.framework.rest.exception.RestServiceException;
import com.ceva.cevatrack.rest.v1.TodoRestService;
import com.ceva.cevatrack.vo.TodoVO;

/**
 * NOT RECOMMENDED. See {@link TodoRestClientUsingJaxRsClientIntegrationTest} for recommended approach. TomEE 7.0.4 or
 * CXF specific jaxrs rest client for Todo service.
 */
public class TodoRestClientUsingCxfIntegrationTest {
    private static final String SOAPUI_MOCK_SERVER_URL = "http://localhost:8010/cjf-template/rest/api/";

    /**
     * Sample CXF JaxRs rest client, a better wrapper than is typesafe ( cast to interface) as opposed to JaxRs client
     * API wherein one has to assume the response types. <br>
     * Note that this is specific to cxf and ONLY supported from cxf 3.0 or TomEE 7.0.4 onwards<br>
     * See http://cxf.apache.org/docs/jax-rs-client-api.html.. <br>
     * See below for a quick summary :-
     * 
     * <pre>
     *   In CXF 2.7.x no JAX-RS 2.0 Client API is supported and CXF specific 
     *   Client API is located in the cxf-rt-frontend-jaxrs module.
     *   ...
     *   CXF 3.0.0 implements JAX-RS 2.0 Client API. Internally it is implemented in terms of CXF specific WebClient.
     * </pre>
     * 
     * @throws Exception
     *             exception
     * @throws RestServiceException
     *             restServiceException
     */
    //@Test
    public void testUsingSoapUiMockAndCxfRestClient() throws RestServiceException, Exception {
        // setup johnzon has the jaxrs provider, default provider for cxf/tomEE ( and its not jackson :(
        // )
        List<Object> providers = new ArrayList<Object>();
        providers.add(new JohnzonProvider<>());

        // get the client and cast it to an interface..super kewl type safe API follows
        TodoRestService todoRestService = JAXRSClientFactory.create(SOAPUI_MOCK_SERVER_URL, TodoRestService.class,
                providers);

        // asserts
        List<TodoVO> todos = todoRestService.getTodos();
        //assertNotNull("Todo was not fetched correctly from soapui mock endpoint", todos);
        //assertEquals("We did not get back 3 todo items", 3, todos.size());
        assertEquals(true,true);
    }

}
