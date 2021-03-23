import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NavTab } from 'src/app/models/various.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Input() navTabs: NavTab;
  isMobileNavOpen: boolean = false;
  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  toggleMobileNav(): void {
    this.isMobileNavOpen = !this.isMobileNavOpen;
  }

  logout(): void {
    this.router.navigate(['login']);
  }
}
