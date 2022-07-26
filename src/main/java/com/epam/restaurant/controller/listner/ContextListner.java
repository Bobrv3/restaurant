package com.epam.restaurant.controller.listner;

import com.epam.restaurant.dao.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ContextListner implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ContextListner.class);
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            connectionPool.initConnectionPool();
        } catch (ClassNotFoundException | InterruptedException | SQLException e) {
            LOGGER.error("Connection pool initialization error...", e);
            throw new RuntimeException("Connection pool initialization error", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            connectionPool.dispose();
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(e);
        }
    }
}
