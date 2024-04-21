package com.rak.feecollection.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonInclude(content = JsonInclude.Include.NON_NULL)
@JsonRootName("ReceiptResponse")
@Schema(description = "Receipt Response Dto")
public class ReceiptResponseDto {
    @JsonProperty("receiptDataList")
    @Schema(description = "receipt Dto List")
    private List<ReceiptDto> receiptDtoList;
    @JsonProperty("purchaseDetailsResponse")
    @Schema(description = "Purchase Detail Response")
    private PurchaseDetailsResponseDto purchaseDetailsResponseDto;
    @JsonProperty("statusCode")
    @Schema(description = "status code")
    private int statusCode;
    @JsonProperty("statusMessage")
    @Schema(description = "status message")
    private String statusMessage;

    public List<ReceiptDto> getReceiptDataList() {
        return receiptDtoList;
    }

    public void setReceiptDataList(List<ReceiptDto> receiptDtoList) {
        this.receiptDtoList = receiptDtoList;
    }

    public PurchaseDetailsResponseDto getPurchaseDetailsResponse() {
        return purchaseDetailsResponseDto;
    }

    public void setPurchaseDetailsResponse(PurchaseDetailsResponseDto purchaseDetailsResponseDto) {
        this.purchaseDetailsResponseDto = purchaseDetailsResponseDto;
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
