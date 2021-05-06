import { Component, OnDestroy, OnInit, Sanitizer } from '@angular/core';
import { FormControl } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { Subscription } from 'rxjs';
import { OfficeInfoResponseModel } from 'src/app/models/office.model';
import { OfficeService } from 'src/app/services/office.service';
import { SnackbarService } from 'src/app/services/snackbar.service';

@Component({
  selector: 'app-office-view-doctor',
  templateUrl: './office-view-doctor.component.html',
  styleUrls: ['./office-view-doctor.component.scss']
})
export class OfficeViewDoctorComponent implements OnInit, OnDestroy {
  mapIsLoaded: boolean = false;
  officeData: OfficeInfoResponseModel | null = null;
  isEditing: boolean = false;
  private sub = new Subscription();

  officeInfo = new FormControl('');

  constructor(private officeService: OfficeService, private snackBar: SnackbarService) { }
  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    this.fetchInfo();
  }

  fetchInfo() {
    this.officeData = null;
    this.sub.add(this.officeService.getOffice().subscribe(res => {
      this.officeData = res[0];
      this.officeInfo.patchValue(res[0].address.information)
    }))
  }

  mapLoaded() {
    this.mapIsLoaded = true;
  }

  edit($event) {
    this.isEditing = true;
    console.log(this.officeInfo.value)
  }

  save($event) {
    const data: OfficeInfoResponseModel = {
      ...this.officeData,
      address: {
        ...this.officeData.address,
        information: this.officeInfo.value
      }
    }
    this.sub.add(this.officeService.patchOffice(data).subscribe(res => {
      this.isEditing = false;
      this.fetchInfo()
    }, err => {
      this.snackBar.error('Zmiana danych nie powiodła się!')
    }))
  }

  cancel($event) {
    this.isEditing = false;
    this.fetchInfo();
  }
}
