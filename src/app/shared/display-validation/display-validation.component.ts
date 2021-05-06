import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-display-validation',
  templateUrl: './display-validation.component.html',
  styleUrls: ['./display-validation.component.scss']
})
export class DisplayValidationComponent implements OnInit {
  @Input() label: string = '';
  @Input() secondLabel: string = '';
  @Input() errors: any;
  constructor() { }

  ngOnInit(): void {
  }

}
