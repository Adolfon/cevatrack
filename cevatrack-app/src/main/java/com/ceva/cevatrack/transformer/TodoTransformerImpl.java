package com.ceva.cevatrack.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Singleton;
import com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformerImpl;
import com.ceva.cevatrack.domain.Todo;
import com.ceva.cevatrack.vo.TodoVO;

/**
 * TodoTransformerImpl.
 * 
 * @author mohammad.rasheed
 *
 */
@Singleton
public class TodoTransformerImpl extends AbstractEntityTransformerImpl<Todo> implements TodoTransformer {
    /**
     * transformToVO.
     * 
     * @see com.ceva.cevatrack.transformer.TodoTransformer#transformToVO (com.ceva.cevatrack.domain.Todo).
     */
    public TodoVO transformToVO(Todo todo) {

        if (todo == null) {
            return null;
        }

        // copy domain to vo
        TodoVO vo = new TodoVO();

        vo.setId(todo.getId());
        vo.setTitle(todo.getTitle());
        vo.setDescription(todo.getDescription());
        vo.setDuedate(todo.getDuedate());
        vo.setPriority(todo.getPriority());
        vo.setOwner(todo.getOwner());

        return vo;
    }

    /** transforms entity list to VO list. */

    @Override
    public List<TodoVO> transformToVO(List<Todo> users) {
        if (users == null || users.isEmpty()) {
            return new ArrayList<TodoVO>();
        }
        return users.stream().map(u -> transformToVO(u)).collect(Collectors.toList());
    }

    /**
     * (non-Javadoc).
     * 
     * @see com.ceva.cevatrack.transformer.TodoTransformer#transformToTodo(com.ceva.cevatrack.vo.TodoVO)
     */
    public Todo transformToTodo(TodoVO vo) {

        if (vo == null) {
            return null;
        }

        Todo todo = new Todo();

        todo.setId(vo.getId());
        todo.setTitle(vo.getTitle());
        todo.setDescription(vo.getDescription());
        todo.setDuedate(vo.getDuedate());
        todo.setPriority(vo.getPriority());
        todo.setOwner(vo.getOwner());

        return todo;
    }

    /** converts vo to entity. */
    @Override
    public void convertToTodo(TodoVO vo, Todo todo) {

        if (vo == null || todo == null) {
            return;
        }

        todo.setId(vo.getId());
        todo.setTitle(vo.getTitle());
        todo.setDescription(vo.getDescription());
        todo.setDuedate(vo.getDuedate());
        todo.setPriority(vo.getPriority());
        todo.setOwner(vo.getOwner());
    }

}
