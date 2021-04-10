import { Injectable } from '@angular/core';
import {Doctor} from '../interfaces/doctor'
import { HttpClient,HttpHeaders,HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  getUrl = 'http://localhost:';
  constructor(private http: HttpClient) { }

  addDoctor( doctor: Doctor): Observable<any>{
    return this.http.post(this.getUrl, JSON.stringify(doctor)).pipe(catchError(this.handleError))
  }
  getDoctors(): Observable<Doctor[]>{
    return this.http.get<any>(this.getUrl).pipe(catchError(this.handleError))
  }
  getDoctorById(id: string): Observable<Doctor>{
    return this.http.get<any>(this.getUrl+'/'+id).pipe(catchError(this.handleError))
  }
  updateDoctor(doctor: Doctor): Observable<any>{
    return this.http.put(this.getUrl, doctor).pipe(catchError(this.handleError))
  }
  deleteDoctor( id: string): Observable<any>{
    return this.http.delete(this.getUrl + '/' + id).pipe(catchError(this.handleError))
  }

  handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
