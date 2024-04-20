package com.rak.feecollection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rak.feecollection.model.PaymentRequest;
import com.rak.feecollection.model.ReceiptResponse;
import com.rak.feecollection.model.StudentDetail;
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

    @Autowired
     MockMvc mockMvc;

    @Autowired
     ObjectMapper objectMapper;

    @MockBean
     ReceiptService receiptService;

    @Test
     void testCreateTuitionFee() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();

        StudentDetail studentDetails = new StudentDetail();
        studentDetails.setStudentId(1l);

        paymentRequest.setStudentDetails(studentDetails);

        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setStatusMessage("SUCCESS");

        when(receiptService.performFeePayment(any(PaymentRequest.class))).thenReturn(receiptResponse);

        ResultActions resultActions = mockMvc.perform(post("/api/receipts/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentRequest)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMessage").value("SUCCESS"));
    }

    @Test
     void testFetchReceiptDetail() throws Exception {
        Long studentId = 1L;
        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setStatusMessage("SUCCESS");

        when(receiptService.getReceiptDetail(anyLong())).thenReturn(receiptResponse);

        ResultActions resultActions = mockMvc.perform(get("/api/receipts/detail/{studentId}", studentId));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMessage").value("SUCCESS"));
    }

    @Test
     void testGetAllReceipts() throws Exception {
        String schoolName = "ABC School";
        String grade = "10";
        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setStatusMessage("SUCCESS");

        when(receiptService.getAllReceipts(eq(schoolName), eq(grade))).thenReturn(receiptResponse);

        ResultActions resultActions = mockMvc.perform(get("/api/receipts/all/receipt")
                .param("schoolName", schoolName)
                .param("grade", grade));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMessage").value("SUCCESS"));
    }
}
