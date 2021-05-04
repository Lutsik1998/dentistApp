import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { OfficeInfoResponseModel } from 'src/app/models/office.model';
import { OfficeService } from 'src/app/services/office.service';

@Component({
  selector: 'app-office-view',
  templateUrl: './office-view.component.html',
  styleUrls: ['./office-view.component.scss']
})
export class OfficeViewComponent implements OnInit {

  mapIsLoaded: boolean = false;
  officeData: OfficeInfoResponseModel | null = null;
  private sub = new Subscription();
  constructor(private officeService: OfficeService) { }

  ngOnInit(): void {
    this.fetchInfo();
  }

  fetchInfo() {
    this.officeData = null;
    this.sub.add(this.officeService.getOffice().subscribe(res => {
      this.officeData = res[0];
    }))
  }

  mapLoaded() {
    this.mapIsLoaded = true;
  }
}
