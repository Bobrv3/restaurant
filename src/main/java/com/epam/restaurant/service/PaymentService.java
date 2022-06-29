package com.epam.restaurant.service;

import com.epam.restaurant.bean.PaymentMethod;

import java.util.List;

public interface PaymentService {
    int createInvoice(int orderId) throws ServiceException;
    List<PaymentMethod> getPaymentMethods() throws ServiceException;
    void createPayment(int invoiceId, int paymentMethodId) throws ServiceException;
}
