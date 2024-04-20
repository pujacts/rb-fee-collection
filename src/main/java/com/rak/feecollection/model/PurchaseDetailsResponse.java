package com.rak.feecollection.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseDetailsResponse {

    @JsonProperty("tuitionFee")
    private Double tuitionFee;
    @JsonProperty("grade")
    private String grade;
    @JsonProperty("customAmount")
    private String customAmount;

    public Double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(Double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCustomAmount() {
        return customAmount;
    }

    public void setCustomAmount(String customAmount) {
        this.customAmount = customAmount;
    }
}
