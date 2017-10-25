/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lduboeuf.todo.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.lduboeuf.todo.model.Task;

/**
 *
 * @author lionel
 */
public class TodoDAO {
    
    
    public static List<Task> findAll(){
         Connection connection = ConnectDB.getConnection();
        
        List<Task> tasks = new ArrayList<>();
        Statement stm;
        try {
            stm = connection.createStatement();

            String sql = "select * from task";
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                
                int id = rs.getInt("id");
                String task = rs.getString("task");

                Task t = new Task();
                t.setId(id);
                t.setTask(task);
                
                tasks.add(t);
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tasks;
    }
}
