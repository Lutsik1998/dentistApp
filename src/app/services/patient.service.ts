import { Injectable } from '@angular/core';
import {Patient} from '../interfaces/patient'
import { HttpClient,HttpHeaders,HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  getUrl = 'http://localhost:';
  constructor(private http: HttpClient) { }

  addPatient( patient: Patient): Observable<any>{
    return this.http.post(this.getUrl, JSON.stringify(patient)).pipe(catchError(this.handleError))
  }
  getPatients(): Observable<Patient[]>{
    return this.http.get<any>(this.getUrl).pipe(catchError(this.handleError))
  }
  getPatientById(id: string): Observable<Patient>{
    return this.http.get<any>(this.getUrl+'/'+id).pipe(catchError(this.handleError))
  }
  updatePatient(patient: Patient): Observable<any>{
    return this.http.put(this.getUrl, patient).pipe(catchError(this.handleError))
  }
  deletePatient( id: string): Observable<any>{
    return this.http.delete(this.getUrl + '/' + id).pipe(catchError(this.handleError))
  }

  handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}

