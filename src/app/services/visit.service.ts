import { HttpClient,HttpHeaders,HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Visit } from '../models/visit';


@Injectable({
    providedIn: 'root'
  })
  export class VisitService {
    baseUrl: string = 'http://localhost:8080/api/visit'
    constructor(private http: HttpClient) { }

    addVisit(visit : Visit):Observable<any>{
        return this.http.post(`${this.baseUrl}/save`, visit).pipe(catchError(this.handleError))
    }
    handleError(error: HttpErrorResponse) {
        return throwError(error);
      }
  }