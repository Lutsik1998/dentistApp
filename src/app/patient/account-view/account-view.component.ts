import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { PatientUpdateRequestModel } from 'src/app/models/patient';
import { AuthService } from 'src/app/services/auth.service';
import { PatientService } from 'src/app/services/patient.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ChangePasswordComponent } from 'src/app/shared/change-password/change-password.component';

@Component({
  selector: 'app-account-view',
  templateUrl: './account-view.component.html',
  styleUrls: ['./account-view.component.scss']
})
export class AccountViewComponent implements OnInit, OnDestroy {
  patientForm = this.fb.group({
    email: [''],
    password: [''],
    roles: [''],
    firstName: ['', [Validators.required]],
    secondName: [''],
    lastName: ['', [Validators.required]],
    pesel: ['',],
    birthDate: ['', [Validators.required]],
    sex: ['', [Validators.required]],
    address: this.fb.group({
      country: ['', [Validators.required]],
      region: ['', [Validators.required]],
      city: ['', [Validators.required]],
      postalCode: ['', [Validators.required]],
      street: ['', [Validators.required]],
      houseNr: ['', [Validators.required]],
      roomNr: ['',],
      information: ['',],
    }),
    phoneNumber: ['', [Validators.required]],
    cardNumber: ['', [Validators.required]],
  })

  isEditing: boolean = false;
  sub: Subscription = new Subscription();
  patientId: string;
  isLoading: boolean = true;
  constructor(private fb: FormBuilder, 
              private patientService: PatientService, 
              private auth: AuthService, 
              private snackBar: SnackbarService, 
              public dialog: MatDialog) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.isLoading = true;
    this.patientForm.disable();
    this.sub.add(this.auth.currentUser.subscribe(res => {
      this.patientId = res.id.substring(1, res.id.length - 1);
      this.sub.add(this.patientService.getPatientById(this.patientId).subscribe(res => {
        this.patientForm.patchValue({
          ...res,
          phoneNumber: res.phoneNumber ? res.phoneNumber[0]?.number : '',
        })
        this.isLoading = false;
      }))
    }))
  }

  edit($event) {
    this.patientForm.enable();
    this.isEditing = true;
  }

  save($event) {
    if(this.patientForm.invalid) {
      this.snackBar.error("Formularz jest niepoprawny")
      return;
    }
    this.isLoading = true;
    const data: PatientUpdateRequestModel = {
      ...this.patientForm.getRawValue(),
      phoneNumber: [{
        number: this.patientForm.get('phoneNumber').value
      }],
      password: ""
    }
    this.patientService.updatePatient(this.patientId, data).subscribe(res => {
      this.snackBar.success('Aktualizacja danych powiodła się')
      this.isEditing = false;
      this.isLoading = false;
      this.getData();
    }, err => {
      this.snackBar.error('Aktualizacja danych nie powiodła się')
      this.isLoading = false;
    })
  }

  changePassword() {
    const dialogRef = this.dialog.open(ChangePasswordComponent);
    this.sub.add(dialogRef.afterClosed().subscribe(res => {
      if(res) {
        this.sub.add(this.patientService.changePassword(this.patientId, res).subscribe(res => {
          this.snackBar.success('Hasło zmienione');
          this.getData();
        }, err => {
          this.snackBar.error('Hasło nie zostało zmienione');
        }))
      }
    }))
  }

  cancel($event) {
    this.getData();
    this.isEditing = false;
  }
}
