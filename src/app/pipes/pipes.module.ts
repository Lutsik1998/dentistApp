import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GenderPipe } from './gender.pipe';
import { DateFormatPipe } from './date-format.pipe';



@NgModule({
  declarations: [GenderPipe, DateFormatPipe],
  imports: [
    CommonModule
  ],
  exports: [
    GenderPipe,
    DateFormatPipe
  ]
})
export class PipesModule { }
