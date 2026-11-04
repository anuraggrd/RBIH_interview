package com.lending.loan_offer_service.platform.dto;

import com.lending.loan_offer_service.platform.domain.Loanpurpose;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class LoanRequest {
    @NotNull(message = "Loan amount is required")
    @DecimalMin(value ="10000" ,message= "Loan amount must be greater than 10000")
    @DecimalMax(value ="5000000" ,message= "Loan amount must be less than 5000000")
    private BigDecimal amount;
    @NotNull(message = "tenure is required")
    @Min(value =6, message = "tenure must be at least to 21" )
    @Max(value =360, message = "tenure must be at most 360" )
    private Integer tenureMonth;
    @NotNull(message = "Loan purpose is required")
    private Loanpurpose purpose;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTenureMonth() {
        return tenureMonth;
    }

    public void setTenureMonth(Integer tenureMonth) {
        this.tenureMonth = tenureMonth;
    }

    public Loanpurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Loanpurpose purpose) {
        this.purpose = purpose;
    }


}
