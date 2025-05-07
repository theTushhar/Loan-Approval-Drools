package com.infoorigin.loanApprovalBackend.service;

import com.infoorigin.loanApprovalBackend.model.User;
import com.infoorigin.loanApprovalBackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final LoanService loanService;

    public UserService(UserRepository userRepository, LoanService loanService) {
        this.userRepository = userRepository;
        this.loanService = loanService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        loanService.processLoanEligibility(savedUser);
        logger.info("Created user and triggered loan eligibility processing for user ID: {}", savedUser.getId());
        return savedUser;
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            boolean userUpdated = isUserUpdated(updatedUser, existingUser);

            updateUserFields(existingUser, updatedUser);
            User savedUser = userRepository.save(existingUser);

            if (userUpdated) {
                loanService.processLoanEligibility(savedUser);
                logger.info("User updated, loan eligibility reprocessed for user ID: {}", savedUser.getId());
            }

            return savedUser;
        });
    }

    private void updateUserFields(User existingUser, User updatedUser) {
        existingUser.setName(updatedUser.getName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setMonthlyIncome(updatedUser.getMonthlyIncome());
        existingUser.setCreditScore(updatedUser.getCreditScore());
        existingUser.setExistingLoanAmount(updatedUser.getExistingLoanAmount());
    }

    private boolean isUserUpdated(User newUser, User existingUser) {
        return !safeEquals(newUser.getName(), existingUser.getName())
                || !safeEquals(newUser.getAge(), existingUser.getAge())
                || !safeEquals(newUser.getMonthlyIncome(), existingUser.getMonthlyIncome())
                || !safeEquals(newUser.getCreditScore(), existingUser.getCreditScore())
                || !safeEquals(newUser.getExistingLoanAmount(), existingUser.getExistingLoanAmount());
    }

    private <T> boolean safeEquals(T a, T b) {
        return a == null ? b == null : a.equals(b);
    }
}
