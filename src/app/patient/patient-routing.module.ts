import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountViewComponent } from './account-view/account-view.component';
import { DoctorViewComponent } from './doctor-view/doctor-view.component';
import { HealthViewComponent } from './health-view/health-view.component';
import { HistoryViewComponent } from './history-view/history-view.component';
import { OfficeViewComponent } from './office-view/office-view.component';
import { PatientComponent } from './patient.component';
import { ReceiptViewComponent } from './receipt-view/receipt-view.component';

const routes: Routes = [
  { path: '', component: PatientComponent, children: [
    { path: 'account', component: AccountViewComponent },
    { path: 'doctor', component: DoctorViewComponent },
    { path: 'health', component: HealthViewComponent },
    { path: 'history', component: HistoryViewComponent },
    { path: 'office', component: OfficeViewComponent },
    { path: 'visit/:id', component: ReceiptViewComponent }
  ]},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientRoutingModule { }
