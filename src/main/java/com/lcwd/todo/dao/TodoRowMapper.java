package com.lcwd.todo.dao;

import com.lcwd.todo.helper.Helper;
import com.lcwd.todo.models.Todo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;

public class TodoRowMapper implements RowMapper<Todo> {
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
}
