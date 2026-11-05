package com.lending.loan_offer_service.platform.repository;

import com.lending.loan_offer_service.platform.enity.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, UUID> {
}
