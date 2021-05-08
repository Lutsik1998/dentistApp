import { Component, OnDestroy, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PatientUpdateRequestModel } from 'src/app/models/patient';
import { AuthService } from 'src/app/services/auth.service';
import { PatientService } from 'src/app/services/patient.service';
import { SnackbarService } from 'src/app/services/snackbar.service';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.scss']
})
export class PatientDetailsComponent implements OnInit, OnDestroy {
  patientForm = this.fb.group({
    email: [''],
    password: [''],
    roles: [''],
    firstName: ['', [Validators.required]],
    secondName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    pesel: ['', [Validators.required]],
    birthDate: ['', [Validators.required]],
    sex: ['', [Validators.required]],
    address: this.fb.group({
      country: ['', [Validators.required]],
      region: ['', [Validators.required]],
      city: ['', [Validators.required]],
      postalCode: ['', [Validators.required]],
      street: ['', [Validators.required]],
      houseNr: ['', [Validators.required]],
      roomNr: ['', [Validators.required]],
      information: [''],
    }),
    phoneNumber: ['', [Validators.required]],
    cardNumber: ['', [Validators.required]],
  })

  get name() {
    return `${this.patientForm.get('firstName').value} ${this.patientForm.get('lastName').value}`
  }

  isEditing: boolean = false;
  sub: Subscription = new Subscription();
  patientId: string;
  isLoading: boolean;
  constructor(private fb: FormBuilder, private patientService: PatientService, private router: Router, private route: ActivatedRoute, private snackBar: SnackbarService) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.isLoading = true;
    this.patientForm.disable();
    this.patientId = this.route.snapshot.params.id;
    this.sub.add(this.patientService.getPatientById(this.patientId).subscribe(res => {
      this.patientForm.patchValue({
        ...res,
        phoneNumber: res.phoneNumber[0].number,
      })
      this.isLoading = false;
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

  cancel($event) {
    this.getData();
    this.isEditing = false;
  }

  back() {
    this.router.navigate(['doctor/patients'])
  }
}
