import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { AngularMaterialModule } from '../material.module';

import { RegistrationComponent } from './registration/registration.component';

import { HeaderComponent } from './header/header.component';
import{HttpClientModule} from '@angular/common/http'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [LoginComponent, RegistrationComponent,HeaderComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    AngularMaterialModule,
    FormsModule,
    ReactiveFormsModule 
  ],
  exports: [
    HeaderComponent
  ]
})
export class SharedModule { }
