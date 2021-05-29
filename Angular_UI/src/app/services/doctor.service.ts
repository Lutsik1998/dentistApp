import { Injectable } from '@angular/core';
import {Doctor, DoctorAddRequestModel, DoctorInfoResponseModel} from '../models/doctor'
import { HttpClient,HttpHeaders,HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  getUrl = 'http://localhost:8080/api/doctor';
  constructor(private http: HttpClient) { }

  addDoctor(doctor: DoctorAddRequestModel): Observable<any>{
    return this.http.post(`${this.getUrl}/auth/signup`, doctor).pipe(catchError(this.handleError))
  }
  getDoctors(): Observable<DoctorInfoResponseModel[]>{
    return this.http.get<DoctorInfoResponseModel[]>(`${this.getUrl}/all`).pipe(catchError(this.handleError))
  }
  getDoctorByEmail(email: string): Observable<DoctorInfoResponseModel> {
    return this.http.get<DoctorInfoResponseModel[]>(`${this.getUrl}/all`).pipe(
      map((data:DoctorInfoResponseModel[] )=> {
        let result = data.filter((element: DoctorInfoResponseModel) => {
          return element.email == email;
        });
        return  result[0];
      })
      );
  }
  getDoctorById(id: string): Observable<DoctorInfoResponseModel>{
    return this.http.get<DoctorInfoResponseModel>(`${this.getUrl}/${id}`).pipe(catchError(this.handleError))
  }
  updateDoctor(id: string, doctor: DoctorAddRequestModel): Observable<any>{
    return this.http.put(`${this.getUrl}/update/${id}`, doctor).pipe(catchError(this.handleError))
  }
  deleteDoctor( id: string): Observable<any>{
    return this.http.delete(this.getUrl + '/' + id).pipe(catchError(this.handleError))
  }

  handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
