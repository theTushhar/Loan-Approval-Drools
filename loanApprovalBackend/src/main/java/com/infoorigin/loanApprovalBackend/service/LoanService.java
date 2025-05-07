package com.infoorigin.loanApprovalBackend.service;

import com.infoorigin.loanApprovalBackend.model.LoanApplication;
import com.infoorigin.loanApprovalBackend.model.User;
import com.infoorigin.loanApprovalBackend.repository.LoanApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    private final LoanApplicationRepository loanRepository;
    private final KieContainer kieContainer;

    public LoanService(LoanApplicationRepository loanRepository, KieContainer kieContainer) {
        this.loanRepository = loanRepository;
        this.kieContainer = kieContainer;
    }

    public List<LoanApplication> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<LoanApplication> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public LoanApplication getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Loan Application not found for User ID: " + userId));
    }

    @Transactional
    public LoanApplication processLoan(LoanApplication loanApplication, User user) {
        loanApplication.setUser(user);

        KieSession kieSession = null;
        try {
            kieSession = kieContainer.newKieSession();
            kieSession.insert(user);
            kieSession.insert(loanApplication);
            kieSession.fireAllRules();
        } catch (Exception e) {
            logger.error("Error processing loan application with rules engine", e);
            throw new RuntimeException("Loan processing failed", e);
        } finally {
            if (kieSession != null) {
                kieSession.dispose();
            }
        }

        return loanRepository.save(loanApplication);
    }

    @Transactional
    public void processLoanEligibility(User user) {
        LoanApplication loanApplication = loanRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    LoanApplication newLoan = new LoanApplication();
                    newLoan.setUser(user);
                    return newLoan;
                });

        processLoan(loanApplication, user);
        logger.info("Loan processed for user ID: {}", user.getId());
    }
}
