package com.lending.loan_offer_service.platform.constant;



import java.math.BigDecimal;

public class LoanPricingConstant {
    public static final BigDecimal BASE_ANNUAL_RATE = new BigDecimal("12.00");
    public static final BigDecimal LOAN_SIZE_PREMIUM_THRESHOLD = new BigDecimal("1000000");
    public static final Integer MIN_ELIGIBLE_CREDIT_SCORE = 600 ;
    public static final BigDecimal ELIGIBILITY_EMI_INCOME_RATIO = new BigDecimal("0.60");
    public static final long MAX_AGE_PLUSE_TENURE_YEARS = 65 ;
    public static final BigDecimal OFFER_EMI_INCOME_RATIO = new BigDecimal("0.50"); ;
}
