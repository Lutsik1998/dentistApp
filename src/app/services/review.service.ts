import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ReviewModel } from '../models/review';
import { Review } from '../models/visit';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  baseUrl: string = 'http://localhost:8080/api/visit';

  constructor(private http: HttpClient) { }

  rateVisit(visitId: string, data: ReviewModel): Observable<any> {
    return this.http.post(`${this.baseUrl}/${visitId}/review`, data).pipe(catchError(this.handleError));
  }

  deleteVisit(visitId: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${visitId}/review`, {responseType: 'text'}).pipe(catchError(this.handleError));
  }

  getReviews(doctorId: string): Observable<Review[]> {
    return this.http.get<Review[]>(`http://localhost:8080/api/doctor/${doctorId}/review/all`).pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
