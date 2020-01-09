package com.ceva.template.api.v1;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;
import com.ceva.template.api.v1.health.HealthApiTest;
import com.ceva.template.api.v1.todo.TodoApiTest;

/**
 * Test suite for all test cases.
 */
@RunWith(JUnitPlatform.class)
@SelectClasses({HealthApiTest.class, TodoApiTest.class})
public class AllTests {

}
