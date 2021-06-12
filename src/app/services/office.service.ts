import { HttpClient,HttpHeaders,HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OfficeInfoResponseModel } from '../models/office.model';

@Injectable({
  providedIn: 'root'
})
export class OfficeService {
  baseUrl: string = 'http://localhost:8080/api/office'
  constructor(private http: HttpClient) { }

  getOffice(): Observable<OfficeInfoResponseModel[]> {
    return this.http.get<OfficeInfoResponseModel[]>(this.baseUrl + '/all')
  }

  postOffice(body) {
    return this.http.post(`${this.baseUrl}/save`, body)
  }

  patchOffice(body: OfficeInfoResponseModel) {
    return this.http.put(`${this.baseUrl}/${body.id}`, body)
  }
}
