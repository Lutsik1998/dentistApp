
import { HttpClient,HttpHeaders,HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map } from "rxjs/operators";
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { Router, RouterStateSnapshot, ActivatedRouteSnapshot} from '@angular/router';
import { Patient } from '../models/patient'
import { CurrentUser } from '../models/user';
import { UserRole } from '../enums/various.enum';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

    private currentUserSubject!: BehaviorSubject<CurrentUser>;
    public currentUser!: Observable<CurrentUser>;
    isLoggedOut: boolean = true;
    constructor( private router: Router,private http: HttpClient) {
      this.currentUserSubject = new BehaviorSubject<CurrentUser>(JSON.parse(localStorage.getItem('currentUser')!));
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
      return this.http.post('http://localhost:8080/api/patient/auth/signup', JSON.stringify(userRegister), {headers: headers1}).pipe(catchError(this.handleError));
    }
    
    login(userLogin: any){
      this.isLoggedOut = false;
      return this.http.post('http://localhost:8080/api/user/auth/signin', userLogin).pipe(
          map((user: CurrentUser) => {
              localStorage.setItem('currentUser', JSON.stringify(user));
              this.currentUserSubject.next(user);
              return user;
            }
          ));
      }
    handleError(error: HttpErrorResponse) {
        return throwError(error);}
  
    getRole(): UserRole {
      return this.currentUserSubject.value.role;
    }

    getId(): string {
      return this.currentUserSubject.value.id;
    }

    logout(){
      localStorage.removeItem('currentUser');
      this.isLoggedOut = true;
      this.currentUserSubject.next(null);
    }
  
  }