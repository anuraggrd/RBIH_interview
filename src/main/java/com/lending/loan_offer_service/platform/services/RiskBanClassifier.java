package com.lending.loan_offer_service.platform.services;

import com.lending.loan_offer_service.platform.domain.RiskBand;

public class RiskBanClassifier {

    public RiskBand classify(int creditScore){
        if(creditScore>=750){
            return RiskBand.HEIGH;
        }
        if(creditScore>=650){
            return RiskBand.MEDIUM;
        }
        if(creditScore>=600){
            return RiskBand.LOW;
        }
        throw  new IllegalArgumentException("Credit score below minimum for risk banding: "+ creditScore);
    }
}
