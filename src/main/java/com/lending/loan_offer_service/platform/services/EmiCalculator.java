package com.lending.loan_offer_service.platform.services;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * standard reducing - balance EMI.
 * EMI= p * r *(1+r)^n/((1+r)^n -1
 */

@Component
public class EmiCalculator {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;
    private static final MathContext MATH_CTX = new MathContext(16,RoundingMode.HALF_UP);

    public BigDecimal calcuate(BigDecimal principal, BigDecimal annualRatePercent, int tenureMonths){
        if(tenureMonths<0){
            throw new IllegalArgumentException("TenureMonth must be possitive");
        }
        if(principal.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalArgumentException("Pricipal must be possitive");
        }
        BigDecimal monthlyRate = annualRatePercent.divide(BigDecimal.valueOf(100) , MATH_CTX)
                .divide(BigDecimal.valueOf(12) , MATH_CTX);

        if(monthlyRate.compareTo(BigDecimal.ZERO) ==0){
            principal.divide(BigDecimal.valueOf(tenureMonths),SCALE,ROUNDING);
        }

        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyRate,MATH_CTX);
        BigDecimal power = onePlusR.pow(tenureMonths,MATH_CTX);
        BigDecimal numerator = principal.multiply(monthlyRate,MATH_CTX).multiply(power,MATH_CTX);
        BigDecimal denomintor = power.subtract(BigDecimal.ONE,MATH_CTX);
        return numerator.divide(denomintor,SCALE,ROUNDING);
    }

    public BigDecimal totalPayable(BigDecimal emi , int tenureMonths){
        return emi.multiply(BigDecimal.valueOf(tenureMonths)).setScale(SCALE,ROUNDING);

    }


}
