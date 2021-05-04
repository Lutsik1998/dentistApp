import { HttpClient } from '@angular/common/http';
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

  patchOffice(body: OfficeInfoResponseModel) {
    console.log(body)
    return this.http.put(`${this.baseUrl}/${body.id}`, body)
  }
}
