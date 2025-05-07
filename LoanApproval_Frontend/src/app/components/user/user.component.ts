import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../models/user';
import { UserService } from '../../service/user.service';
import { LoanService } from '../../service/loan.service';

@Component({
  selector: 'app-user',
  standalone: false,
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  users: User[] = [];
  selectedUser: User | null = null;
  user: User = new User(); // Properly instantiate the User class

  constructor(
    private userService: UserService,
    private loanService: LoanService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe(users => {
      this.users = users;
    });
  }


  viewLoanEligibility(user: User): void {
    if (user.id !== undefined) {
      // Encode user ID using Base64
      const encodedId = btoa(user.id.toString());
      this.router.navigate(['/loan_eligibility'], {
        queryParams: { token: encodedId } // Use 'token' instead of 'user' for obfuscation
      });
    } else {
      console.error('User ID is undefined');
    }
  }

  onSubmit(userForm: any): void {
    if  (userForm.valid) {
      if (this.user.id) {
        this.userService.updateUser(this.user.id, this.user).subscribe(() => {
          this.loadUsers();
          this.resetUserForm(userForm);
        });
      } else {
        this.userService.createUser(this.user).subscribe(() => {
          this.loadUsers();
          this.resetUserForm(userForm);
        });
      }
    }
  }

  resetUserForm(userForm: any): void {
    userForm.reset();
    this.user = new User();
  }

  editUser(user: User): void {
    this.user = { ...user }; // Pre-fill the form with the selected user's data
  }
}
