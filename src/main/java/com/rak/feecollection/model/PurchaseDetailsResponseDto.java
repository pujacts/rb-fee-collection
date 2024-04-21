package com.rak.feecollection.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Purchase Detail Response Dto")
public class PurchaseDetailsResponseDto {

    @JsonProperty("tuitionFee")
    @Schema(description = "tuition Fee")
    private Double tuitionFee;
    @JsonProperty("grade")
    @Schema(description = "grade")
    private String grade;
    @JsonProperty("customAmount")
    @Schema(description = "custom Amount")
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
