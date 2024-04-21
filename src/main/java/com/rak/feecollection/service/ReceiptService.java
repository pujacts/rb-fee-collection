package com.rak.feecollection.service;

import com.rak.feecollection.model.PaymentRequestDto;
import com.rak.feecollection.model.ReceiptResponseDto;

public interface ReceiptService {
    ReceiptResponseDto performFeePayment(final PaymentRequestDto paymentRequestDto);

    ReceiptResponseDto getReceiptDetail(final Long studentDetail);

    ReceiptResponseDto getAllReceipts(final String schoolName, final String grade);
}
