package com.rak.feecollection.util;

import com.rak.feecollection.entity.Receipt;
import com.rak.feecollection.model.ReceiptData;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReceiptUtility {
    public static ReceiptData mapStudentToStudentResponse(Receipt receipt) {
        Assert.notNull(receipt, "Receipt is null");
        ReceiptData receiptData = new ReceiptData();
        receiptData.setStudentId(receipt.getStudentId());
        receiptData.setStudentName(receipt.getStudentName());
        receiptData.setCardNumber(receipt.getCardNumber());
        receiptData.setCardType(receipt.getCardType().name());
        receiptData.setReference(receipt.getTransactionReference());
        receiptData.setTransactionDateAndTime(receipt.getCollectionDate());
        receiptData.setTransactionAmount(receipt.getTransactionAmount());
        return receiptData;
    }

    public static List<ReceiptData> mapStudentToStudentResponse(List<Receipt> receipts) {
        return Optional.ofNullable(receipts)
                .orElse(Collections.emptyList())
                .stream()
                .map(receipt -> {
                    ReceiptData receiptData = new ReceiptData();
                    receiptData.setStudentId(receipt.getStudentId());
                    receiptData.setStudentName(receipt.getStudentName());
                    receiptData.setCardNumber(receipt.getCardNumber());
                    receiptData.setCardType(receipt.getCardType().name());
                    receiptData.setReference(receipt.getTransactionReference());
                    receiptData.setTransactionDateAndTime(receipt.getCollectionDate());
                    receiptData.setTransactionAmount(receipt.getTransactionAmount());
                    return receiptData;
                })
                .collect(Collectors.toList());
    }

    public static double calculateCustomFee(final int gradeCount, final double tuitionFee) {
        return gradeCount * tuitionFee;
    }
}
