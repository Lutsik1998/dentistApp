import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-account-view',
  templateUrl: './account-view.component.html',
  styleUrls: ['./account-view.component.scss']
})
export class AccountViewComponent implements OnInit {
  personalForm = this.fb.group({
    name: '',
    secondName: '',
    surname: '',
    pesel: '',
    birthday: '',
    gender: '',
    email: '',
    phone: '',
    address: '',
  })

  accountForm = this.fb.group({
    email: '',
    username: '',
  })

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.personalForm.disable();
    this.accountForm.disable();
  }

  personalEnable($event) {
    this.personalForm.enable();
  }

  personalSave($event) {
    this.personalForm.disable();
  }

  personalCancel($event) {
    this.personalForm.disable();
  }

  accountEnable($event) {
    this.accountForm.enable();
  }

  accountSave($event) {
    this.accountForm.disable();
  }

  accountCancel($event) {
    this.accountForm.disable();
  }
}
