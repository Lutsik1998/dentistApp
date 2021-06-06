import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  baseUrl = 'http://localhost:8080/api/visit';
  constructor(private http: HttpClient) {}

  addRecipe(visitId: string, data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/${visitId}/recipe`, data).pipe(catchError(this.handleError));
  }

  addImage(visitId: string, recipeId: string,  file: File): Observable<any> {

    const endpoint = `${this.baseUrl}/${visitId}/recipe/${recipeId}/image`;
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post(endpoint, formData).pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
