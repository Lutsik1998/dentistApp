import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Recipe } from '../models/recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  baseUrl = 'http://localhost:8080/api/visit';
  constructor(private http: HttpClient) {}

  addRecipe(visitId: string, data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/${visitId}/recipe`, data).pipe(catchError(this.handleError));
  }

  editRecipe(visitId: string, recipeId: string,  recipe: Recipe) {
    return this.http.put(`${this.baseUrl}/${visitId}/recipe/${recipeId}`, recipe)
  }

  deleteRecipe(visitId: string, recipeId: string) {
    return this.http.delete(`${this.baseUrl}/${visitId}/recipe/${recipeId}`, {responseType: 'text'})
  }

  addImage(visitId: string, recipeId: string,  file: File): Observable<any> {

    const endpoint = `${this.baseUrl}/${visitId}/recipe/${recipeId}/image`;
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post(endpoint, formData).pipe(catchError(this.handleError));
  }

  getImage(visitId: string, recipeId: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/${visitId}/recipe/${recipeId}/image`, {responseType: 'blob'})
  }

  handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
