##exemple de Web services Gestionnaire de taches mono utilisateur 

 - utilisation de Maven pour la gestion des dépendences
 - utilisation de JPA pour la persistence
 - utilisation de [Spark](http://sparkjava.com)

##install & run
```
git clone https://github.com/afpa-mx2016/spark-todo-app
cd spark-todo-app
mvn run
```

##tests des Web Services

 - Liste des todos:

 ```
 curl http://localhost:8080/api/v1/todos
 ```

 - Obtenir le todo avec id=6:

 ```
 curl http://localhost:8080/api/v1/todos/6
 ```

 - Mettre à jour le todo avec id=6:

 ```
 curl -XPUT -d '{"task":"my task", "priority":1}' http://localhost:8080/api/v1/todos/6
 ```

 - Supprimer le todo avec id=6:

 ```
 curl -XDELETE  http://localhost:8080/api/v1/todos/6
 ```

 - Créer un  todo:

 ```
 curl -XPOST -d '{"task":"my task", "priority":1}' http://localhost:8080/api/v1/todos
 ```


Mini js-app dans le navigateur `http://localhost:8080/`


