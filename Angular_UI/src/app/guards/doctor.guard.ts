import { Injectable, OnDestroy } from '@angular/core';
import { CanLoad, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserRole } from '../enums/various.enum';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class DoctorGuard implements CanLoad, OnDestroy {
  sub: Subscription = new Subscription();
  userRole: UserRole;
  constructor(private auth: AuthService, private router: Router) {
    this.sub.add(this.auth.currentUser.subscribe(res => {
      this.userRole = res?.role;
    }))
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  canLoad() {
    if(this.userRole == UserRole.doctor) {
      return true;
    }
    this.router.navigate(['login'])
    return false;
  }
  
}
