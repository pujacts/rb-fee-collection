package com.rak.feecollection.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonInclude(content = JsonInclude.Include.NON_NULL)
@JsonRootName("ReceiptResponse")
public class ReceiptResponse {
    @JsonProperty("receiptDataList")
    private List<ReceiptData> receiptDataList;
    @JsonProperty("purchaseDetailsResponse")
    private PurchaseDetailsResponse purchaseDetailsResponse;
    @JsonProperty("statusCode")
    private int statusCode;
    @JsonProperty("statusMessage")
    private String statusMessage;

    public List<ReceiptData> getReceiptDataList() {
        return receiptDataList;
    }

    public void setReceiptDataList(List<ReceiptData> receiptDataList) {
        this.receiptDataList = receiptDataList;
    }

    public PurchaseDetailsResponse getPurchaseDetailsResponse() {
        return purchaseDetailsResponse;
    }

    public void setPurchaseDetailsResponse(PurchaseDetailsResponse purchaseDetailsResponse) {
        this.purchaseDetailsResponse = purchaseDetailsResponse;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
