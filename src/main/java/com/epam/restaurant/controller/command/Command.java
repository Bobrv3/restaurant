package com.epam.restaurant.controller.command;

import com.epam.restaurant.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException;
}
