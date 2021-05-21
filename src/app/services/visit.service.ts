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

    getVisits(): Observable<VisitResponseModel[]> {
      return this.http.get<VisitResponseModel[]>(`${this.baseUrl}/all`).pipe(catchError(this.handleError))
    }

    dateObject(value: string): Date {
      const arr = value.split('-');
      return new Date(
        Number.parseInt(arr[0]),
        Number.parseInt(arr[1]),
        Number.parseInt(arr[2]),
        Number.parseInt(arr[3]),
        Number.parseInt(arr[4])
      );
    }

    handleError(error: HttpErrorResponse) {
        return throwError(error);
      }
  }