package com.lending.loan_offer_service.platform.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoanApplicationRequest {
    @NotNull(message = "Application details are required")
    @Valid
    private ApplicationRequest applicant;
    @NotNull(message = "Loan details are required")
    @Valid
    private LoanRequest loan;

    public LoanRequest getLoan() {
        return loan;
    }

    public void setLoan(LoanRequest loan) {
        this.loan = loan;
    }

    public ApplicationRequest getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicationRequest applicant) {
        this.applicant = applicant;
    }


}
