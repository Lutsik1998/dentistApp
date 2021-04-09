import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';

import { DoctorRoutingModule } from './doctor-routing.module';
import { DoctorComponent } from './doctor.component';
import { PatientsViewComponent } from './patients-view/patients-view.component';
import { OfficeViewDoctorComponent } from './office-view-doctor/office-view-doctor.component';
import { AccountViewDoctorComponent } from './account-view-doctor/account-view-doctor.component';
import { OfficeInfoComponent } from './office-view-doctor/office-info/office-info.component';
import { DoctorsListComponent } from './doctors-list/doctors-list.component';


@NgModule({
  declarations: [DoctorComponent, PatientsViewComponent, OfficeViewDoctorComponent, AccountViewDoctorComponent, OfficeInfoComponent, DoctorsListComponent],
  imports: [
    CommonModule,
    DoctorRoutingModule,
    SharedModule,
  ]
})
export class DoctorModule { }
