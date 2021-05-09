import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GenderPipe } from './gender.pipe';



@NgModule({
  declarations: [GenderPipe],
  imports: [
    CommonModule
  ],
  exports: [
    GenderPipe
  ]
})
export class PipesModule { }
