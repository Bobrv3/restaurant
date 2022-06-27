package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;

public class ChangeLocale implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession(true);
        session.setAttribute("local", (String) request.getParameter("locale"));

        String lastPage = (String) session.getAttribute("lastPage");
        try {
            request.getRequestDispatcher(MessageFormat.format("{0}", lastPage)).forward(request, response);
        } catch (ServletException e) {
            // TODO обработать
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
