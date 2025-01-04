package com.lcwd.todo.dao;

import com.lcwd.todo.helper.Helper;
import com.lcwd.todo.models.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@Repository
public class TodoDao {

    Logger logger = LoggerFactory.getLogger(TodoDao.class);

    private JdbcTemplate template;

    @Autowired
    public TodoDao( JdbcTemplate template) {
        this.template = template;

        //create table if doesn't exist

        String createTable = "CREATE TABLE IF NOT EXISTS todos (" +
                "id INT PRIMARY KEY, " +
                "title VARCHAR(100) NOT NULL, " +
                "content VARCHAR(500), " +
                "status VARCHAR(10) NOT NULL, " +
                "addedDate TIMESTAMP, " +
                "todoDate TIMESTAMP" +
                ")";
        int update = template.update(createTable);
        logger.info("TODO TABLE CREATED {}", update);
    }

    public TodoDao(){}

    public JdbcTemplate getTemplate() {
        return template;
    }
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    //Save TODO in database...
    public Todo saveTodo(Todo todo){
        String insertQuery = " insert into todos(id, title, content, status, addedDate, todoDate) values(?,?,?,?,?,?) ";

        int rows = template.update(insertQuery,todo.getId(), todo.getTitle(), todo.getContent(), todo.getStatus(), todo.getAddedDate(), todo.getToDoDate());

        logger.info("JDBC OPERATION: {} inserted", rows);

        return todo;
    }

    //Get single TODO from database...
    public Todo getTodo(int id) throws ParseException {
        String query = "select * from todos WHERE id = ?";
        //without using roeMapper
//        Map<String, Object> todoData = template.queryForMap(query, id);
//
//        Todo todo = new Todo();
//
//        todo.setId(((int) todoData.get("id")));
//        todo.setStatus(((String) todoData.get("title")));
//        todo.setContent(((String) todoData.get("content")));
//        todo.setStatus(((String) todoData.get("status")));
//        todo.setAddedDate(Helper.parseDate(((Timestamp) todoData.get("addedDate")).toLocalDateTime()));
//        todo.setToDoDate(Helper.parseDate(((Timestamp) todoData.get("todoDate")).toLocalDateTime()));
//        todo.setAddedDate(Helper.parseDate((LocalDateTime) todoData.get("addedDate")));
//        todo.setToDoDate(Helper.parseDate((LocalDateTime) todoData.get("todoDate")));

        //with using RowMapper
//        Todo todo = template.queryForObject(query, new TodoRowMapper(), id);
        //rowMapper anonymous class
        Todo todo = template.queryForObject(query, new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                Todo todo = new Todo();

                todo.setId((rs.getInt("id")));
                todo.setStatus((rs.getString("title")));
                todo.setContent((rs.getString("content")));
                todo.setStatus((rs.getString("status")));
                try {// Handle `addedDate`
                    Timestamp addedDateTimestamp = rs.getObject("addedDate", Timestamp.class);
                    if (addedDateTimestamp != null) {
                        todo.setAddedDate(Helper.parseDate(addedDateTimestamp.toLocalDateTime()));
                    }

                    // Handle `todoDate`
                    Timestamp todoDateTimestamp = rs.getObject("todoDate", Timestamp.class);
                    if (todoDateTimestamp != null) {
                        todo.setToDoDate(Helper.parseDate(todoDateTimestamp.toLocalDateTime()));
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
//        todo.setAddedDate(Helper.parseDate((LocalDateTime) todoData.get("addedDate")));
//        todo.setToDoDate(Helper.parseDate((LocalDateTime) todoData.get("todoDate")));

                return todo;
            }
        }, id);
        return todo;
    }

    // Get all TODO from database...
    public List<Todo> getAllTodos(){
        String query = "select * from todos";
        //without using rowMapper
//        List<Map<String, Object>> maps = template.queryForList(query);
//        List<Todo> todos = maps.stream().map((map)->{
//            Todo todo = new Todo();
//
//            todo.setId(((int) map.get("id")));
//            todo.setStatus(((String) map.get("title")));
//            todo.setContent(((String) map.get("content")));
//            todo.setStatus(((String) map.get("status")));
//            try {
//                todo.setAddedDate(Helper.parseDate(((Timestamp) map.get("addedDate")).toLocalDateTime()));
//                todo.setToDoDate(Helper.parseDate(((Timestamp) map.get("todoDate")).toLocalDateTime()));
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//
//            return todo;
//        }).toList();
        //using rowMapper
//        List<Todo> todos = template.query(query, new TodoRowMapper());
        //rowMapper using lambda expression
        List<Todo> todos = template.query(query,(rs, rowNum) -> {
            Todo todo = new Todo();

            todo.setId((rs.getInt("id")));
            todo.setStatus((rs.getString("title")));
            todo.setContent((rs.getString("content")));
            todo.setStatus((rs.getString("status")));
            try {// Handle `addedDate`
                Timestamp addedDateTimestamp = rs.getObject("addedDate", Timestamp.class);
                if (addedDateTimestamp != null) {
                    todo.setAddedDate(Helper.parseDate(addedDateTimestamp.toLocalDateTime()));
                }

                // Handle `todoDate`
                Timestamp todoDateTimestamp = rs.getObject("todoDate", Timestamp.class);
                if (todoDateTimestamp != null) {
                    todo.setToDoDate(Helper.parseDate(todoDateTimestamp.toLocalDateTime()));
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
//        todo.setAddedDate(Helper.parseDate((LocalDateTime) todoData.get("addedDate")));
//        todo.setToDoDate(Helper.parseDate((LocalDateTime) todoData.get("todoDate")));

            return todo;
        });

        return todos;
    }

    // Update TODO...
    public Todo updateTodo(int id, Todo newTodo){
        String query = "update todos set title=?,content=?,status=?,addedDate=?,todoDate=? WHERE id=?";
        int update = template.update(query, newTodo.getTitle(), newTodo.getContent(), newTodo.getStatus(), newTodo.getAddedDate(), newTodo.getToDoDate(),id);
        logger.info("Updated {}", update);
        newTodo.setId(id);
        return newTodo;
    }

    // Delete single TODO from database...
    public void deleteTodo(int id){
        String query = "delete from todos WHERE id=?";
        int update = template.update(query, id);
        logger.info("Deleted {}",update);
    }

    //Delete all TODO from database...
    public void deleteMultiple(int ids[]){
        String query = "delete from todos WHERE id=?";
        //for forEach=>
        int[] ints = template.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int id = ids[i];
                ps.setInt(1,id);
            }

            @Override
            public int getBatchSize() {
                return ids.length;
            }
        });
        for(int i : ints){
            logger.info("Deleted {}",i);
        }
    }

}
