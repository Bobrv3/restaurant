package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.RegistrationUserData;
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

public class Registration implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Registration.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();
    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";
    private static final String NAME_PARAM = "name";
    private static final String PHONE_NUMBER_PARAM = "phoneNumber";
    private static final String EMAIL_PARAM = "email";
    private static final int ROLE_ID = 2;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        // TODO через билдер
        RegistrationUserData userData =
                new RegistrationUserData(
                        request.getParameter(LOGIN_PARAM),
                        request.getParameter(PASSWORD_PARAM).toCharArray(),
                        request.getParameter(NAME_PARAM),
                        request.getParameter(PHONE_NUMBER_PARAM),
                        request.getParameter(EMAIL_PARAM),
                        ROLE_ID);

        UserService userService = serviceProvider.getUserService();
        if (!userService.signUp(userData)) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/registrationPage");
            try {
                request.setAttribute("invalidSignUp", true);
                requestDispatcher.forward(request, response);
            } catch (ServletException e) {
                LOGGER.error("Error to forward in the registration command..", e);
            } catch (IOException e) {
                LOGGER.error("Invalid address getRequestDispatcher(/registrationFailed) in the registration command..", e);
            }
        }

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.println("Success! " + userData.getName());
        } catch (IOException e) {
            LOGGER.error("Error to get writer in the registration command..", e);
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
