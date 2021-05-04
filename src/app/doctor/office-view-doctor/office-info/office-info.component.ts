import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { OfficeInfoResponseModel } from 'src/app/models/office.model';
import { OfficeService } from 'src/app/services/office.service';

@Component({
  selector: 'app-office-info',
  templateUrl: './office-info.component.html',
  styleUrls: ['./office-info.component.scss']
})
export class OfficeInfoComponent implements OnInit, OnDestroy {

  private sub: Subscription = new Subscription();
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
  @Output() getData = new EventEmitter();
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
    console.log(this.infoForm.getRawValue())
  }
  constructor(private fb: FormBuilder, private officeService: OfficeService) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  edit($event) {
    this.infoForm.enable();
  }

  save($event) {
    this.infoForm.disable();
    const data: OfficeInfoResponseModel = {
      ...this.infoForm.getRawValue(),
      phoneNumber: {
        number: this.infoForm.get('phone').value,
      }
    }
    this.sub.add(this.officeService.patchOffice(data).subscribe(res => {
      console.log(res)
    }))
    this.getData.emit();
  }

  cancel($event) {
    this.infoForm.disable();
    this.getData.emit();
  }

  ngOnInit(): void {
    
  }
}
