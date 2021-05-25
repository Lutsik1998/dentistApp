import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Visit, VisitResponseModel } from '../models/visit';


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

    getVisits(): Observable<VisitResponseModel[]> {
      return this.http.get<VisitResponseModel[]>(`${this.baseUrl}/all`).pipe(catchError(this.handleError))
    }

    deleteVisit(id: string) {
      return this.http.delete(`${this.baseUrl}/${id}`, {responseType: 'text'}).pipe(catchError(this.handleError))
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