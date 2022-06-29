package com.epam.restaurant.dao;

public interface PaymentDAO {
    int createInvoice(int orderId) throws DAOException;
}
