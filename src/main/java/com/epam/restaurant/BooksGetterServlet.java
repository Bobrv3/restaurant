package com.epam.restaurant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BooksGetterServlet extends HttpServlet {
    private static final String DB_PROPERTIES_PATH = "src/main/resources/database.properties";
    private static final Logger LOGGER = LogManager.getLogger(BooksGetterServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        PrintWriter writer = resp.getWriter();

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
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
            LOGGER.error("Connection failed... " + e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error("class driver not found. " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Properties props = new Properties();

        try (InputStream in = Files.newInputStream(Paths.get(DB_PROPERTIES_PATH))) {
            props.load(in);
        } catch (IOException e) {
            LOGGER.error("Error reading the database properties file");
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        String url = props.getProperty("URL");
        String username = props.getProperty("USER");
        String password = props.getProperty("PASSWORD");
        String driverName = props.getProperty("DRIVER_NAME");

        Class.forName(driverName);

        return DriverManager.getConnection(url, username, password);
    }
}
