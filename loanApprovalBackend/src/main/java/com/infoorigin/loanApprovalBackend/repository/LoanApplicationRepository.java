package com.infoorigin.loanApprovalBackend.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoorigin.loanApprovalBackend.model.*;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

	//LoanApplication findByUserId(Long userId);
	Optional<LoanApplication> findByUserId(Long userId);

}