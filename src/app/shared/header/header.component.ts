import { Component, Input, OnInit } from '@angular/core';
import { NavTab } from 'src/app/models/various.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Input() navTabs: NavTab;
  constructor() { }

  ngOnInit(): void {
  }

}
