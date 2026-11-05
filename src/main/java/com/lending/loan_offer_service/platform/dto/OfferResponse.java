package com.lending.loan_offer_service.platform.dto;

import java.math.BigDecimal;

public class OfferResponse {
    private BigDecimal interestRate;
    private Integer tenurMonths;
    private BigDecimal emi;
    private BigDecimal totalPayable;

    public OfferResponse() {
    }

    public OfferResponse(BigDecimal interestRate, Integer tenurMonths, BigDecimal emi, BigDecimal totalPayable) {
        this.interestRate = interestRate;
        this.tenurMonths = tenurMonths;
        this.emi = emi;
        this.totalPayable = totalPayable;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getTenurMonths() {
        return tenurMonths;
    }

    public void setTenurMonths(Integer tenurMonths) {
        this.tenurMonths = tenurMonths;
    }

    public BigDecimal getEmi() {
        return emi;
    }

    public void setEmi(BigDecimal emi) {
        this.emi = emi;
    }

    public BigDecimal getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(BigDecimal totalPayable) {
        this.totalPayable = totalPayable;
    }


}
