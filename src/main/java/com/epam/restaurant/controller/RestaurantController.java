package com.epam.restaurant.controller;

import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.controller.command.CommandProvider;
import com.epam.restaurant.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;


public class RestaurantController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(RestaurantController.class);
    private static final CommandProvider commandProvider = CommandProvider.getInstance();
    private static final String PARAMETER_COMMAND = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Command command = commandProvider.getCommand(req.getParameter(PARAMETER_COMMAND).toUpperCase());

        try {
            command.execute(req, resp);
        } catch (ServiceException | ServletException e) {
            LOGGER.error(e.getMessage(), e);

            try {
                int lastIndx = e.getMessage().lastIndexOf(":");
                if (lastIndx == -1) {
                    String errorMsg = e.getMessage();

                    resp.sendRedirect(MessageFormat.format("/errorPage?errorMsg={0}", errorMsg));

                } else {
                    String errorMsg = e.getMessage().substring(lastIndx);
                    resp.sendRedirect(MessageFormat.format("/errorPage?errorMsg={0}", errorMsg));
                }
            } catch (IOException ex) {
                LOGGER.error("Error: invalid address to redirect");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Command command = commandProvider.getCommand(req.getParameter(PARAMETER_COMMAND).toUpperCase());

        try {
            command.execute(req, resp);
        } catch (ServiceException | ServletException e) {
            LOGGER.error(e.getMessage(), e);

            try {
                int lastIndx = e.getMessage().lastIndexOf(":");
                if (lastIndx == -1) {
                    String errorMsg = e.getMessage();

                    resp.sendRedirect(MessageFormat.format("/errorPage?errorMsg={0}", errorMsg));

                } else {
                    String errorMsg = e.getMessage().substring(lastIndx);
                    resp.sendRedirect(MessageFormat.format("/errorPage?errorMsg={0}", errorMsg));
                }
            } catch (IOException ex) {
                LOGGER.error("Error: invalid address to redirect");
            }
        }
    }
}
