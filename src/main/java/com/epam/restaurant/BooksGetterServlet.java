package com.epam.restaurant;

import com.epam.restaurant.dao.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BooksGetterServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(BooksGetterServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        PrintWriter writer = resp.getWriter();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery("Select * from books;");

            while (resultSet.next()) {
                writer.print(resultSet.getString(1));
                writer.print(" ");
                writer.print(resultSet.getString(2));
                writer.print(" ");
                writer.print(resultSet.getString(3));
                writer.print(" ");
                writer.println(resultSet.getString(4));
                writer.print("<br>");
            }

        } catch (SQLException e) {
            LOGGER.error("failed to get statement: {}", e.getMessage());
        }
        try {
            connectionPool.closeConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
