package com.ceva.cevatrack.transformer;

import java.util.List;
import com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformer;
import com.ceva.cevatrack.domain.Todo;
import com.ceva.cevatrack.vo.TodoVO;

/**
 * TodoTransformer.
 * 
 * @author mohammad.rasheed
 *
 */
public interface TodoTransformer extends AbstractEntityTransformer<Todo> {
    /** converts entity to vo. */
    public TodoVO transformToVO(Todo todo);

    /** converts entity list to vo list. */
    public List<TodoVO> transformToVO(List<Todo> users);

    /** converts vo to entity. */
    public Todo transformToTodo(TodoVO todo);

    /** converts vo to entity. */
    public void convertToTodo(TodoVO todoVo, Todo todo);

}
