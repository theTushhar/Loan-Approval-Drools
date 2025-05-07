import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoanApplication } from '../models/loan';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  private apiUrl = 'http://localhost:8080/api/loans';

  constructor(private http: HttpClient) {}

  // Fetch a single loan application by user ID
  getLoanByUserId(userId: number): Observable<LoanApplication> {
    return this.http.get<LoanApplication>(`${this.apiUrl}/user/${userId}`);
  }
}
