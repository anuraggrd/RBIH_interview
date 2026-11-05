package com.lending.loan_offer_service;

import com.lending.loan_offer_service.platform.services.EmiCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmiCalculatorTest {
    private EmiCalculator calculator;

    @BeforeEach
    void setup(){
        calculator = new EmiCalculator();
    }
    @Test
    void calculatesEmiforKnownInputs(){
        BigDecimal emi = calculator.calcuate(new BigDecimal("500000"), new BigDecimal("12.00"),36);
        assertEquals(new BigDecimal("16607.15"), emi);
    }
    @Test
    void totalPayableIsEmiTimesTenure(){
        BigDecimal emi = new BigDecimal("1000.00");
        assertEquals(new BigDecimal("36000.00"),calculator.totalPayable(emi,36));
    }
}
