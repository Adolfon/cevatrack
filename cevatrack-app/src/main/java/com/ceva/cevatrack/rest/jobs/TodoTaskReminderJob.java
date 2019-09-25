package com.ceva.cevatrack.rest.jobs;

//
// import java.util.Date;
// import java.util.List;
// import javax.ejb.AccessTimeout;
// import javax.ejb.Singleton;
// import javax.inject.Inject;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import com.ceva.framework.ejb.service.job.Job;
// import com.ceva.template.service.TodoService;
// import com.ceva.template.vo.TodoVO;
//
/// **
// * This job updates the description based on the dueDate. The execute method gets invoked by a
// Rest
// * Service. The idea to Invoke the jobs based on Rest calls rather than depending on any Quartz
// * Schedulers
// *
// * @author pruthvi.janapareddy
// */
// @Singleton
// public class TodoTaskReminderJob implements Job {
// private static final Logger LOG = LoggerFactory.getLogger(TodoTaskReminderJob.class);
//
// @Inject
// private TodoService todoService;
//
// /**
// * This will return the name of the job.
// */
// @Override
// public String getName() {
// return "todoTaskReminderJob";
// }
//
// /**
// * This defines whether the job isAsync or not.
// */
// @Override
// public boolean isAsync() {
// return false;
// }
//
// /**
// * This defines the purpose of the job and the business logic goes here The access time specifies
// * how much time a concurrent request can wait before bailing out Currently it is set to 1 sec.
// */
// @Override
// @AccessTimeout(value = 1000)
// public void execute() {
// LOG.debug("In the execute of - TodoTaskReminderJob");
// List<TodoVO> todos = todoService.findAllTodos();
// if (null != todos && !todos.isEmpty()) {
// LOG.debug("In the execute of - TodoTaskReminderJob - Size of Todos is : ", todos.size());
// todos.forEach(todo -> {
// LOG.debug("Inside the todo");
// if (null != todo && null != todo.getDuedate() && todo.getDuedate().equals(new Date())) {
// todo.setDescription(todo.getDescription() + " Today is the due Date !!");
// try {
// todoService.updateTodo(todo);
// } catch (Exception e) {
// LOG.error(
// "Exception caught while updating Todo in TodoTaskReminderJob " + e.getMessage());
// throw new RuntimeException(e);
// }
// }
// });
// }
// }
// }
