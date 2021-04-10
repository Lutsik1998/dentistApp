import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from './../../services/auth.service';
import {User} from '../../interfaces/user'

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

  constructor(private router: Router,private authService: AuthService) { }

  ngOnInit(): void {
  }

  login() {
    if(this.loginForm.invalid) {
      return;
    }
    console.log(this.loginForm.getRawValue())
    this.authService.login(this.loginForm.value).subscribe(
      (user: User) =>{
        console.log(user);
        if(user.role === "[ROLE_PATIENT]"){
          this.router.navigate(['/patient/office'])
        }
        if(user.role === "[ROLE_DOCTOR]"){
          this.router.navigate(['/doctor/office'])
        }
        if(user.role === "[ROLE_ADMIN]"){
          this.router.navigate(['/patient/office'])
        }
      },
      (exc)=>{
        alert("Doesn't exist user with this login or password. Try again");
      }
    );
  }
  registration(){
    this.router.navigate(['/registration'])
  }
}
