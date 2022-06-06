package com.epam.restaurant;

import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOFactory;
import com.epam.restaurant.dao.UserDAO;
import com.epam.restaurant.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class BooksGetterServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(BooksGetterServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        PrintWriter writer = resp.getWriter();

        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            User user = userDAO.signIn("ant9", "1");
            writer.print(user);
        } catch (DAOException e) {
            e.printStackTrace();
        }

//        try {
//            connectionPool.closeConnections();
//        } catch (DAOException e) {
//            e.printStackTrace();
//        }
    }
}
