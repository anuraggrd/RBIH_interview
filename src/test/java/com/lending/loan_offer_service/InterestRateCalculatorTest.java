package com.lending.loan_offer_service;

import com.lending.loan_offer_service.platform.domain.EmploymentType;
import com.lending.loan_offer_service.platform.domain.RiskBand;
import com.lending.loan_offer_service.platform.services.InterestRateCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterestRateCalculatorTest {

    private InterestRateCalculator calculator ;

    @BeforeEach
    void setUp(){
        calculator  = new InterestRateCalculator();
    }
    @Test
    void lowRiskedSalariedsmallloanStayAtBase(){
        BigDecimal rate = calculator.calculate(RiskBand.LOW, EmploymentType.SALARIED,new BigDecimal("500000"));
        assertEquals( new BigDecimal("12.00"),rate);
    }

    @Test
    void stackAllPremiums(){
        BigDecimal rate = calculator.calculate(RiskBand.HEIGH, EmploymentType.SELFEMPLOYED,new BigDecimal("1500000"));
        assertEquals( new BigDecimal("16.50"),rate);
    }


}
