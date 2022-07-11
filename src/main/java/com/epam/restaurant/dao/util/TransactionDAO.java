package com.epam.restaurant.dao.util;

import com.epam.restaurant.dao.DAOException;

import java.sql.Connection;

public interface TransactionDAO {
    void startTransaction() throws DAOException;

    void commit() throws DAOException;

    void rollback() throws DAOException;

    ThreadLocal<Connection> getConnectionHolder();
}
