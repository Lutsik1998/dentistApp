import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from './../../services/auth.service';
import { User } from '../../models/user'
import { SnackbarService } from 'src/app/services/snackbar.service';
import { UserRole } from 'src/app/enums/various.enum';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required)
  })

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }

  
  constructor(private router: Router,private authService: AuthService, private snackbar: SnackbarService) { }

  ngOnInit(): void {
  }

  login() {
    if(this.loginForm.invalid) {
      return;
    }
    this.authService.login(this.loginForm.value).subscribe(
      (user: User) =>{
        if(user.role === UserRole.patient){
          this.router.navigate(['/patient/office'])
        }
        if(user.role === UserRole.doctor){
          this.router.navigate(['/doctor/office'])
        }
        if(user.role === UserRole.admin){
          this.router.navigate(['/patient/office'])
        }
      },
      (exc)=>{
        this.snackbar.error('Nieprawidłowy email/hasło')
      }
    );
  }
  registration(){
    this.router.navigate(['/registration'])
  }
}
