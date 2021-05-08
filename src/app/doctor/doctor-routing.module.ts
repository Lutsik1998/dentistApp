import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountViewDoctorComponent } from './account-view-doctor/account-view-doctor.component';
import { DoctorComponent } from './doctor.component';
import { AddDoctorComponent } from './doctors-list/add-doctor/add-doctor.component';
import { DoctorsListComponent } from './doctors-list/doctors-list.component';
import { OfficeViewDoctorComponent } from './office-view-doctor/office-view-doctor.component';
import { AddPatientComponent } from './patients-view/add-patient/add-patient.component';
import { PatientsViewComponent } from './patients-view/patients-view.component';

const routes: Routes = [
  {path: '', component: DoctorComponent, children: [
    {path: 'patients', component: PatientsViewComponent},
    {path: 'office', component: OfficeViewDoctorComponent},
    {path: 'account', component: AccountViewDoctorComponent},
    {path: 'doctors', component: DoctorsListComponent},
    {path: 'add-doctor', component: AddDoctorComponent},
    {path: 'add-patient', component: AddPatientComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DoctorRoutingModule { }
