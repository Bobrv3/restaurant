package com.epam.restaurant.controller;

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
    private static final String COMMAND_PARAMETER = "command";
    private static final String LAST_COMMAND_PARAMETER = "lastCommand";
    private static final String MAIN_PAGE = "move_to_home";

    // TODO переделать не через команды, а через строку реквеста
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        HttpSession session = request.getSession(true);

        String lastCommand = (String) session.getAttribute(LAST_COMMAND_PARAMETER);
        if (lastCommand == null) {
            session.setAttribute(LAST_COMMAND_PARAMETER, MAIN_PAGE);
        }

        String requestParameter = request.getParameter(COMMAND_PARAMETER);
        if (requestParameter != null && !requestParameter.equals("change_locale")) {
            session.setAttribute(LAST_COMMAND_PARAMETER, requestParameter);
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
        } catch (SQLException e) {
            LOGGER.error(e);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
    }
}
