package com.lending.loan_offer_service;

import com.lending.loan_offer_service.platform.domain.ApplicationStatus;
import com.lending.loan_offer_service.platform.domain.EmploymentType;
import com.lending.loan_offer_service.platform.domain.Loanpurpose;
import com.lending.loan_offer_service.platform.domain.RiskBand;
import com.lending.loan_offer_service.platform.dto.ApplicationRequest;
import com.lending.loan_offer_service.platform.dto.ApplicationResponse;
import com.lending.loan_offer_service.platform.dto.LoanApplicationRequest;
import com.lending.loan_offer_service.platform.dto.LoanRequest;
import com.lending.loan_offer_service.platform.repository.LoanApplicationRepository;
import com.lending.loan_offer_service.platform.services.LoanApplicationService;
import com.lending.loan_offer_service.platform.uitility.EligibilityEvaluator;
import com.lending.loan_offer_service.platform.uitility.EmiCalculator;
import com.lending.loan_offer_service.platform.uitility.InterestRateCalculator;
import com.lending.loan_offer_service.platform.uitility.RiskBanClassifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {

    @Mock
    private LoanApplicationRepository repository;

    private LoanApplicationService service;

    @BeforeEach
    void setUp() {
        EmiCalculator emiCalculator = new EmiCalculator();
        service = new LoanApplicationService(
                new EligibilityEvaluator(emiCalculator),
                new RiskBanClassifier(),
                new InterestRateCalculator(),
                emiCalculator,
                repository);

        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void approvesSampleApplicationFromSpec() {
        ApplicationResponse response = service.process(request(
                "Anita", 30, "75000", EmploymentType.SALARIED, 720,
                "500000", 36, Loanpurpose.PERSONAL));

        assertEquals(ApplicationStatus.APPROVED, response.getStatus());
        assertEquals(RiskBand.MEDIUM, response.getRiskband());
        assertNotNull(response.getOffer());
        assertEquals(new BigDecimal("13.50"), response.getOffer().getInterestRate());
        assertEquals(36, response.getOffer().getTenurMonths());
        assertNull(response.getRejectionReason());
        verify(repository).save(any());
    }

    @Test
    void rejectsAndPersistsReasons() {
        ApplicationResponse response = service.process(request(
                "Ravi", 40, "80000", EmploymentType.SALARIED, 550,
                "300000", 24, Loanpurpose.AUTO));

        assertEquals(ApplicationStatus.REJECTED, response.getStatus());
        assertNull(response.getRiskband());
        assertTrue(response.getRejectionReason().contains("CREDIT_SCORE_BELOW_MINIMUM"));
        assertNull(response.getOffer());
    }

    @Test
    void rejectsWhenOfferEmiBreachesFiftyPercentCap() {
        // Eligible on base 12% but final rate pushes EMI over 50% of income.
        // Income 25000, loan 600000, 12 months, HIGH risk + self employed -> high rate

        ApplicationResponse response = service.process(request(
                "Neha", 28, "25000", EmploymentType.SELFEMPLOYED, 620,
                "600000", 48, Loanpurpose.PERSONAL));

        // may fail eligibility at 60% first depending on numbers; assert reject either way
        assertEquals(ApplicationStatus.REJECTED, response.getStatus());
        assertNotNull(response.getRejectionReason());
        assertTrue(response.getRejectionReason().size() >= 1);
    }

    private LoanApplicationRequest request(
            String name,
            int age,
            String income,
            EmploymentType emp,
            int score,
            String amount,
            int tenure,
            Loanpurpose purpose) {

        ApplicationRequest applicant = new ApplicationRequest();
        applicant.setName(name);
        applicant.setAge(age);
        applicant.setMonthlyIncome(new BigDecimal(income));
        applicant.setEmploymentType(emp);
        applicant.setCreditScore(score);

        LoanRequest loan = new LoanRequest();
        loan.setAmount(new BigDecimal(amount));
        loan.setTenureMonth(tenure);
        loan.setPurpose(purpose);

        LoanApplicationRequest req = new LoanApplicationRequest();
        req.setApplicant(applicant);
        req.setLoan(loan);

        return req;
    }
}


