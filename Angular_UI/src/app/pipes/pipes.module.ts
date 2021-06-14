import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GenderPipe } from './gender.pipe';
import { DateFormatPipe } from './date-format.pipe';
import { StringDatePipe } from './string-date.pipe';



@NgModule({
  declarations: [GenderPipe, DateFormatPipe, StringDatePipe],
  imports: [
    CommonModule
  ],
  exports: [
    GenderPipe,
    DateFormatPipe,
    StringDatePipe
  ]
})
export class PipesModule { }
