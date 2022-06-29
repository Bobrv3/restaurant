package com.epam.restaurant.dao;

import com.epam.restaurant.bean.PaymentMethod;

import java.util.List;

public interface PaymentDAO {
    int createInvoice(int orderId) throws DAOException;
    List<PaymentMethod> getPaymentMethods() throws DAOException;
    boolean createPayment(int invoiceId, int paymentMethodId) throws DAOException;
}
