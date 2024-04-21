package com.rak.feecollection.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payment Request Dto")
public class PaymentRequestDto {
    @Schema(description = "Student Dto")
    private StudentDto studentDto;
    @Schema(description = "Card Dto")
    private CardDto cardDto;

    public StudentDto getStudentDto() {
        return studentDto;
    }

    public void setStudentDto(StudentDto studentDto) {
        this.studentDto = studentDto;
    }

    public CardDto getCardDto() {
        return cardDto;
    }

    public void setCardDto(CardDto cardDto) {
        this.cardDto = cardDto;
    }
}
