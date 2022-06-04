package com.epam.restaurant;

import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connectionPool.initConnectionPool();

            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from books;");

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

        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOGGER.error("error to close resultSet...");
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOGGER.error("error to close statement...");
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error("error to put connection into the pool...");
                }
            }
        }


        try {
            connectionPool.closeConnections();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
