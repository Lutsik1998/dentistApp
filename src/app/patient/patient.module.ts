import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PatientRoutingModule } from './patient-routing.module';
import { PatientComponent } from './patient.component';


@NgModule({
  declarations: [PatientComponent],
  imports: [
    CommonModule,
    PatientRoutingModule
  ]
})
export class PatientModule { }
