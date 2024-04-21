package com.rak.feecollection.util;

import com.rak.feecollection.entity.Receipt;
import com.rak.feecollection.model.ReceiptDto;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReceiptUtility {
    public static ReceiptDto mapStudentToStudentResponse(Receipt receipt) {
        Assert.notNull(receipt, "Receipt is null");
        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setStudentId(receipt.getStudentId());
        receiptDto.setStudentName(receipt.getStudentName());
        receiptDto.setCardNumber(receipt.getCardNumber());
        receiptDto.setCardType(receipt.getCardType().name());
        receiptDto.setReference(receipt.getTransactionReference());
        receiptDto.setTransactionDateAndTime(receipt.getCollectionDate());
        receiptDto.setTransactionAmount(receipt.getTransactionAmount());
        return receiptDto;
    }

    public static List<ReceiptDto> mapStudentToStudentResponse(List<Receipt> receipts) {
        return Optional.ofNullable(receipts)
                .orElse(Collections.emptyList())
                .stream()
                .map(receipt -> {
                    ReceiptDto receiptDto = new ReceiptDto();
                    receiptDto.setStudentId(receipt.getStudentId());
                    receiptDto.setStudentName(receipt.getStudentName());
                    receiptDto.setCardNumber(receipt.getCardNumber());
                    receiptDto.setCardType(receipt.getCardType().name());
                    receiptDto.setReference(receipt.getTransactionReference());
                    receiptDto.setTransactionDateAndTime(receipt.getCollectionDate());
                    receiptDto.setTransactionAmount(receipt.getTransactionAmount());
                    return receiptDto;
                })
                .collect(Collectors.toList());
    }

    public static double calculateCustomFee(final int gradeCount, final double tuitionFee) {
        return gradeCount * tuitionFee;
    }
}
