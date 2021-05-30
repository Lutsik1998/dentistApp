import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { DoctorAddRequestModel } from 'src/app/models/doctor';
import { AuthService } from 'src/app/services/auth.service';
import { DoctorService } from 'src/app/services/doctor.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ChangePasswordComponent } from 'src/app/shared/change-password/change-password.component';

@Component({
  selector: 'app-account-view-doctor',
  templateUrl: './account-view-doctor.component.html',
  styleUrls: ['./account-view-doctor.component.scss']
})
export class AccountViewDoctorComponent implements OnInit, OnDestroy {
  docForm = this.fb.group({
    email: [''],
    password: [''],
    roles: [''],
    firstName: ['', [Validators.required]],
    secondName: ['', ],
    lastName: ['', [Validators.required]],
    pesel: ['', ],
    birthDate: ['', [Validators.required]],
    sex: ['', [Validators.required]],
    address: this.fb.group({
      country: ['', [Validators.required]],
      region: ['', [Validators.required]],
      city: ['', [Validators.required]],
      postalCode: ['', [Validators.required]],
      street: ['', [Validators.required]],
      houseNr: ['', [Validators.required]],
      roomNr: ['', ],
      information: ['', ],
    }),
    phoneNumber: ['', [Validators.required]],
    licence: ['', [Validators.required]],
    specialization: ['',],
  })

  isEditing: boolean = false;
  sub: Subscription = new Subscription();
  docId: string;
  isLoading: boolean = true;
  constructor(private fb: FormBuilder,
              private docService: DoctorService, 
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
    this.docForm.disable();
    this.sub.add(this.auth.currentUser.subscribe(res => {
      this.docId = res.id.substring(1, res.id.length - 1);
      this.sub.add(this.docService.getDoctorById(this.docId).subscribe(res => {
        this.docForm.patchValue({
          ...res,
          phoneNumber: res.phoneNumber ? res.phoneNumber[0]?.number : '',
          specialization: res.specialization ? res.specialization[0] : '',
        })
        this.isLoading = false;
      }))
    }))
  }

  edit($event) {
    this.docForm.enable();
    this.isEditing = true;
  }

  save($event) {
    if(this.docForm.invalid) {
      this.snackBar.error("Formularz jest niepoprawny")
      return;
    }
    this.isLoading = true;
    const data: DoctorAddRequestModel = {
      ...this.docForm.getRawValue(),
      phoneNumber: [{
        number: this.docForm.get('phoneNumber').value
      }],
      specialization: [this.docForm.get('specialization').value],
      password: ''
    }
    this.docService.updateDoctor(this.docId, data).subscribe(res => {
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

  changePassword() {
    const dialogRef = this.dialog.open(ChangePasswordComponent);
    this.sub.add(dialogRef.afterClosed().subscribe(res => {
      if(res) {
        this.sub.add(this.docService.changePassword(this.docId, res).subscribe(res => {
          this.snackBar.success('Hasło zmienione');
          this.getData();
        }, err => {
          this.snackBar.error('Hasło nie zostało zmienione');
        }))
      }
    }))
  }
}
