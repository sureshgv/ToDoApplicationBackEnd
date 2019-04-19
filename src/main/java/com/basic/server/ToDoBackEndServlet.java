package com.basic.server;

/**
 * @author sureshgv
 */

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.sqlite.JDBC;
import org.sqlite.SQLiteConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ToDoBackEndServlet extends HttpServlet {
    private static String DB_FILE = "/Users/sureshgv/learn/todoList.db";
    private static String TODO_ITEMS_STRING = "todoItems";
    private final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private static Logger logger = Logger.getLogger(ToDoBackEndServlet.class.getCanonicalName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // TODO: Move the string items to properties as necessary
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        final PrintWriter out = resp.getWriter();
        List<String> fetchedItems = fetchToDoItems(DB_FILE);
        out.write(convertToJson(fetchedItems));
    }

    public String convertToJson(List<String> todoItems) {
        JsonArray jarray = new Gson().toJsonTree(todoItems).getAsJsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(TODO_ITEMS_STRING, jarray);
        return jsonObject.toString();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private static List<String> fetchToDoItems(String sqliteFile) {
        List<String> todoItems = new ArrayList<String>();
        final String query = "select * from todoItems;";
        final String dbUrl = "jdbc:sqlite:" + sqliteFile;

        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig sqliteConfig = new SQLiteConfig();
            sqliteConfig.setReadOnly(true);
            Properties properties = sqliteConfig.toProperties();
            Connection dbConnect = JDBC.createConnection(dbUrl, properties);
            PreparedStatement dbPrepare = dbConnect.prepareStatement(query);
            ResultSet rs = dbPrepare.executeQuery();
            while (rs.next()) {
                long id = rs.getLong(1);
                String description = rs.getString(2);
                todoItems.add(description);
            }
            Thread.sleep(1000);
            dbPrepare.close();
            dbConnect.close();
        } catch (Exception e) {
            logger.error("Cannot connect to the SQLiteDB.");
            throw new RuntimeException(e);
        }

        for (String item : todoItems) {
            System.out.println("The item: " + item);
        }
        return todoItems;
    }
}
