package com.epam.restaurant.controller.util;

import com.epam.restaurant.dao.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@WebListener
public class ServletListner implements ServletRequestListener, ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ServletListner.class);
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String LAST_PAGE_ATTR = "lastPage";
    private static final String MAIN_PAGE_ADDR = "/home";
    private static final String NOT_LAST_PAGE_REGEX = "(/restaurant).*|(/images).*|(/css).*|(/js).*|(/ajaxController)";

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        HttpSession session = request.getSession();

        String lastPage = (String) session.getAttribute(LAST_PAGE_ATTR);
        if (lastPage == null) {
            session.setAttribute(LAST_PAGE_ATTR, MAIN_PAGE_ADDR);
        } else {
            String uri = request.getRequestURI();
            if (!uri.matches(NOT_LAST_PAGE_REGEX)) {
                session.setAttribute(LAST_PAGE_ATTR, uri);
            }
        }

    }

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

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
    }
}
