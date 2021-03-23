import { Component, OnInit } from '@angular/core';
import { NavTab } from '../models/various.model';

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.scss']
})
export class DoctorComponent implements OnInit {

  navTabs: NavTab[] = [
    {path: 'patients', text: 'Pacjenci'},
    {path: 'office', text: 'Gabinet'},
    {path: 'account', text: 'Konto'},
  ]

  constructor() { }

  ngOnInit(): void {
  }

}
