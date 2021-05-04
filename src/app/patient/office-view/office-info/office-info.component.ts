import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { OfficeInfoResponseModel } from 'src/app/models/office.model';

@Component({
  selector: 'app-office-info',
  templateUrl: './office-info.component.html',
  styleUrls: ['./office-info.component.scss']
})
export class OfficeInfoComponent implements OnInit {
  infoForm: FormGroup = this.fb.group({
    id: {value: '', disabled: true},
    name: {value: '', disabled: true},
    address: this.fb.group({
      country: {value: '', disabled: true},
      region: {value: '', disabled: true},
      city: {value: '', disabled: true},
      postalCode: {value: '', disabled: true},
      street: {value: '', disabled: true},
      houseNr: {value: '', disabled: true},
      roomNr: {value: '', disabled: true},
      information: {value: '', disabled: true},
    }),
    listDoctorsId: {value: '', disabled: true},
    phone: {value: '', disabled: true},
    nip: {value: '', disabled: true},
  })
  @Input() set data(value: OfficeInfoResponseModel | null) {
    if(!value) return;
    this.infoForm.patchValue({
      id: value.id,
      name: value.name,
      nip: value.nip,
      phone: value.phoneNumber.number,
      address: value.address,
      listDoctorsId: value.listDoctorsId,
    })
  }
  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
  }

}
