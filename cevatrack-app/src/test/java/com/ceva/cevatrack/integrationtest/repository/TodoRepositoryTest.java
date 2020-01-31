package com.ceva.cevatrack.integrationtest.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.apache.openejb.config.DeploymentFilterable;
import org.apache.openejb.junit.jee.EJBContainerRule;
import org.apache.openejb.junit.jee.InjectRule;
import org.apache.openejb.junit.jee.config.Properties;
import org.apache.openejb.junit.jee.config.Property;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import com.ceva.cevatrack.domain.Todo;
import com.ceva.cevatrack.repository.TodoRepository;

@Properties({ @Property(key = DeploymentFilterable.CLASSPATH_INCLUDE, value = ".*"),
        @Property(key = "openejb.deployments.classpath.filter.descriptors", value = "true"),
        @Property(key = "openejb.exclude-include.order", value = "include-exclude"),
        @Property(key = "openejb.altdd.prefix", value = "test") })
public class TodoRepositoryTest {

    @ClassRule
    public static final EJBContainerRule CONTAINER_RULE = new EJBContainerRule();

    @Rule
    public final InjectRule injectRule = new InjectRule(this, CONTAINER_RULE);

    @Inject
    TodoRepository todoRepository;

    //@Test
    public void testCreateTodo() {
        assertNotNull("Todo repository wasn't injected", todoRepository);
        Todo todo = new Todo(null, "8-August", "Desc", Integer.MIN_VALUE, new Date(), "User1");
        todo.setCreatedOn(new Date());
        todo.setCreatedBy("unit test");
        Todo savedTodo = todoRepository.save(todo);
        assertNotNull("DB did not generate the ID", savedTodo.getId());
        assertTrue("We have our todo in the DB", todoRepository.findAll().size() > 0);
    }

    //@Test
    public void testDeleteTodo() {
        Todo todo = new Todo(null, "Todo", "Desc", Integer.MIN_VALUE, new Date(), "User1");
        todo.setCreatedOn(new Date());
        todo.setCreatedBy("unit test");
        Todo savedTodo = todoRepository.save(todo);
        assertNotNull("DB did not generate the ID", savedTodo.getId());
        Long id = todo.getId();
        todoRepository.attachAndRemove(todo);
        Todo todo1 = todoRepository.findBy(id);
        assertEquals("We found out delete todo in DB", null, todo1);
    }

    //@Test
    public void testFindByLikeReturningQueryResult() {

        for (int i = 1; i < 100; i++) {
            Todo todo = new Todo("Todo" + i, "Desc" + i, Integer.MIN_VALUE, new Date(), "User1");
            todo.setCreatedBy("unit.test");
            todo.setCreatedOn(new Date());
            todoRepository.save(todo);
        }

        List<Todo> result = todoRepository.findAll();
        assertNotNull(result);
        assertEquals(99L, result.size());
    }

}
