import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { CommonModule, DatePipe } from '@angular/common';
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
import { DoctorReviewsComponent } from './doctor-reviews/doctor-reviews.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { RecipeDetailsComponent } from './recipe-details/recipe-details.component';
import { NgxDropzoneModule } from 'ngx-dropzone';
import { ImageShowComponent } from './image-show/image-show.component';
import { JawViewComponent } from './jaw-view/jaw-view.component';
import { TeethMapComponent } from './jaw-view/teeth-map/teeth-map.component';


@NgModule({
  declarations: [LoginComponent, RegistrationComponent,HeaderComponent, SpinnerComponent, SubheaderEditComponent, ConfirmationDialogComponent, DoctorReviewsComponent, ChangePasswordComponent, RecipeDetailsComponent, ImageShowComponent, JawViewComponent, TeethMapComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    AngularMaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    PipesModule,
    NgbModule,
    NgxDropzoneModule
  ],
  exports: [
    PipesModule,
    HeaderComponent,
    SpinnerComponent,
    SubheaderEditComponent,
    ReactiveFormsModule,
    AngularMaterialModule,
    NgbModule,
    ConfirmationDialogComponent,
    DoctorReviewsComponent,
    ChangePasswordComponent,
    DoctorReviewsComponent,
    RecipeDetailsComponent,
    ImageShowComponent,
    JawViewComponent
  ],
  providers: [DatePipe]
})
export class SharedModule { }
