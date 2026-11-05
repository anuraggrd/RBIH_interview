package com.lending.loan_offer_service.platform.services;

import com.lending.loan_offer_service.platform.constant.LoanPricingConstant;
import com.lending.loan_offer_service.platform.domain.EmploymentType;
import com.lending.loan_offer_service.platform.domain.RiskBand;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class InterestRateCalculator {
    public BigDecimal calculate(RiskBand riskband , EmploymentType empType ,BigDecimal loanamount){
      BigDecimal rate =   LoanPricingConstant.BASE_ANNUAL_RATE
        .add(riskPremium(riskband))
                .add(employmentPremium(empType))
                .add(loanSizePremium(loanamount));

        return rate.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal loanSizePremium(BigDecimal loanamount) {
        if(loanamount.compareTo(LoanPricingConstant.LOAN_SIZE_PREMIUM_THRESHOLD)>0){
            return new BigDecimal("0.50");
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal employmentPremium(EmploymentType empType) {
        if(empType == EmploymentType.SELFEMPLOYED){
            return new BigDecimal("1.00");
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal riskPremium(RiskBand riskband) {
        return switch (riskband){
            case LOW -> BigDecimal.ZERO;
            case MEDIUM -> new BigDecimal("1.50");
            case HEIGH -> new BigDecimal("3.00");
        };
    }
}
