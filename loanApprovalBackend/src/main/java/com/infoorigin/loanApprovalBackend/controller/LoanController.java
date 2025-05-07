package com.infoorigin.loanApprovalBackend.controller;

import com.infoorigin.loanApprovalBackend.model.*;
import com.infoorigin.loanApprovalBackend.service.LoanService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/loans")
public class LoanController {

	@Autowired
	private LoanService loanService; // New service class

	@GetMapping
	public ResponseEntity<List<LoanApplication>> getAllLoans() {
		return ResponseEntity.ok(loanService.getAllLoans());
	}

	@GetMapping("/{id}")
	public ResponseEntity<LoanApplication> getLoanById(@PathVariable Long id) {
		return loanService.getLoanById(id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

//	@GetMapping("/user/{userId}")
//	public ResponseEntity<LoanApplication> getLoansByUserId(@PathVariable Long userId) {
//		return loanService.getLoansByUserId(userId).map(ResponseEntity::ok)
//				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//	}
	
	  @GetMapping("/user/{userId}")
	    public ResponseEntity<LoanApplication> getLoanApplicationByUserId(@PathVariable Long userId) {
	        try {
	            LoanApplication loanApplication = loanService.getLoansByUserId(userId);
	            return ResponseEntity.ok(loanApplication);
	        } catch (EntityNotFoundException ex) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }

	@PostMapping
	public ResponseEntity<LoanApplication> applyLoan(@RequestBody LoanApplication loanApplication) {
		LoanApplication processedLoan = loanService.processLoan(loanApplication, loanApplication.getUser());
		return ResponseEntity.status(HttpStatus.CREATED).body(processedLoan);
	}
}
