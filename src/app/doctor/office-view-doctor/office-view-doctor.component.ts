import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-office-view-doctor',
  templateUrl: './office-view-doctor.component.html',
  styleUrls: ['./office-view-doctor.component.scss']
})
export class OfficeViewDoctorComponent implements OnInit {
  mapIsLoaded: boolean = false;
  constructor() { }

  ngOnInit(): void {
  }
  mapLoaded() {
    this.mapIsLoaded = true;
  }
}
