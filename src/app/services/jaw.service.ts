import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Tooth } from '../models/patient';

@Injectable({
  providedIn: 'root'
})
export class JawService {

  getUrl = 'http://localhost:8080/api/patient';
  constructor(private http: HttpClient) {}

  getJaw(patientId: string): Observable<Tooth[]> {
    return this.http.get<Tooth[]>(`${this.getUrl}/${patientId}/jaw`).pipe(tap((res) => {
      console.log(res[0])
    }));
  }

  updateTooth(patientId: string, toothId: string, data: Tooth) {
    return this.http.put(`${this.getUrl}/${patientId}/jaw/${toothId}`, data)
  }
}
