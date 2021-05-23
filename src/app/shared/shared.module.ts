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
import { PipesModule } from '../pipes/pipes.module';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';


@NgModule({
  declarations: [LoginComponent, RegistrationComponent,HeaderComponent, SpinnerComponent, SubheaderEditComponent, ConfirmationDialogComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    AngularMaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    PipesModule
  ],
  exports: [
    PipesModule,
    HeaderComponent,
    SpinnerComponent,
    SubheaderEditComponent,
    ReactiveFormsModule,
    AngularMaterialModule,
    ConfirmationDialogComponent
  ]
})
export class SharedModule { }
