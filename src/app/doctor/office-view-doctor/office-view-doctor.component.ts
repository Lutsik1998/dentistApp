import { Component, OnDestroy, OnInit, Sanitizer } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Subscription } from 'rxjs';
import { OfficeInfoResponseModel } from 'src/app/models/office.model';
import { OfficeService } from 'src/app/services/office.service';

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
  constructor(private officeService: OfficeService, private sanitizer: DomSanitizer) { }
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
      console.log(this.officeData)
    }))
  }

  mapLoaded() {
    this.mapIsLoaded = true;
  }

  changeEditing($event) {
    this.isEditing = $event;
  }

  textFieldChanged($event) {
    console.log(this.officeData.address.information)
  }
  
}
