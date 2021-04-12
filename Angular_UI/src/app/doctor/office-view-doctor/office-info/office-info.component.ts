import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-office-info',
  templateUrl: './office-info.component.html',
  styleUrls: ['./office-info.component.scss']
})
export class OfficeInfoComponent implements OnInit {

  infoForm: FormGroup = this.fb.group({
    name: {value: 'PRODENTAL', disabled: true},
    address: {value: 'Trwała 5, 53-335 Wrocław', disabled: true},
    phone: {value: '48691594352', disabled: true},
    email: {value: 'prodental@gmail.com', disabled: true},
    nip: {value: '1567320844', disabled: true},
    regon: {value: '278296930', disabled: true},
  })
  constructor(private fb: FormBuilder) { }

  edit($event) {
    this.infoForm.enable();
  }

  save($event) {
    this.infoForm.disable();
  }

  cancel($event) {
    this.infoForm.disable();
  }

  ngOnInit(): void {
    
  }
}
