package com.lending.loan_offer_service;

import com.lending.loan_offer_service.platform.domain.RiskBand;
import com.lending.loan_offer_service.platform.uitility.RiskBanClassifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RiskBandClassifierTest {

    private RiskBanClassifier classifier;

    @BeforeEach
    void setUp(){
        classifier = new RiskBanClassifier();
    }

    @ParameterizedTest
    @CsvSource({
            "900, LOW",
            "750, LOW",
            "749, MEDIUM",
            "650, MEDIUM",
            "649, HEIGH",
            "600, HEIGH"
    })
    void classifierBoundaries(int score, RiskBand expected){
        assertEquals(expected,classifier.classify(score));
    }
    @Test
    void throwWhenBelowEligibleRange(){
        assertThrows(IllegalArgumentException.class, () -> classifier.classify(599));
    }

}
