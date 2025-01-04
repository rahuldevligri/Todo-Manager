package com.lcwd.todo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data //@Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor
@NoArgsConstructor // Generates a no-arguments constructor
@AllArgsConstructor // Generates an all-arguments constructor
@Entity
@Table(name = "jpa_todos")
public class Todo {

    @Id
    private int id;
    @Column(name = "todo_title", length = 100)
    private String title;

    @Column(name = "todo_content", length = 1000)
    private String content;

    @Column(name = "todo_status", length = 10)
    private String status;

    //when todo add it will add automatically
    @Column(name="todo_added_date")
    private Date addedDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "todo_todo_date")
    private Date toDoDate;
}
