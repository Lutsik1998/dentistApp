import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from '@angular/forms'

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})

export class RegistrationComponent implements OnInit {

  genders: string[] =  ['Mężczyzna','Kobieta'];
  constructor() { }
  ngOnInit(): void {
   
  }



}
