import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/services/snackbar.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {


  password = new FormControl('', Validators.compose([
    Validators.minLength(5),
    Validators.required,
    Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$')
  ]))

  constructor(public dialogRef: MatDialogRef<ChangePasswordComponent>, private snackBar: SnackbarService) { }

  ngOnInit(): void {
  }

  confirm() {
    if(this.password.invalid) {
      this.snackBar.error('Formularz niepoprawny')
      return;
    }
    this.dialogRef.close({password: this.password.value})
  }

  cancel() {
    this.dialogRef.close();
  }
}
