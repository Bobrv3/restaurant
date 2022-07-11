package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeLocale implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ChangeLocale.class);

    private static final String LOCAL = "local";
    private static final String LAST_PAGE_ATTR = "lastPage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        HttpSession session = request.getSession(true);
        session.setAttribute(LOCAL, request.getParameter(LOCAL));

        String lastPage = (String) session.getAttribute(LAST_PAGE_ATTR);
        try {
            request.getRequestDispatcher(lastPage).forward(request, response);
        } catch (IOException ex) {
            LOGGER.error("Invalid address to forward or redirect in the authorization command..", ex);
        }

    }
}
