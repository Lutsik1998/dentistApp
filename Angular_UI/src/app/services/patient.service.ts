import { Injectable } from '@angular/core';
import {
  Patient,
  PatientInfoResponseModel,
  PatientUpdateRequestModel,
} from '../models/patient';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  getUrl = 'http://localhost:8080/api/patient';
  constructor(private http: HttpClient) {}

  addPatient(patient: Patient): Observable<any> {
    return this.http
      .post(this.getUrl, JSON.stringify(patient))
      .pipe(catchError(this.handleError));
  }
  signUpPatient(patient: PatientUpdateRequestModel): Observable<any> {
    return this.http
      .post(`${this.getUrl}/auth/signup`, patient)
      .pipe(catchError(this.handleError));
  }
  getPatients(): Observable<PatientInfoResponseModel[]> {
    return this.http
      .get<PatientInfoResponseModel[]>(`${this.getUrl}/all`)
      .pipe(catchError(this.handleError));
  }
  getPatientByEmail(email: string): Observable<PatientInfoResponseModel> {
    return this.http.get<PatientInfoResponseModel[]>(`${this.getUrl}/all`).pipe(
      map((data:PatientInfoResponseModel[] )=> {
        let result = data.filter((element: PatientInfoResponseModel) => {
          return element.email == email;
        });
        return  result[0];
      })
      );
  }
  getPatientById(id: string): Observable<PatientInfoResponseModel> {
    return this.http
      .get<PatientInfoResponseModel>(`${this.getUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }
  updatePatient(
    id: string,
    patient: PatientUpdateRequestModel
  ): Observable<any> {
    return this.http
      .put(`${this.getUrl}/update/${id}`, patient)
      .pipe(catchError(this.handleError));
  }
  deletePatient(id: string): Observable<any> {
    return this.http
      .delete(this.getUrl + '/' + id)
      .pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
