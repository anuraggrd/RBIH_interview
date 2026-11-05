package com.lending.loan_offer_service;

import com.lending.loan_offer_service.platform.domain.EmploymentType;
import com.lending.loan_offer_service.platform.domain.Loanpurpose;
import com.lending.loan_offer_service.platform.domain.RejectionReason;
import com.lending.loan_offer_service.platform.dto.ApplicationRequest;
import com.lending.loan_offer_service.platform.dto.LoanRequest;
import com.lending.loan_offer_service.platform.services.EligibilityEvaluator;
import com.lending.loan_offer_service.platform.services.EmiCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EligibilityEvaluatorTest {
    private EligibilityEvaluator evaluator;

    @BeforeEach
    void setUp(){
        evaluator = new EligibilityEvaluator(new EmiCalculator());
    }

    @Test
    void passforHealthyApplication(){
        List<RejectionReason> reasons = evaluator.evaluate(applicant(35,75000,720), loan(500000,36));
        assertTrue(reasons.isEmpty());
    }

    @Test
    void rejectsforMultiplereasons(){
        List<RejectionReason> reasons = evaluator.evaluate(applicant(63,150000,500), loan(200000,36));
        assertTrue(reasons.contains(RejectionReason.CREDIT_SCORE_BELOW_MINIMUM));
        assertTrue(reasons.contains(RejectionReason.AGE_TENURE_LIMIT_EXCEEDED));
    }

    private ApplicationRequest applicant(int age ,int income ,int score){
        ApplicationRequest a = new ApplicationRequest();
        a.setName("Test User");
        a.setAge(age);
        a.setMonthlyIncome( BigDecimal.valueOf(income));
        a.setEmploymentType(EmploymentType.SALARIED);
        a.setCreditScore(score);
        return a;
    }
    private LoanRequest loan(int amount, int tenure){
        LoanRequest l = new LoanRequest();
        l.setAmount(BigDecimal.valueOf(amount));
        l.setPurpose(Loanpurpose.PERSONAL);
        l.setTenureMonth(tenure);
        return l;
    }

}
