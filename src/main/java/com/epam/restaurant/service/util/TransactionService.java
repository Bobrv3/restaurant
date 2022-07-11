package com.epam.restaurant.service.util;

import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.service.ServiceException;

public interface TransactionService {
    void startTransaction() throws ServiceException;

    void commit() throws ServiceException;

    void rollback() throws ServiceException;
}
