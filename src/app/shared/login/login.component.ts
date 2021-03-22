import { Component, OnInit } from '@angular/core';
import { HttpClient,HttpHeaders,HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private router: Router,private http: HttpClient) { }

  ngOnInit(): void {
}

  login() {
    return this.http.get('http://localhost:8080/api/helper/test');
    this.router.navigate(['/patient'])
  }
  registration(){
    this.router.navigate(['/registration'])
  }
}
