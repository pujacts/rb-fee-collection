package com.rak.feecollection.service.impl;

import com.rak.feecollection.entity.Receipt;
import com.rak.feecollection.entity.School;
import com.rak.feecollection.model.*;
import com.rak.feecollection.repository.ReceiptRepository;
import com.rak.feecollection.repository.SchoolDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ReceiptServiceImplTest {

    ReceiptServiceImpl receiptService;

    @Mock
    ReceiptRepository receiptRepository;

    @Mock
    SchoolDetailRepository schoolDetailRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        receiptService = new ReceiptServiceImpl(receiptRepository, schoolDetailRepository);
    }

    @Test
    void testCreateTuitionFee() {
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setStudentDto(new StudentDto());
        paymentRequestDto.getStudentDto().setSchoolDto(new SchoolDto());
        paymentRequestDto.getStudentDto().getSchoolDto().setSchoolName("School");
        paymentRequestDto.getStudentDto().getSchoolDto().setGrade("Grade");

        paymentRequestDto.setCardDto(new CardDto());
        paymentRequestDto.getCardDto().setCardNumber("1234567890");
        paymentRequestDto.getCardDto().setCardType("Visa");
        paymentRequestDto.getCardDto().setTransactionAmount(BigDecimal.valueOf(10000l));

        School schoolDetail = new School();
        schoolDetail.setSchoolName("School");
        schoolDetail.setGrade("Grade");

        PaymentTransactionDto paymentTransactionDto = new PaymentTransactionDto();
        paymentTransactionDto.setTotalFee(100.0);
        paymentTransactionDto.setTransactionReference(12345l);

        when(schoolDetailRepository.findBySchoolName("School")).thenReturn(schoolDetail);
        when(receiptRepository.saveAll(any())).thenReturn(new ArrayList<>());

        ReceiptResponseDto receiptResponse = receiptService.performFeePayment(paymentRequestDto);

        assertEquals("SUCCESS", receiptResponse.getStatusMessage());
    }

    @Test
    void testGetReceiptDetail() {
        Long studentId = 1L;

        List<Receipt> receipts = new ArrayList<>();
        Receipt receipt = new Receipt();
        receipt.setStudentId(studentId);
        receipt.setTransactionAmount(100.0);
        receipt.setCollectionDate(OffsetDateTime.now());
        receipt.setCardType("VISA");
        receipts.add(receipt);

        when(receiptRepository.findByStudentId(studentId)).thenReturn(receipts);

        ReceiptResponseDto receiptResponse = receiptService.getReceiptDetail(studentId);

        assertEquals("SUCCESS", receiptResponse.getStatusMessage());
    }

    @Test
    void testGetAllReceipts() {
        String schoolName = "School";
        String grade = "Grade";

        List<Receipt> receipts = new ArrayList<>();
        Receipt receipt = new Receipt();
        receipt.setStudentId(1L);
        receipt.setTransactionAmount(100.0);
        receipt.setCollectionDate(OffsetDateTime.now());
        receipt.setCardType("VISA");
        receipts.add(receipt);

        when(receiptRepository.findAllBySchoolDetail_SchoolNameAndSchoolDetail_Grade(schoolName, grade)).thenReturn(receipts);

        ReceiptResponseDto receiptResponse = receiptService.getAllReceipts(schoolName, grade);

        assertEquals("SUCCESS", receiptResponse.getStatusMessage());
    }
}
