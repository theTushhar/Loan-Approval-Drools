import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './components/user/user.component';
import { LoanEligibilityComponent } from './components/loan-eligibility/loan-eligibility.component';
import { AppComponent } from './app.component';



const routes: Routes = [
  { path: 'user', component: UserComponent },
  { path: 'loan_eligibility', component: LoanEligibilityComponent },
  { path: '', redirectTo: 'user', pathMatch: 'full' }, // Redirect to 'user' by default
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
