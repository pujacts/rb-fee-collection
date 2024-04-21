package com.rak.feecollection.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payment Transaction Dto")
public class PaymentTransactionDto {
    @Schema(description = "Total fees")
    private double totalFee;
    @Schema(description = "Trsancation Reference")
    private long transactionReference;

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public long getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(long transactionReference) {
        this.transactionReference = transactionReference;
    }
}
