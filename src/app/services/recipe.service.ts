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
    return this.http.post(`${this.baseUrl}/${visitId}/recipe/${recipeId}/image`, file).pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
