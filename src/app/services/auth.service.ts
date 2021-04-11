
import { HttpClient,HttpHeaders,HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map } from "rxjs/operators";
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { Router, RouterStateSnapshot, ActivatedRouteSnapshot} from '@angular/router';
import { Patient } from '../interfaces/patient'
import { User } from '../interfaces/user';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

    private currentUserSubject!: BehaviorSubject<any>;
    public currentUser!: Observable<any>;
    isLoggedOut: boolean = true;
    constructor( private router: Router,private http: HttpClient) {
      this.currentUserSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentUser')!));
      this.currentUser = this.currentUserSubject.asObservable();
     }
     canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
      if (this.isLoggedOut) {
        return true;
      } else {
        alert('Please logout')
        this.router.navigate(['']);
        return false;
      }
    }
  
    register(userRegister: Patient){
      const headers1 = new HttpHeaders().set('Content-Type', 'application/json').set('Accept', 'application/json').set('Authorization', 'my_token');
      return this.http.post('http://localhost:8080/api/auth/patient/signup', JSON.stringify(userRegister), {headers: headers1}).pipe(catchError(this.handleError));
    }
    
    login(userLogin: any){
      this.isLoggedOut = false;
      return this.http.post('http://localhost:8080/api/user/auth/signin', userLogin).pipe(
          map((user: User) => {
              localStorage.setItem('currentUser', JSON.stringify(user));
              this.currentUserSubject.next(user);
              return user;
            }
          ));
      }
    handleError(error: HttpErrorResponse) {
        return throwError(error);}
  
  
    logout(){
      localStorage.removeItem('currentUser');
      this.isLoggedOut = true;
      this.currentUserSubject.next(null);
    }
  
  }