package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.User;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;
import com.epam.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Authorization implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Authorization.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();
    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        UserService userService = serviceProvider.getUserService();

        String login = request.getParameter(LOGIN_PARAM);
        char[] password = request.getParameter(PASSWORD_PARAM).toCharArray();

        User user = null;
        PrintWriter writer = null;
        try {
            user = userService.signIn(login, password);
            writer = response.getWriter();
            writer.println("Hello " + user.getId());
        } catch (IOException e) {
            // TODO обработать искл
            e.printStackTrace();
        } catch (NullPointerException e) {
            LOGGER.info("Invalid username or password...", e);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/authorizationFailed");
            try {
                requestDispatcher.forward(request, response);
            } catch (ServletException ex) {
                LOGGER.error("Error to forward in the authorization command..", ex);
            } catch (IOException ex) {
                LOGGER.error("Invalid address getRequestDispatcher(/authorizationFailed) in the authorization command..", ex);
            }
        } finally {
            Arrays.fill(password, ' ');

            writer.flush();
            writer.close();
        }
    }
}
