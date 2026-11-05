package com.lending.loan_offer_service.platform.uitility;

import com.lending.loan_offer_service.platform.constant.LoanPricingConstant;
import com.lending.loan_offer_service.platform.domain.RejectionReason;
import com.lending.loan_offer_service.platform.dto.ApplicationRequest;
import com.lending.loan_offer_service.platform.dto.LoanRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class EligibilityEvaluator {
   private final  EmiCalculator emiCalculator;


    public EligibilityEvaluator(EmiCalculator emiCalculator) {
        this.emiCalculator = emiCalculator;
    }
    public List<RejectionReason> evaluate(ApplicationRequest applicant , LoanRequest loan) {
        List<RejectionReason> reasons = new ArrayList<>();
        if (applicant.getCreditScore() < LoanPricingConstant.MIN_ELIGIBLE_CREDIT_SCORE) {
            reasons.add(RejectionReason.CREDIT_SCORE_BELOW_MINIMUM);
        }
        BigDecimal tenureYears = BigDecimal.valueOf(loan.getTenureMonth())
                .divide(BigDecimal.valueOf(12), 4, RoundingMode.HALF_UP);
        BigDecimal agePlustenure = BigDecimal.valueOf(applicant.getAge()).add(tenureYears);

        if (agePlustenure.compareTo(BigDecimal.valueOf(LoanPricingConstant.MAX_AGE_PLUSE_TENURE_YEARS)) > 0) {
            reasons.add(RejectionReason.AGE_TENURE_LIMIT_EXCEEDED);
        }
        BigDecimal baseEmi = emiCalculator.calculate(
                loan.getAmount(),
                LoanPricingConstant.BASE_ANNUAL_RATE,
                loan.getTenureMonth());
        BigDecimal maxAllowedEmi = applicant.getMonthlyIncome()
                .multiply(LoanPricingConstant.ELIGIBILITY_EMI_INCOME_RATIO);

        if (baseEmi.compareTo(maxAllowedEmi) > 0) {
            reasons.add(RejectionReason.EMI_EXCEEDS_60_PERSENT);
        }
        return reasons;



    }
}
