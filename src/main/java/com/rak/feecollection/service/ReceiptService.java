package com.rak.feecollection.service;

import com.rak.feecollection.model.PaymentRequest;
import com.rak.feecollection.model.ReceiptResponse;

public interface ReceiptService {
    ReceiptResponse performFeePayment(final PaymentRequest paymentRequest);

    ReceiptResponse getReceiptDetail(final Long studentDetail);

    ReceiptResponse getAllReceipts(final String schoolName, final String grade);
}
