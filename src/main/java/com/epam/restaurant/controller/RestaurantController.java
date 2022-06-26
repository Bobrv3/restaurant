package com.epam.restaurant.controller;

import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.controller.command.CommandName;
import com.epam.restaurant.controller.command.CommandProvider;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class RestaurantController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(RestaurantController.class);
    private static final CommandProvider commandProvider = new CommandProvider();
    private static final String PARAMETER_COMMAND = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");

        Command command = commandProvider.getCommand(req.getParameter(PARAMETER_COMMAND).toUpperCase());
        try {
            command.execute(req, resp);
        } catch (ServiceException e) {
            resp.sendRedirect("/errorPage");
        } catch (ServletException e) {
            LOGGER.error(e);
            resp.sendRedirect("/errorPage");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");

        Command command = commandProvider.getCommand(req.getParameter(PARAMETER_COMMAND).toUpperCase());
        try {
            command.execute(req, resp);
        } catch (ServiceException e) {
            resp.sendRedirect("/errorPage");
        } catch (ServletException e) {
            LOGGER.error(e);
            resp.sendRedirect("/errorPage");
        }
    }
}
