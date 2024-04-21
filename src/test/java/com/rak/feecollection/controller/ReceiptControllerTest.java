package com.rak.feecollection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rak.feecollection.model.PaymentRequestDto;
import com.rak.feecollection.model.ReceiptResponseDto;
import com.rak.feecollection.model.StudentDto;
import com.rak.feecollection.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReceiptController.class)
@AutoConfigureMockMvc
 class ReceiptControllerIntegrationTest {

   private static final String RECEIPT_RESOURCE = "/api/fees";
   private static final String PROCESS_FEE = "/payment";
   private static final String RETRIEVE_RECEIPT = "/{studentId}/receipt";
   private static final String GET_ALL_RECEIPT = "/receipts";

    @Autowired
     MockMvc mockMvc;

    @Autowired
     ObjectMapper objectMapper;

    @MockBean
     ReceiptService receiptService;

    @Test
     void testCreateTuitionFee() throws Exception {
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();

        StudentDto studentDetails = new StudentDto();
        studentDetails.setStudentId(1l);

        paymentRequestDto.setStudentDto(studentDetails);

        ReceiptResponseDto receiptResponse = new ReceiptResponseDto();
        receiptResponse.setStatusMessage("SUCCESS");

        when(receiptService.performFeePayment(any(PaymentRequestDto.class))).thenReturn(receiptResponse);

        ResultActions resultActions = mockMvc.perform(post(RECEIPT_RESOURCE + PROCESS_FEE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentRequestDto)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMessage").value("SUCCESS"));
    }

    @Test
     void testFetchReceiptDetail() throws Exception {
        Long studentId = 1L;
        ReceiptResponseDto receiptResponse = new ReceiptResponseDto();
        receiptResponse.setStatusMessage("SUCCESS");

        when(receiptService.getReceiptDetail(anyLong())).thenReturn(receiptResponse);

        ResultActions resultActions = mockMvc.perform(get(RECEIPT_RESOURCE + RETRIEVE_RECEIPT, studentId));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMessage").value("SUCCESS"));
    }

    @Test
     void testGetAllReceipts() throws Exception {
        String schoolName = "ABC School";
        String grade = "10";
        ReceiptResponseDto receiptResponseDto = new ReceiptResponseDto();
        receiptResponseDto.setStatusMessage("SUCCESS");

        when(receiptService.getAllReceipts(eq(schoolName), eq(grade))).thenReturn(receiptResponseDto);

        ResultActions resultActions = mockMvc.perform(get(RECEIPT_RESOURCE + GET_ALL_RECEIPT)
                .param("schoolName", schoolName)
                .param("grade", grade));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMessage").value("SUCCESS"));
    }
}
