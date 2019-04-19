Simple java web app that fetches todo items from a SQLite Data Base. 
The http response returned like a list of items in a Json format.

Query ad follows:
-----------------
http://localhost:8080/ToDoApplicationBackEnd/getToDoItems

Give the response:
------------------
{code}
{
"todoItems": [
"First item for description",
"The description",
"The description",
"The description",
"The description",
"The description",
"The description",
"The description",
"The description"
]
}
{code}
