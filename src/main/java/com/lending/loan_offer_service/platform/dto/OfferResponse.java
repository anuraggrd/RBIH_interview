package com.lending.loan_offer_service.platform.dto;

import java.math.BigDecimal;

public class OfferResponse {
    private BigDecimal interestRate;
    private BigDecimal tenurMonths;
    private BigDecimal emi;
    private Integer totalPayable;

    public OfferResponse() {
    }

    public OfferResponse(BigDecimal interestRate, BigDecimal tenurMonths, BigDecimal emi, Integer totalPayable) {
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

    public BigDecimal getTenurMonths() {
        return tenurMonths;
    }

    public void setTenurMonths(BigDecimal tenurMonths) {
        this.tenurMonths = tenurMonths;
    }

    public BigDecimal getEmi() {
        return emi;
    }

    public void setEmi(BigDecimal emi) {
        this.emi = emi;
    }

    public Integer getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(Integer totalPayable) {
        this.totalPayable = totalPayable;
    }


}
