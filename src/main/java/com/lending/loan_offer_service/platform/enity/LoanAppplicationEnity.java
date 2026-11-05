package com.lending.loan_offer_service.platform.enity;

import com.lending.loan_offer_service.platform.domain.*;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;

import java.math.BigDecimal;
import java.time.Instant;

public class LoanAppplicationEnity {
    private String applicationName;
    private Integer age;
    private BigDecimal monthlyIncome;
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;
    private Integer creditScore;
    private BigDecimal loanAmount;
    private Integer tenureMonth;
    @Enumerated(EnumType.STRING)
    private Loanpurpose loanpurpose;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus staus;

    @Enumerated(EnumType.STRING)
    private RiskBand riskBand;

    private BigDecimal interestRate;
    private BigDecimal emi;
    private BigDecimal totalpayable;

    @Lob
    @Column(length =  20000)
    private String rejectionReasons;
    private Instant createdAt;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getTenureMonth() {
        return tenureMonth;
    }

    public void setTenureMonth(Integer tenureMonth) {
        this.tenureMonth = tenureMonth;
    }

    public Loanpurpose getLoanpurpose() {
        return loanpurpose;
    }

    public void setLoanpurpose(Loanpurpose loanpurpose) {
        this.loanpurpose = loanpurpose;
    }

    public ApplicationStatus getStaus() {
        return staus;
    }

    public void setStaus(ApplicationStatus staus) {
        this.staus = staus;
    }

    public RiskBand getRiskBand() {
        return riskBand;
    }

    public void setRiskBand(RiskBand riskBand) {
        this.riskBand = riskBand;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getEmi() {
        return emi;
    }

    public void setEmi(BigDecimal emi) {
        this.emi = emi;
    }

    public BigDecimal getTotalpayable() {
        return totalpayable;
    }

    public void setTotalpayable(BigDecimal totalpayable) {
        this.totalpayable = totalpayable;
    }

    public String getRejectionReasons() {
        return rejectionReasons;
    }

    public void setRejectionReasons(String rejectionReasons) {
        this.rejectionReasons = rejectionReasons;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
