package com.lending.loan_offer_service.platform.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lending.loan_offer_service.platform.domain.ApplicationStatus;
import com.lending.loan_offer_service.platform.domain.RiskBand;

import java.util.List;
import java.util.UUID;

public class ApplicationResponse {
    private UUID applicationId;
    private ApplicationStatus status;
    private RiskBand riskband;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OfferResponse offer;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> rejectionReason;

    public UUID getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(UUID applicationId) {
        this.applicationId = applicationId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public RiskBand getRiskband() {
        return riskband;
    }

    public void setRiskband(RiskBand riskband) {
        this.riskband = riskband;
    }

    public OfferResponse getOffer() {
        return offer;
    }

    public void setOffer(OfferResponse offer) {
        this.offer = offer;
    }

    public List<String> getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(List<String> rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
