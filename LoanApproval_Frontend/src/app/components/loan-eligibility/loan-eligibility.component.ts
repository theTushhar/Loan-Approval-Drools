import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { LoanApplication } from '../../models/loan';
import { LoanService } from '../../service/loan.service';

@Component({
  selector: 'app-loan-eligibility',
  standalone: false,
  templateUrl: './loan-eligibility.component.html',
  styleUrls: ['./loan-eligibility.component.css']
})
export class LoanEligibilityComponent implements OnInit {
  loanDetails: LoanApplication | null = null;

  constructor(
    private route: ActivatedRoute,
    private loanService: LoanService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Get the encoded user ID from query parameters
    this.route.queryParams.subscribe((params) => {
      const encodedId = params['token']; // Use 'token' instead of 'user'
      if (encodedId) {
        try {
          // Decode the user ID
          const userId = parseInt(atob(encodedId), 10);
          // Fetch loan eligibility data using the user ID
          this.loanService.getLoanByUserId(userId).subscribe((loan) => {
            this.loanDetails = loan;
          });
        } catch (e) {
          console.error('Invalid token:', e);
        }
      } else {
        console.error('Token not found in query parameters.');
      }
    });
  }
  

  goBack(): void {
    // Navigate to the previous page (or a specific route)
    this.router.navigate(['/user']); // Adjust this route as needed
  }
}