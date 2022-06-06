package com.epam.restaurant;

import com.epam.restaurant.bean.RegistrationUserData;
import com.epam.restaurant.bean.User;
import com.epam.restaurant.dao.ConnectionPool;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOFactory;
import com.epam.restaurant.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class BooksGetterServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(BooksGetterServlet.class);
    private final DAOFactory daoFactory = DAOFactory.getInstance();
    // TODO убрать с этого уровня ConnectionPool, подумать как и где его инициализировать в другом месте
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        PrintWriter writer = resp.getWriter();

        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            String login = "test4";
            String pswd = "1";
            String name = "Pavel";
            String phone_number = "375447896931";
            String email = null;
            int roles_id = 2;

//            userDAO.registration(new RegistrationUserData(login, pswd, name, phone_number, email, roles_id));
            User user = userDAO.signIn(login, pswd);
            writer.print(user);
        } catch (DAOException e) {
            e.printStackTrace();
        }

//        try {
//            connectionPool.dispose();
//        } catch (DAOException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void init() throws ServletException {
        try {
            connectionPool.initConnectionPool();
        } catch (DAOException e) {
            // TODO выбросить ошшибку уровня
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            connectionPool.dispose();
        } catch (DAOException e) {
            // TODO выбросить ошшибку уровня
            e.printStackTrace();
        }
    }
}
