import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DoctorGuard } from './guards/doctor.guard';
import { PatientGuard } from './guards/patient.guard';
import { LoginComponent } from './shared/login/login.component';
import { RegistrationComponent } from './shared/registration/registration.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: '', redirectTo: '/login', pathMatch:  'full'},
  { path: 'patient', loadChildren: () => import('./patient/patient.module').then(m => m.PatientModule), canLoad: [PatientGuard]},
  { path: 'doctor', loadChildren: () => import('./doctor/doctor.module').then(m => m.DoctorModule), canLoad: [DoctorGuard]},
  { path: '**', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
