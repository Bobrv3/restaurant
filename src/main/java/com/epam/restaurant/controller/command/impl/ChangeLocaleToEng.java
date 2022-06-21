package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLocaleToEng implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        request.getSession(true).setAttribute("local", "en");

        try {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServletException e) {
            // TODO обработать искл
             e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
