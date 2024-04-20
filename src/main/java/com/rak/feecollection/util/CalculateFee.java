package com.rak.feecollection.util;

import com.rak.feecollection.model.FeeDetail;
import com.rak.feecollection.model.PaymentTransactionDetail;
public class CalculateFee {

    private static long lastTimestamp = 0;
    private static long counter = 0;

    public static PaymentTransactionDetail feeCalculation(final String schoolName, final String grade) {
        PaymentTransactionDetail paymentTransactionDetail = new PaymentTransactionDetail();
        paymentTransactionDetail.setTransactionReference(transactionReference());
        paymentTransactionDetail.setTotalFee(FeeDetail.getFee(schoolName, grade));
        return paymentTransactionDetail;
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
