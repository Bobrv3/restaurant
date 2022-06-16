package com.epam.restaurant.controller.command.impl;

import com.epam.restaurant.controller.command.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MoveToRegistrationPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        // TODO проверка на null
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/registrationPage");

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
