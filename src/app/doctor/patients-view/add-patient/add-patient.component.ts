import { Component, OnDestroy, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PatientUpdateRequestModel } from 'src/app/models/patient';
import { PatientService } from 'src/app/services/patient.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ParentErrorStateMatcher, StringsMatch } from 'src/app/shared/validators';

@Component({
  selector: 'app-add-patient',
  templateUrl: './add-patient.component.html',
  styleUrls: ['./add-patient.component.scss']
})
export class AddPatientComponent implements OnInit, OnDestroy {

  passwordMatcher = new ParentErrorStateMatcher();
  docForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: this.fb.group({
      pass: ['', [Validators.required]],
      confirmPass: ['', [Validators.required]],
    }, {validator: StringsMatch('pass','confirmPass')}),
    roles: ['ROLE_PATIENT'],
    firstName: ['', [Validators.required]],
    secondName: [''],
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
      roomNr: ['',],
      information: [''],
    }),
    phoneNumber: ['', [Validators.required]],
    cardNumber: ['', [Validators.required]],
  })

  sub = new Subscription()

  constructor(private router: Router, private fb: FormBuilder, private patientService: PatientService, private snackbar: SnackbarService) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
  }

  back() {
    this.router.navigate(['doctor/patients'])
  }

  confirm() {
    if(this.docForm.invalid) {
      this.snackbar.error('Formularz jest nieprawidłowo uzupełniony')
      return;
    }
    const data: PatientUpdateRequestModel = {
      ...this.docForm.getRawValue(),
      phoneNumber: [{
        number: this.docForm.get('phoneNumber').value
      }],
      roles: [this.docForm.get('roles').value],
      password: this.docForm.get('password').get('pass').value,
    }
    this.sub.add(this.patientService.signUpPatient(data).subscribe(res => {
      this.snackbar.success("Pacjent został dodany");
      this.back();
    }, err => {
      this.snackbar.error("Pacjent nie został dodany")
    }))
  }
}
