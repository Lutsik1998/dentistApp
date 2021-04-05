import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
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

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  login() {
    if(this.loginForm.invalid) {
      return;
    }
    console.log(this.loginForm.getRawValue())
    this.router.navigate(['/patient/office'])
  }
  registration(){
    this.router.navigate(['/registration'])
  }
}
