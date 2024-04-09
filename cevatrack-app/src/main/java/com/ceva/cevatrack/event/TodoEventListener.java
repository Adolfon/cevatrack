package com.ceva.cevatrack.event;

import javax.enterprise.event.Observes;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.cevatrack.vo.TodoVO;

@Named
public class TodoEventListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TodoEventListener.class);

  public void addTodoEvent(@Observes @Add TodoVO todoVo) {
    LOGGER.info("Event Listener: Todo - " + todoVo.getDescription() + " added");
  }

  public void updateTodoEvent(@Observes @Update TodoVO todoVo) {
    LOGGER.info("Event Listener: Todo - " + todoVo.getDescription() + " updated");
  }
}
