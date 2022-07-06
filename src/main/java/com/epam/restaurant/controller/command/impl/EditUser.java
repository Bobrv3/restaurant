package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;
import com.epam.restaurant.service.UserService;
import com.epam.restaurant.service.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

public class EditUser implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditUser.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String PERSINAL_INFO_PAGE_ADDR = "/personalInfo";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        String userName = request.getParameter("userName");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        AuthorizedUser sessionAuthUser = (AuthorizedUser) request.getSession().getAttribute("user");
        RegistrationUserData sessionUserData = (RegistrationUserData) request.getSession().getAttribute("userData");

        RegistrationUserData newUserData = new RegistrationUserData();
        newUserData.setName(sessionUserData.getName());
        newUserData.setPhoneNumber(sessionUserData.getPhoneNumber());
        newUserData.setEmail(sessionUserData.getEmail());

        try {
            if (userName != null) {
                newUserData.setName(userName);

                sessionAuthUser.setName(userName);
            }
            if (phoneNumber != null) {
                newUserData.setPhoneNumber(phoneNumber);
            }
            if (email != null) {
                newUserData.setEmail(email);
            }

            UserService userService = serviceProvider.getUserService();
            userService.updateUser(sessionAuthUser.getLogin(), newUserData);

            request.getSession().setAttribute("userData", newUserData);

            response.sendRedirect(PERSINAL_INFO_PAGE_ADDR);
        } catch (IOException e) {
            LOGGER.error("Error invalid address to redirect", e);
        } catch (ValidationException e) {
            sessionAuthUser.setName(sessionUserData.getName());

            String errorMsg = e.getMessage();
            try {
                request.getRequestDispatcher(MessageFormat.format("/personalInfo?invalidUpdate=true&errorMsg={0}", errorMsg)).forward(request, response);
            } catch (IOException ex) {
                LOGGER.error("Error invalid address to forward", e);
            }
        }
    }
}
