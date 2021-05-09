import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { OfficeInfoResponseModel } from 'src/app/models/office.model';
import { OfficeService } from 'src/app/services/office.service';
import { SnackbarService } from 'src/app/services/snackbar.service';

@Component({
  selector: 'app-office-info',
  templateUrl: './office-info.component.html',
  styleUrls: ['./office-info.component.scss']
})
export class OfficeInfoComponent implements OnInit, OnDestroy {

  private sub: Subscription = new Subscription();
  infoForm: FormGroup = this.fb.group({
    id: [''],
    name: ['', [Validators.required]],
    address: this.fb.group({
      country: [''],
      region: [''],
      city: ['', [Validators.required]],
      postalCode: ['', [Validators.required]],
      street: ['', [Validators.required]],
      houseNr: ['', [Validators.required]],
      roomNr: [''],
      information: [''],
    }),
    listDoctorsId: [''],
    phone: ['', [Validators.required]],
    nip: ['', [Validators.required]],
  })
  isEditing: boolean = false;
  isLoaded: boolean = false;
  @Output() editingChange = new EventEmitter<boolean>();
  @Output() getData = new EventEmitter();
  @Input() set data(value: OfficeInfoResponseModel | null) {
    if(!value) return;
    this.isLoaded = true;
    this.infoForm.patchValue({
      id: value.id,
      name: value.name,
      nip: value.nip,
      phone: value.phoneNumber.number,
      address: value.address,
      listDoctorsId: value.listDoctorsId,
    })
  }
  constructor(private fb: FormBuilder, private officeService: OfficeService, private snackBar: SnackbarService) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  edit($event) {
    this.infoForm.enable();
    this.isEditing = true;
    this.editingChange.emit(this.isEditing)
  }

  save($event) {
    if(this.infoForm.invalid) {
      this.snackBar.error("Formularz jest niepoprawnie wypełniony")
      return;
    }
    this.infoForm.disable();
    const data: OfficeInfoResponseModel = {
      ...this.infoForm.getRawValue(),
      phoneNumber: {
        number: this.infoForm.get('phone').value,
      }
    }
    this.isLoaded = false;
    this.sub.add(this.officeService.patchOffice(data).subscribe(res => {
      this.getData.emit();
      this.isEditing = false;
      this.editingChange.emit(this.isEditing)
    }, err => {
      this.snackBar.error('Zmiana danych nie powiodła się!')
      this.getData.emit();
    }))
  }

  cancel($event) {
    this.infoForm.disable();
    this.isLoaded = false;
    this.isEditing = false;
    this.editingChange.emit(this.isEditing)
    this.getData.emit();
  }

  ngOnInit(): void {
    this.infoForm.disable();
  }
}
