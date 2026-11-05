package com.lending.loan_offer_service.platform.services;

import com.lending.loan_offer_service.platform.constant.LoanPricingConstant;
import com.lending.loan_offer_service.platform.domain.RejectionReason;
import com.lending.loan_offer_service.platform.domain.RiskBand;
import com.lending.loan_offer_service.platform.domain.ApplicationStatus;
import com.lending.loan_offer_service.platform.dto.*;
import com.lending.loan_offer_service.platform.enity.LoanApplicationEntity;
import com.lending.loan_offer_service.platform.repository.LoanApplicationRepository;
import com.lending.loan_offer_service.platform.uitility.EligibilityEvaluator;
import com.lending.loan_offer_service.platform.uitility.EmiCalculator;
import com.lending.loan_offer_service.platform.uitility.InterestRateCalculator;
import com.lending.loan_offer_service.platform.uitility.RiskBanClassifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.*;

@Service
public class LoanApplicationService {
    private final EligibilityEvaluator eligibilityEvaluator;
    private final RiskBanClassifier riskBanClassifier;
    private final InterestRateCalculator interestRateCalculator;
    private final EmiCalculator emiCalculator;
    private final LoanApplicationRepository repository;

    public LoanApplicationService(EligibilityEvaluator eligibilityEvaluator, RiskBanClassifier riskBanClassifier, InterestRateCalculator interestRateCalculator, EmiCalculator emiCalculator, LoanApplicationRepository loanApplicationRepository) {
        this.eligibilityEvaluator = eligibilityEvaluator;
        this.riskBanClassifier = riskBanClassifier;
        this.interestRateCalculator = interestRateCalculator;
        this.emiCalculator = emiCalculator;
        this.repository = loanApplicationRepository;
    }

    @Transactional
    public ApplicationResponse process( LoanApplicationRequest request) {
        UUID applicationId = UUID.randomUUID();
        ApplicationRequest applicant = request.getApplicant();
        LoanRequest loan = request.getLoan();

        List<RejectionReason> eligibilityFailures = eligibilityEvaluator.evaluate(applicant, loan);
        if (!eligibilityFailures.isEmpty()) {
            return persistRejected(applicationId, request, eligibilityFailures);
        }

        RiskBand riskBand = riskBanClassifier.classify(applicant.getCreditScore());
        BigDecimal interestRate = interestRateCalculator.calculate(
                riskBand, applicant.getEmploymentType(), loan.getAmount());

        BigDecimal offerEmi = emiCalculator.calculate(loan.getAmount(), interestRate, loan.getTenureMonth());
        BigDecimal maxOfferEmi = applicant.getMonthlyIncome()
                .multiply(LoanPricingConstant.OFFER_EMI_INCOME_RATIO);

        if (offerEmi.compareTo(maxOfferEmi) > 0) {
            return persistRejected(applicationId, request, List.of(RejectionReason.EMI_EXCEEDS_50_PERSENT));
        }

        BigDecimal totalPayable = emiCalculator.totalPayable(offerEmi, loan.getTenureMonth());
        return persistApproved(applicationId, request, riskBand, interestRate, offerEmi, totalPayable);
    }

    private ApplicationResponse persistApproved(UUID id,
                                                LoanApplicationRequest request,
                                                RiskBand riskBand,
                                                BigDecimal interestRate,
                                                BigDecimal emi,
                                                BigDecimal totalPayable) {

        LoanApplicationEntity entity = baseEntity(id, request);
        entity.setStatus(ApplicationStatus.APPROVED);
        entity.setRiskBand(riskBand);
        entity.setInterestRate(interestRate);
        entity.setEmi(emi);
        entity.setTotalpayable(totalPayable);
        repository.save(entity);

        ApplicationResponse response = new ApplicationResponse();
        response.setApplicationId(id);
        response.setStatus(ApplicationStatus.APPROVED);
        response.setRiskband(riskBand);
        response.setOffer(new OfferResponse(interestRate, request.getLoan().getTenureMonth(), emi, totalPayable));
        return response;
    }


    private ApplicationResponse persistRejected(UUID id,
                                                LoanApplicationRequest request,
                                                List<RejectionReason> reasons) {

        LoanApplicationEntity entity = baseEntity(id, request);
        entity.setStatus(ApplicationStatus.REJECTED);
        entity.setRejectionReasons(reasons.stream()
                .map(Enum::name)
                .collect(Collectors.joining(",")));
        repository.save(entity);

        ApplicationResponse response = new ApplicationResponse();
        response.setApplicationId(id);
        response.setStatus(ApplicationStatus.REJECTED);
        response.setRiskband(null);
        response.setRejectionReason(reasons.stream().map(Enum::name).collect(Collectors.toList()));
        return response;
    }

    private LoanApplicationEntity baseEntity(UUID id, LoanApplicationRequest request) {
        ApplicationRequest applicant = request.getApplicant();
        LoanRequest loan = request.getLoan();

        LoanApplicationEntity entity = new LoanApplicationEntity();
        entity.setId(id);
        entity.setApplicantName(applicant.getName());
        entity.setAge(applicant.getAge());
        entity.setMonthlyIncome(applicant.getMonthlyIncome());
        entity.setEmploymentType(applicant.getEmploymentType());
        entity.setCreditScore(applicant.getCreditScore());
        entity.setLoanAmount(loan.getAmount());
        entity.setTenureMonth(loan.getTenureMonth());
        entity.setLoanpurpose(loan.getPurpose());
        entity.setCreatedAt(Instant.now());
        return entity;
    }
}

