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
    address: {value: 'Trwała 7, 50-529 Wrocław', disabled: true},
    phone: {value: '+48 691 594 352', disabled: true},
    email: {value: 'prodental@gmail.com', disabled: true},
    nip: {value: '1567320844', disabled: true},
    regon: {value: '278296930', disabled: true},
  })
  isEnabled: boolean = false;
  constructor(private fb: FormBuilder) { }

  edit() {
    this.infoForm.enable();
    this.isEnabled = true;
  }

  save() {
    this.infoForm.disable();
    this.isEnabled = false;
  }

  drop() {
    this.infoForm.disable();
    this.isEnabled = false;
  }

  ngOnInit(): void {
    
  }
}
