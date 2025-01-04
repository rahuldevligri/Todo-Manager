package com.lcwd.todo;

import com.lcwd.todo.dao.TodoDao;
import com.lcwd.todo.models.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;


@SpringBootApplication
public class TodoManagerApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(TodoManagerApplication.class);

	@Autowired
	private TodoDao todoDao;
	public static void main(String[] args) {
		SpringApplication.run(TodoManagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		logger.info("Application Started");
//		JdbcTemplate template = todoDao.getTemplate();
//		logger.info("Template Object : {}", template);

		//create todo
//		Todo todo = new Todo();
//		todo.setId(2);
//		todo.setTitle("Java placement course");
//		todo.setContent("jabe");
//		todo.setStatus("PENDING");
//		todo.setAddedDate(new Date());
//		todo.setToDoDate(new Date());
//
//		todoDao.saveTodo(todo);

		//get Single todo using id
//		Todo todo = todoDao.getTodo(1);
//		logger.info("TODO : {}",todo);

		//get all todos
//		List<Todo> allTodos = todoDao.getAllTodos();
//		logger.info("All TODOS: {}", allTodos);

		//update todo using id
//		Todo todo = todoDao.getTodo(2);
//		logger.info("TODO : {}",todo);
//		todo.setTitle("Spring Boot");
//		todo.setContent("SpringBoot Learning");
//		todo.setStatus("Done");
//		todo.setAddedDate(new Date());
//		todo.setToDoDate(new Date());
//		todoDao.updateTodo(2,todo);

		//delete single todo
//		todoDao.deleteTodo(2);

		//delete all todos
//		todoDao.deleteMultiple(new int[]{1,2});


//		logger.info("{}", todoDao.getTodo(3717155));
//		logger.info("{}", todoDao.getAllTodos());
	}
}
