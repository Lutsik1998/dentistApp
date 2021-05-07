import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DoctorAddRequestModel } from 'src/app/models/doctor';
import { DoctorService } from 'src/app/services/doctor.service';
import { SnackbarService } from 'src/app/services/snackbar.service';

@Component({
  selector: 'app-add-doctor',
  templateUrl: './add-doctor.component.html',
  styleUrls: ['./add-doctor.component.scss']
})
export class AddDoctorComponent implements OnInit {

  docForm = this.fb.group({
    email: ['', [Validators.required]],
    password: this.fb.group({
      pass: ['', [Validators.required]],
      confirmPass: ['', [Validators.required]],
    }),
    roles: ['ROLE_DOCTOR'],
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
      information: ['', [Validators.required]],
    }),
    phoneNumber: ['', [Validators.required]],
    licence: ['', [Validators.required]],
    specialization: ['', [Validators.required]],
  })

  constructor(private router: Router, private fb: FormBuilder, private doctorService: DoctorService, private snackbar: SnackbarService) { }

  ngOnInit(): void {
  }

  back() {
    this.router.navigate(['doctor/doctors'])
  }

  confirm() {
    if(this.docForm.invalid) {
      console.log(this.docForm)
      return;
    }
    console.log(this.docForm.getRawValue())
    const data: DoctorAddRequestModel = {
      ...this.docForm.getRawValue(),
      phoneNumber: [{
        number: this.docForm.get('phoneNumber').value
      }],
      specialization: [this.docForm.get('specialization').value],
      roles: [this.docForm.get('roles').value],
      password: this.docForm.get('password').get('pass').value,
    }
    console.log(data)
    this.doctorService.addDoctor(data).subscribe(res => {
      this.snackbar.success("Lekarz został dodany");
      this.back();
    }, err => {
      this.snackbar.error("Lekarz nie został dodany")
    })
  }
}
