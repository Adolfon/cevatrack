package com.ceva.cevatrack.citest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.ceva.cevatrack.domain.FileUploader;
import com.ceva.cevatrack.domain.Todo;
import com.ceva.cevatrack.exception.NoSuchObjectException;
import com.ceva.cevatrack.exception.NullObjectException;
import com.ceva.cevatrack.repository.FileUploaderRepository;
import com.ceva.cevatrack.repository.TodoRepository;
import com.ceva.cevatrack.service.TodoServiceImpl;
import com.ceva.cevatrack.transformer.FileUploaderTransformer;
import com.ceva.cevatrack.transformer.TodoTransformer;
import com.ceva.cevatrack.vo.TodoVO;

public class TodoServiceTest {

  TodoServiceImpl service;

  TodoRepository mockRepository;

  TodoTransformer mockTodoTransformer;

  FileUploaderTransformer mockFileUploaderTransformer;

  FileUploaderRepository mockFileUploaderRepository;

  /**
   * Its sets up.
   * 
   * @throws Exception if something fails
   */
  @Before
  public void setUp() throws Exception {
    service = new TodoServiceImpl();
    mockRepository = mock(TodoRepository.class);
    mockTodoTransformer = mock(TodoTransformer.class);
    service.setTodoRepository(mockRepository);
    service.setTodoTransformer(mockTodoTransformer);
    mockFileUploaderRepository = mock(FileUploaderRepository.class);
    mockFileUploaderTransformer = mock(FileUploaderTransformer.class);
    service.setFileUploaderRepository(mockFileUploaderRepository);
    service.setFileUploaderTransformer(mockFileUploaderTransformer);
  }

  // @Test
  public void testCreateTodo() throws Exception {
    Todo todoForSaving = new Todo(null, "Title", "Desc", Integer.MIN_VALUE, new Date(), "User1");
    TodoVO todoVoforSaving;
    todoVoforSaving = new TodoVO(null, "Title", "Desc", Integer.MIN_VALUE, new Date(), "User1");
    Todo savedTodo = new Todo(1000L, "Title", "Desc", Integer.MIN_VALUE, new Date(), "User1");
    TodoVO savedTodoVo;
    savedTodoVo = new TodoVO(1000L, "Title", "Desc", Integer.MIN_VALUE, new Date(), "User1");
    FileUploader fileUploaderForSaving = new FileUploader(null, 1000L, "FileName", "ContentType");
    FileUploader fileUploaderForSave = new FileUploader(2000L, 1000L, "FileName", "ContentType");
    when(mockTodoTransformer.transformToTodo(any())).thenReturn(todoForSaving);
    when(mockRepository.save(todoForSaving)).thenReturn(savedTodo);
    when(mockTodoTransformer.transformToVO(savedTodo)).thenReturn(savedTodoVo);

    when(mockFileUploaderTransformer.transformToFileUploader(any()))
        .thenReturn(fileUploaderForSaving);
    when(mockFileUploaderRepository.save(fileUploaderForSaving)).thenReturn(fileUploaderForSave);
    TodoVO createdVo;
    createdVo = service.createTodo(todoVoforSaving);
    verify(mockRepository, times(1)).save(any());
    verify(mockTodoTransformer, times(1)).transformToTodo(any(TodoVO.class));
    verify(mockTodoTransformer, times(1)).transformToVO(any(Todo.class));
    verify(mockTodoTransformer, times(1)).prepareForCreate(any(Todo.class));
    // verify(mockTodoTransformer, times(1)).prepareForUpdate(any(Todo.class));

    assertNotNull(createdVo.getId());
  }

  // @Test(expected = NullObjectException.class)
  public void testCreateTodoWithNull() throws Exception {
    service.createTodo(null);
  }

  // @Test(expected = NullObjectException.class)
  public void testUpdateTodoWithNull() throws Exception {
    service.updateTodo(null);
  }

  // @Test(expected = IllegalArgumentException.class)
  public void testUpdateTodoWithoutId() throws Exception {

    TodoVO todoVoToUpdate =
        new TodoVO(null, "Title123", "Desc", Integer.MIN_VALUE, new Date(), "User1");
    service.updateTodo(todoVoToUpdate);
  }

  // @Test
  public void testUpdateTodoWithExistingId() throws Exception {
    Todo savedTodo = new Todo(1000L, "Title", "Desc", Integer.MIN_VALUE, new Date(), "User1");
    TodoVO todoVoToUpdate =
        new TodoVO(1000L, "Title123", "Desc", Integer.MIN_VALUE, new Date(), "User1");
    Todo todoToUpdate = new Todo(1000L, "Title123", "Desc", Integer.MIN_VALUE, new Date(), "User1");

    when(mockRepository.findBy(1000L)).thenReturn(savedTodo);
    when(mockRepository.save(todoToUpdate)).thenReturn(todoToUpdate);
    when(mockTodoTransformer.transformToVO(todoToUpdate)).thenReturn(todoVoToUpdate);

    TodoVO updatedVo;
    updatedVo = service.updateTodo(todoVoToUpdate);

    verify(mockRepository).save(any());
    verify(mockTodoTransformer).convertToTodo(any(TodoVO.class), any(Todo.class));
    verify(mockTodoTransformer).transformToVO(any(Todo.class));
    verify(mockTodoTransformer).prepareForUpdate(any(Todo.class));

    assertEquals(updatedVo.getTitle(), "Title123");

  }

  // @Test(expected = NoSuchObjectException.class)
  public void testUpdateTodoWithNonExistingId() throws Exception {
    TodoVO todoVoToUpdate =
        new TodoVO(2000L, "Title", "Desc", Integer.MIN_VALUE, new Date(), "User1");
    when(mockRepository.findBy(2000L)).thenReturn(null);
    service.updateTodo(todoVoToUpdate);

    verify(mockRepository).findBy(2000L);
  }

  // @Test
  public void testFindAllWhenExceptionThrownByRepository() {
    List<Todo> todoList = new ArrayList<Todo>();
    List<TodoVO> todoVoList = new ArrayList<TodoVO>();

    when(mockRepository.findAll()).thenReturn(todoList);
    when(mockTodoTransformer.transformToVO(todoList)).thenReturn(todoVoList);

    List<TodoVO> resultList = service.findAllTodos();

    assertEquals(resultList.size(), 0);

    verify(mockRepository).findAll();
    verify(mockTodoTransformer).transformToVO(todoList);
  }

  // @Test(expected = NullObjectException.class)
  public void testDeleteTodoWithNull() throws Exception {
    service.deleteTodo((TodoVO) null);
  }

  // @Test(expected = IllegalArgumentException.class)
  public void testDeleteTodoWithoutId() throws Exception {
    TodoVO todoVoToDelete =
        new TodoVO(null, "Title", "Desc", Integer.MIN_VALUE, new Date(), "User1");
    service.deleteTodo(todoVoToDelete);
  }

  // @Test(expected = NoSuchObjectException.class)
  public void testDeleteTodoWithNonExistingId() throws Exception {
    TodoVO todoVoToDelete =
        new TodoVO(2000L, "Title", "Desc", Integer.MIN_VALUE, new Date(), "User1");
    when(mockRepository.findBy(2000L)).thenReturn(null);
    service.deleteTodo(todoVoToDelete);

    verify(mockRepository).findBy(2000L);
  }

}
