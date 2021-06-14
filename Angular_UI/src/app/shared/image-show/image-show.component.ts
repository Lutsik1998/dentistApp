import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-image-show',
  templateUrl: './image-show.component.html',
  styleUrls: ['./image-show.component.scss']
})
export class ImageShowComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data, 
              public dialogRef: MatDialogRef<ImageShowComponent>,) { }

  ngOnInit(): void {
  }

}
