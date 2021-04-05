import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientRoutingModule } from './patient-routing.module';
import { PatientComponent } from './patient.component';
import { SharedModule } from '../shared/shared.module';
import { OfficeViewComponent } from './office-view/office-view.component';
import { DoctorViewComponent } from './doctor-view/doctor-view.component';
import { HealthViewComponent } from './health-view/health-view.component';
import { HistoryViewComponent } from './history-view/history-view.component';
import { ReceiptViewComponent } from './receipt-view/receipt-view.component';
import { AccountViewComponent } from './account-view/account-view.component';
import { OfficeInfoComponent } from './office-view/office-info/office-info.component';


@NgModule({
  declarations: [PatientComponent, OfficeViewComponent, DoctorViewComponent, HealthViewComponent, HistoryViewComponent, ReceiptViewComponent, AccountViewComponent, OfficeInfoComponent],
  imports: [
    CommonModule,
    PatientRoutingModule,
    SharedModule
  ]
})
export class PatientModule { }
