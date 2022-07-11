package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;
import com.epam.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Authorization implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Authorization.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String USER_ATTR = "user";
    private static final String INVALID_SIGN_IN_ATTR = "invalidSignIn";

    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";

    private static final String HOME_ADDRESS = "/home";
    private static final String AUTH_PAGE_ADDRESS = "/authorizationPage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        UserService userService = serviceProvider.getUserService();

        String login = request.getParameter(LOGIN_PARAM);
        char[] password = request.getParameter(PASSWORD_PARAM).toCharArray();

        try {
            AuthorizedUser user = userService.signIn(login, password);
            if (user.getLogin() == null) {
                LOGGER.info("Invalid username or password...");
                request.setAttribute(INVALID_SIGN_IN_ATTR, true);
                request.getRequestDispatcher(AUTH_PAGE_ADDRESS).forward(request, response);
            } else {
                request.getSession().setAttribute(USER_ATTR, user);

                response.sendRedirect(HOME_ADDRESS);
            }
        }  catch (IOException ex) {
            LOGGER.error("Invalid address to forward or redirect in the authorization command..", ex);
        }
    }
}
