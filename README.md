Simple java web app that fetches todo items from a SQLite Data Base. 
The http response returns a list of items in a Json format.

Query as follows:
-----------------
Once deployed to Apache Tomcat server. You can access using the following query.
http://localhost:8080/ToDoApplicationBackEnd/getToDoItems

Gives the response:
------------------
{
"todoItems": [
"First item for description",
"The description1",
"The description2",
"The description2",
]
}

