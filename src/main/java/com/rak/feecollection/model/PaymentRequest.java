package com.rak.feecollection.model;

public class PaymentRequest {
    private StudentDetail studentDetails;
    private CardDetails cardDetails;

    public StudentDetail getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(StudentDetail studentDetails) {
        this.studentDetails = studentDetails;
    }

    public CardDetails getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(CardDetails cardDetails) {
        this.cardDetails = cardDetails;
    }
}
