import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { AngularMaterialModule } from '../material.module';
import { RouterModule } from '@angular/router';

import { RegistrationComponent } from './registration/registration.component';

import { HeaderComponent } from './header/header.component';
import{HttpClientModule} from '@angular/common/http'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SpinnerComponent } from './spinner/spinner.component';
import { SubheaderEditComponent } from './subheader-edit/subheader-edit.component';


@NgModule({
  declarations: [LoginComponent, RegistrationComponent,HeaderComponent, SpinnerComponent, SubheaderEditComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    AngularMaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  exports: [
    HeaderComponent,
    SpinnerComponent,
    SubheaderEditComponent,
    ReactiveFormsModule,
    AngularMaterialModule
  ]
})
export class SharedModule { }
