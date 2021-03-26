import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RouterModule } from '@angular/router';

import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import { HeaderComponent } from './header/header.component';
import {MatDividerModule} from '@angular/material/divider';



@NgModule({
  declarations: [LoginComponent, HeaderComponent],
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    RouterModule,
    MatDividerModule
  ],
  exports: [
    HeaderComponent
  ]
})
export class SharedModule { }
