package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.bean.AuthorizedUser;
import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.criteria.Criteria;
import com.epam.restaurant.bean.criteria.SearchCriteria;
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

public class EditUser implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditUser.class);
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    private static final String PERSINAL_INFO_PAGE_ADDR = "/personalInfo";

    private static final String EX1 = "Error invalid address to redirect";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        String userName = request.getParameter("userName");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        AuthorizedUser user = (AuthorizedUser) request.getSession().getAttribute("user");
        RegistrationUserData userData = (RegistrationUserData) request.getSession().getAttribute("userData");

        Criteria criteria = new Criteria();
        if (userName != null) {
            criteria.add(SearchCriteria.Users.NAME.name(), userName);
            userData.setName(userName);

            user.setName(userName);
            request.getSession().setAttribute("user", user);
        }
        if (phoneNumber != null) {
            criteria.add(SearchCriteria.Users.PHONE_NUMBER.name(), phoneNumber);
            userData.setPhoneNumber(phoneNumber);
        }
        if (email != null) {
            criteria.add(SearchCriteria.Users.EMAIL.name(), email);
            userData.setEmail(email);
        }
        request.getSession().setAttribute("userData", userData);

        UserService userService = serviceProvider.getUserService();
        userService.updateUser(user.getLogin(), criteria);

        try {
            response.sendRedirect(PERSINAL_INFO_PAGE_ADDR);
        } catch (IOException e) {
            LOGGER.error(EX1, e);
        }
    }
}
