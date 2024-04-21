package com.rak.feecollection.util;

import com.rak.feecollection.model.FeeDto;
import com.rak.feecollection.model.PaymentTransactionDto;
public class CalculateFee {

    private static long lastTimestamp = 0;
    private static long counter = 0;

    public static PaymentTransactionDto feeCalculation(final String schoolName, final String grade) {
        PaymentTransactionDto paymentTransactionDto = new PaymentTransactionDto();
        paymentTransactionDto.setTransactionReference(transactionReference());
        paymentTransactionDto.setTotalFee(FeeDto.getFee(schoolName, grade));
        return paymentTransactionDto;
    }

    public static long transactionReference() {
        return generateUniqueId();
    }

    public static synchronized long generateUniqueId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp != lastTimestamp) {
            lastTimestamp = timestamp;
            counter = 0;
        }
        // 0xFFFF is a hexadecimal literal representing the binary value 1111 1111 1111 1111.
        return (timestamp << 16) | (counter++ & 0xFFFF);
    }
}
