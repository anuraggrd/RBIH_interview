package com.lending.loan_offer_service.platform.dto;

import com.lending.loan_offer_service.platform.domain.EmploymentType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ApplicationRequest {
    @NotBlank(message = "Applicant name is required")
    private String name;
    @NotBlank(message = "Applicant name is required")
    @Min(value =21, message = "Age but be greater than eqaul to 21" )
    @Max(value =60, message = "Age but be at most 60" )
    private Integer age;
    @NotNull(message = "monthly income is required")
    @DecimalMin(value ="0.1" ,message= "Mothly income must be greater than 0")
    private BigDecimal monthlyIncome;
    @NotNull(message = "Employment Type is required")
    private EmploymentType employmentType;
    @NotBlank(message = "Applicant name is required")
    @Min(value =300, message = "creditScore must be greater than eqaul to 300" )
    @Min(value =900, message = "creditScore must be at most 900 " )
    private Integer creditScore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
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

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }





}
