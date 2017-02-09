/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lduboeuf.todo;

import com.google.gson.Gson;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.lduboeuf.todo.model.Task;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;

/**
 *
 * @author lionel
 */
public class Main {
    
    static final String PREFIX = "/api/v1";
    static final Gson gson = new Gson();
    
    public static void main(String[] args) {
        //get("/hello", (req, res) -> "Hello World");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tasks-pu");
        EntityManager em = emf.createEntityManager();
        
        
        port(8080);
        staticFiles.location("/public"); // Static files
        
        get(PREFIX + "/todos", "application/json", (request, response) -> {
            List<Task> tasks = em.createNamedQuery("Task.findAll").getResultList();
            return tasks;
        }, new JsonTransformer());
        
        get(PREFIX + "/todos/:id", "application/json", (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            Task task = em.find(Task.class, id);
            return task;
            //return new MyMessage("Hello World");
        }, new JsonTransformer());
        
        post(PREFIX + "/todos", "application/json", (request, response) -> {
            //gson.
            Task t = gson.fromJson(request.body(), Task.class);
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(t);
            tx.commit();
            
            response.status(201);
            
            return t;
        }, new JsonTransformer());
        
        put(PREFIX + "/todos/:id", "application/json", (request, response) -> {
            
            Integer id = Integer.valueOf(request.params(":id"));
            Task task = em.find(Task.class, id);
            if (task==null){
                response.status(404);
                return null;
            }
            
            Task t = gson.fromJson(request.body(), Task.class);
            t.setId(Integer.valueOf(request.params(":id")));
            task.setTask(t.getTask());
            task.setStatus(t.getStatus());
            task.setPriority(t.getPriority());
            
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(task);
            tx.commit();
            return task;
        }, new JsonTransformer());
         
        delete(PREFIX + "/todos/:id","*/*", (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            Task task = em.find(Task.class, id);
            if (task==null){
                response.status(404);
                return null;
            }
            EntityTransaction tx = em.getTransaction();
            em.getTransaction().begin();
            em.remove(task);
            em.getTransaction().commit();
            
            response.status(204);
            
            return "ok";
        });
        
    }
}
