package com.epam.restaurant.service.impl;

import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.dao.PaymentDAO;
import com.epam.restaurant.service.PaymentService;
import com.epam.restaurant.service.ServiceException;

public class PaymentImpl implements PaymentService {
    private static final DAOProvider daoProvider = DAOProvider.getInstance();

    @Override
    public int createInvoice(int orderId) throws ServiceException {
        PaymentDAO paymentDAO = daoProvider.getPaymentDAO();
        try {
            int invoiceId = paymentDAO.createInvoice(orderId);
            return invoiceId;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
