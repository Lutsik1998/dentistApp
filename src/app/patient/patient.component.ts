import { Component, OnInit } from '@angular/core';
import { NavTab } from '../models/various.model';

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.scss']
})
export class PatientComponent implements OnInit {

  navTabs: NavTab[] = [
    {path: 'office', text: 'Gabinet'},
    {path: 'doctor', text: 'Lekarz'},
    {path: 'health', text: 'Zdrowie'},
    {path: 'history', text: 'Historia zabiegów'},
    {path: 'receipt', text: 'Recepty'},
    {path: 'account', text: 'Konto'},
  ]
  constructor() { }
  ngOnInit(): void {
  }

}
