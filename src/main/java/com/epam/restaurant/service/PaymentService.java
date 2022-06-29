package com.epam.restaurant.service;

public interface PaymentService {
    int createInvoice(int orderId) throws ServiceException;
}
