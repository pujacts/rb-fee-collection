package com.rak.feecollection.controller;

import com.rak.feecollection.model.PaymentRequest;
import com.rak.feecollection.model.ReceiptResponse;
import com.rak.feecollection.service.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {
    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
    private final ReceiptService receiptService;

    public ReceiptController(final ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/payment")
    public ResponseEntity<ReceiptResponse> performFeePayment(@RequestBody final PaymentRequest paymentRequest) {
        logger.info("Perform Fee Payment of Student ID [{}] ", paymentRequest.getStudentDetails().getStudentId());
        return ResponseEntity.ok(receiptService.performFeePayment(paymentRequest));
    }

    @GetMapping("/detail/{studentId}")
    public ResponseEntity<ReceiptResponse> fetchReceiptDetail(@PathVariable final Long studentId) {
        logger.info("Get receipt details of Student ID [{}] ", studentId);
        return ResponseEntity.ok(receiptService.getReceiptDetail(studentId));
    }

    @GetMapping("/all/receipt")
    public ResponseEntity<ReceiptResponse> getAllReceipts(@RequestParam final String schoolName, @RequestParam final String grade) {
        logger.info("Get All receipt details of School :: [{}] nad Grade :: [{}] ", schoolName, grade);
        return ResponseEntity.ok(receiptService.getAllReceipts(schoolName, grade));
    }
}