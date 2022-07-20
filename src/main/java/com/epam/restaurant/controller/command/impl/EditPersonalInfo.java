package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.ServiceException;
import com.epam.restaurant.service.ServiceProvider;
import com.epam.restaurant.service.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditPersonalInfo implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditPersonalInfo.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String USER_NAME_PARAM = "userName";
    private static final String PHONE_NUMBER_PARAM = "phoneNumber";
    private static final String EMAIL_PARAM = "email";
    private static final String USER_ATTR = "user";
    private static final String USER_DATA_ATTR = "userData";
    private static final int FOUND_USER = 0;

    private static final String PERSINAL_INFO_PAGE_ADDR = "/personalInfo";
    private static final String VALIDATION_ERROR_PAGE_ADDR = "/personalInfo?invalidUpdate=true&errorMsg=%s";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        String userName = request.getParameter(USER_NAME_PARAM);
        String phoneNumber = request.getParameter(PHONE_NUMBER_PARAM);
        String email = request.getParameter(EMAIL_PARAM);
        AuthorizedUser sessionAuthUser = (AuthorizedUser) request.getSession().getAttribute(USER_ATTR);
        RegistrationUserData sessionUserData = (RegistrationUserData) request.getSession().getAttribute(USER_DATA_ATTR);

        try {
            if (userName != null) {
                sessionAuthUser.setName(userName);
                sessionUserData.setName(userName);
            }
            if (phoneNumber != null) {
                sessionUserData.setPhoneNumber(phoneNumber);
            }
            if (email != null) {
                sessionUserData.setEmail(email);
            }

            serviceProvider.getUserService().updateUser(sessionAuthUser.getLogin(), sessionUserData);

            response.sendRedirect(PERSINAL_INFO_PAGE_ADDR);
        } catch (IOException e) {
            LOGGER.error("Error invalid address to redirect", e);
            throw new ServletException(e);
        } catch (ValidationException e) {
            Criteria criteria = new Criteria();
            criteria.add(SearchCriteria.Users.LOGIN.name(), sessionAuthUser.getLogin());
            // rollback changes in session user data
            sessionUserData = serviceProvider.getUserService().find(criteria).get(FOUND_USER);
            request.getSession().setAttribute(USER_DATA_ATTR, sessionUserData);

            String errorMsg = e.getMessage();
            try {
                request.getRequestDispatcher(String.format(VALIDATION_ERROR_PAGE_ADDR, errorMsg)).forward(request, response);
            } catch (IOException ex) {
                LOGGER.error("Error invalid address to forward", e);
                throw new ServletException(ex);
            }
        }
    }
}
