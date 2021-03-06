import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Review, Visit, VisitResponseModel } from '../models/visit';


@Injectable({
    providedIn: 'root'
  })
  export class VisitService {
    baseUrl: string = 'http://localhost:8080/api/visit'
    constructor(private http: HttpClient) { }

    addVisit(visit : Visit):Observable<any>{
        return this.http.post(`${this.baseUrl}/save`, visit).pipe(catchError(this.handleError))
    }

    getVisit(id: string): Observable<VisitResponseModel> {
      return this.http.get<VisitResponseModel>(`${this.baseUrl}/${id}`).pipe(catchError(this.handleError))
    }
    getFreeDays(id: string,startDate:string,durationDate:number,durationTime:number): Observable<string[]> {
      return this.http.get<string[]>(`${this.baseUrl}/freeDays?doctorId=${id}&startDate=${startDate}&durationDate=${durationDate}&durationTime=${durationTime}`).pipe(catchError(this.handleError))
    }
    getFreeTime(id: string,startDateTime:string,endDateTime:string): Observable<Visit[]> {
      return this.http.get<Visit[]>(`${this.baseUrl}/filter1?doctorId=${id}&startDateTime=${startDateTime}&endDateTime=${endDateTime}`).pipe(catchError(this.handleError))
    }

    getVisits(): Observable<VisitResponseModel[]> {
      return this.http.get<VisitResponseModel[]>(`${this.baseUrl}/all`).pipe(catchError(this.handleError))
    }

    deleteVisit(id: string) {
      return this.http.delete(`${this.baseUrl}/${id}`, {responseType: 'text'}).pipe(catchError(this.handleError))
    }
    addReview(
      id: string,
      review: Review
    ): Observable<any> {
      return this.http
        .post(`${this.baseUrl}/${id}/review`, review)
        .pipe(catchError(this.handleError));
    }
    dateObject(value: string): Date {
      return new Date(
        value
      );
    }

    handleError(error: HttpErrorResponse) {
        return throwError(error);
      }
  }