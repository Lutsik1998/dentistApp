import { Component, OnInit } from '@angular/core';
import { NavTab } from '../models/various.model';

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.scss']
})
export class PatientComponent implements OnInit {

  navTabs: NavTab[] = [
    {path: '', text: 'Gabinet'},
    {path: '', text: 'Lekarz'},
    {path: '', text: 'Zdrowie'},
    {path: '', text: 'Historia zabiegów'},
    {path: '', text: 'Recepty'},
    {path: '', text: 'Konto'},
  ]
  constructor() { }
  ngOnInit(): void {
  }

}
