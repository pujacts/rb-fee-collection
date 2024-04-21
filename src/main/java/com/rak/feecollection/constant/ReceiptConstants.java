package com.rak.feecollection.constant;

public interface ReceiptConstants {
    int SERVER_ERROR = 500;
    int SUCCESS_CODE = 200;
    int BAD_REQUEST = 400;
    int NOT_FOUND = 404;
    String SUCCESS = "SUCCESS";
    String NOT_FOUND_MSG = "Not Found";
    String CURRENCY_AED = "AED";

    String RECEIPT_RESOURCE = "/api/fees";
    String PROCESS_FEE = "/payment";
    String RETRIEVE_RECEIPT = "/{studentId}/receipt";
    String GET_ALL_RECEIPT = "/receipts";
}
