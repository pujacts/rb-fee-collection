package com.rak.feecollection.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.time.OffsetDateTime;

@JsonRootName("receiptData")
public class ReceiptData {
    @JsonProperty("Date & Time")
    private OffsetDateTime transactionDateAndTime;

    @JsonProperty("Student Name")
    private String studentName;

    @JsonProperty("Student Id")
    private Long studentId;

    @JsonProperty("Reference #")
    private Long reference;

    @JsonProperty("Card #")
    private String cardNumber;

    @JsonProperty("Card Type")
    private String cardType;
    private double transactionAmount;

    public OffsetDateTime getTransactionDateAndTime() {
        return transactionDateAndTime;
    }

    public void setTransactionDateAndTime(OffsetDateTime transactionDateAndTime) {
        this.transactionDateAndTime = transactionDateAndTime;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
