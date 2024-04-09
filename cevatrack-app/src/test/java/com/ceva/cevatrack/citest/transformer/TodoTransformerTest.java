package com.ceva.cevatrack.citest.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.ceva.cevatrack.domain.Todo;
import com.ceva.cevatrack.transformer.TodoTransformer;
import com.ceva.cevatrack.transformer.TodoTransformerImpl;
import com.ceva.cevatrack.vo.TodoVO;

public class TodoTransformerTest {

  TodoTransformer transformer = new TodoTransformerImpl();

  @Before
  public void setUp() throws Exception {}

  // @Test
  public void testTransformToVo() {
    Todo todo = new Todo(1000L, "Title", "Desc", Integer.MIN_VALUE, new Date(), "User1");
    TodoVO vo = transformer.transformToVO(todo);

    assertEquals(todo.getId(), vo.getId());
    assertEquals(todo.getTitle(), vo.getTitle());
    assertEquals(todo.getDescription(), vo.getDescription());
    assertEquals(todo.getPriority(), vo.getPriority());
    assertEquals(todo.getDuedate(), vo.getDuedate());
    assertEquals(todo.getOwner(), vo.getOwner());
  }

  // @Test
  public void testTransformNullToVo() {
    Todo todo = null;
    TodoVO vo = transformer.transformToVO(todo);
    assertEquals(vo, null);
  }

  // @Test
  public void testTransformToTodo() {
    TodoVO vo = new TodoVO(1000L, "Title123", "Desc", Integer.MIN_VALUE, new Date(), "User1");
    Todo todo = transformer.transformToTodo(vo);

    assertEquals(todo.getId(), vo.getId());
    assertEquals(todo.getTitle(), vo.getTitle());
    assertEquals(todo.getDescription(), vo.getDescription());
    assertEquals(todo.getPriority(), vo.getPriority());
    assertEquals(todo.getDuedate(), vo.getDuedate());
    assertEquals(todo.getOwner(), vo.getOwner());
  }

  // @Test
  public void testTransformNullToTodo() {
    TodoVO vo = null;
    Todo todo = transformer.transformToTodo(vo);
    assertEquals(todo, null);
  }

  // @Test
  public void testConvertToTodo() {
    TodoVO vo = new TodoVO(2000L, "Title123", "Desc123", Integer.MIN_VALUE, new Date(), "User2");
    Todo todo = new Todo(1000L, "Title", "Desc", Integer.MIN_VALUE, new Date(), "User1");

    transformer.convertToTodo(vo, todo);

    assertEquals(todo.getId(), vo.getId());
    assertEquals(todo.getTitle(), vo.getTitle());
    assertEquals(todo.getDescription(), vo.getDescription());
    assertEquals(todo.getPriority(), vo.getPriority());
    assertEquals(todo.getDuedate(), vo.getDuedate());
    assertEquals(todo.getOwner(), vo.getOwner());
  }

  // @Test
  public void testConvertVoToNullTodo() {
    TodoVO vo = new TodoVO(2000L, "Title123", "Desc123", Integer.MIN_VALUE, new Date(), "User2");
    Todo todo = null;

    transformer.convertToTodo(vo, todo);
    assertEquals(todo, null);
    assertNotEquals(vo, null);
  }

  // @Test
  public void testConvertNullVoToTodo() {
    TodoVO vo = null;
    Todo todo = new Todo(1000L, "Title", "Desc", Integer.MIN_VALUE, new Date(), "User1");

    transformer.convertToTodo(vo, todo);
    assertEquals(vo, null);
    assertNotEquals(todo, null);
  }

  // @Test
  public void testConvertNullVoToNullTodo() {
    TodoVO vo = null;
    Todo todo = null;

    transformer.convertToTodo(vo, todo);
    assertEquals(vo, null);
    assertEquals(todo, null);
  }

  // @Test
  public void testTransformToVoList() {
    List<Todo> users = new ArrayList<Todo>();
    users.add(new Todo(1001L, "Title1", "Desc1", Integer.MIN_VALUE, new Date(), "User1"));
    users.add(new Todo(1002L, "Title2", "Desc2", Integer.MIN_VALUE, new Date(), "User2"));

    List<TodoVO> voList = transformer.transformToVO(users);

    assertEquals(voList.size(), users.size());
    for (int i = 0; i < users.size(); i++) {

      assertEquals(users.get(i).getId(), voList.get(i).getId());
      assertEquals(users.get(i).getTitle(), voList.get(i).getTitle());
      assertEquals(users.get(i).getDescription(), voList.get(i).getDescription());
      assertEquals(users.get(i).getPriority(), voList.get(i).getPriority());
      assertEquals(users.get(i).getDuedate(), voList.get(i).getDuedate());
      assertEquals(users.get(i).getOwner(), voList.get(i).getOwner());
    }
  }

  // @Test
  public void testTransformNullTodoList() {
    List<Todo> users = null;
    List<TodoVO> usersVo = transformer.transformToVO(users);
    assertEquals(usersVo.size(), 0);
  }

  // @Test
  public void testTransformEmptyTodoList() {
    List<Todo> users = new ArrayList<Todo>();
    List<TodoVO> usersVo = transformer.transformToVO(users);
    assertEquals(usersVo.size(), 0);
  }

}
