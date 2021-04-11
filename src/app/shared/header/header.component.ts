import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NavTab } from 'src/app/models/various.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Input() navTabs: NavTab;
  isMobileNavOpen: boolean = false;
  constructor(private router: Router, private auth: AuthService) { }

  ngOnInit(): void {
  }

  toggleMobileNav(): void {
    this.isMobileNavOpen = !this.isMobileNavOpen;
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['login']);
  }
}
