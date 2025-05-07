package com.infoorigin.loanApprovalBackend;

import org.kie.api.KieServices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class LoanApprovalBackendApplication {

	private static final Logger logger = LoggerFactory.getLogger(LoanApprovalBackendApplication.class);
	private static final KieServices kieServices = KieServices.Factory.get();

	public static void main(String[] args) {
		SpringApplication.run(LoanApprovalBackendApplication.class, args);
		logger.info("Loan Approval Backend application started successfully.");
	}
}



