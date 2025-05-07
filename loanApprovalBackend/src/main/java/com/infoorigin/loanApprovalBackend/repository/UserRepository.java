package com.infoorigin.loanApprovalBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoorigin.loanApprovalBackend.model.*;

public interface UserRepository extends JpaRepository<User, Long> {}
