package com.rak.feecollection.controller;

import com.rak.feecollection.model.PaymentRequestDto;
import com.rak.feecollection.model.ReceiptResponseDto;
import com.rak.feecollection.service.ReceiptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.rak.feecollection.constant.ReceiptConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
})
@RestController
@RequestMapping(value = RECEIPT_RESOURCE, produces = APPLICATION_JSON_VALUE)
public class ReceiptController {
    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);

    private final ReceiptService receiptService;

    public ReceiptController(final ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Operation(summary = "Make fee payment for student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully processed payment")
    })
    @PostMapping(PROCESS_FEE)
    public ResponseEntity<ReceiptResponseDto> performFeePayment(@RequestBody final PaymentRequestDto paymentRequestDto) {
        logger.info("Perform Fee Payment of Student ID [{}] ", paymentRequestDto.getStudentDto().getStudentId());
        return ResponseEntity.ok(receiptService.performFeePayment(paymentRequestDto));
    }

    @Operation(summary = "Get receipt details for student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched student receipt")
    })
    @GetMapping(RETRIEVE_RECEIPT)
    public ResponseEntity<ReceiptResponseDto> fetchReceiptDetail(@PathVariable final Long studentId) {
        logger.info("Get receipt details of Student ID [{}] ", studentId);
        return ResponseEntity.ok(receiptService.getReceiptDetail(studentId));
    }

    @Operation(summary = "Get all receipt details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrevive details")
    })
    @GetMapping(GET_ALL_RECEIPT)
    public ResponseEntity<ReceiptResponseDto> getAllReceipts(@RequestParam final String schoolName, @RequestParam final String grade) {
        logger.info("Get All receipt details of School :: [{}] nad Grade :: [{}] ", schoolName, grade);
        return ResponseEntity.ok(receiptService.getAllReceipts(schoolName, grade));
    }
}