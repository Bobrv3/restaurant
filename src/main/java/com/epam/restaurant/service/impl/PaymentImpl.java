package com.epam.restaurant.service.impl;

import com.epam.restaurant.bean.PaymentMethod;
import com.epam.restaurant.dao.DAOException;
import com.epam.restaurant.dao.DAOProvider;
import com.epam.restaurant.dao.PaymentDAO;
import com.epam.restaurant.service.PaymentService;
import com.epam.restaurant.service.ServiceException;

import java.util.List;

public class PaymentImpl implements PaymentService {
    private static final DAOProvider daoProvider = DAOProvider.getInstance();
    private static final PaymentDAO paymentDAO = daoProvider.getPaymentDAO();

    @Override
    public int createInvoice(int orderId) throws ServiceException {
        try {
            int invoiceId = paymentDAO.createInvoice(orderId);
            return invoiceId;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() throws ServiceException {
        try {
            return paymentDAO.getPaymentMethods();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void createPayment(int invoiceId, int paymentMethodId) throws ServiceException {
        try {
            paymentDAO.createPayment(invoiceId, paymentMethodId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
