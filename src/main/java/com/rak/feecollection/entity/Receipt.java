package com.rak.feecollection.entity;

import com.rak.feecollection.enums.CardType;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "receipt")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id", nullable = false)
    private Long receiptId;
    @Column(name = "student_name", nullable = false)
    private String studentName;
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "txn_reference", nullable = false)
    private Long transactionReference;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "card_type_id", nullable = false)
    private int cardTypeId;

    @Transient
    private CardType cardType;

    @Column(name = "txn_amount", nullable = false)
    private double transactionAmount;
    @Column(name = "collection_date", nullable = false)
    private OffsetDateTime collectionDate;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School schoolDetail;

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(Long transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public CardType getCardType() {
        return CardType.getById(cardTypeId);
    }
    public void setCardType(String cardType) {
        this.cardType = CardType.valueOf(cardType.toUpperCase());
        this.cardTypeId = this.cardType.getId();
    }

    public OffsetDateTime getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(OffsetDateTime collectionDate) {
        this.collectionDate = collectionDate;
    }

    public School getSchoolDetail() {
        return schoolDetail;
    }

    public void setSchoolDetail(School schoolDetail) {
        this.schoolDetail = schoolDetail;
    }
}
