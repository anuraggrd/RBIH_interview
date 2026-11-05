package com.lending.loan_offer_service.platform.api;

import com.lending.loan_offer_service.platform.dto.ApplicationResponse;
import com.lending.loan_offer_service.platform.dto.LoanApplicationRequest;
import com.lending.loan_offer_service.platform.services.LoanApplicationService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class LoanApplicationController {

    private final LoanApplicationService loanService;

    public LoanApplicationController(LoanApplicationService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse create(
            @Valid @RequestBody LoanApplicationRequest request) {

        return loanService.process(request);
    }
}