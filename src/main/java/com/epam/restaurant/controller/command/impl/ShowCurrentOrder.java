package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.controller.command.Command;
import com.epam.restaurant.service.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowCurrentOrder implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/showCurrentOrder");

        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            // TODO logger и вывод ошибки пользователю
            e.printStackTrace();
        } catch (IOException e) {
            // TODO logger и вывод ошибки пользователю
            e.printStackTrace();
        }
    }
}
