import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-office-view',
  templateUrl: './office-view.component.html',
  styleUrls: ['./office-view.component.scss']
})
export class OfficeViewComponent implements OnInit {

  mapIsLoaded: boolean = false;
  constructor() { }

  ngOnInit(): void {
  }

  mapLoaded() {
    this.mapIsLoaded = true;
  }
}
