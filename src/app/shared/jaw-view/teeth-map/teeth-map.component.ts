import { EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Tooth } from 'src/app/models/patient';

@Component({
  selector: 'app-teeth-map',
  templateUrl: './teeth-map.component.html',
  styleUrls: ['./teeth-map.component.scss']
})
export class TeethMapComponent implements OnInit, OnChanges {

  @Output() toothClick = new EventEmitter();
  @Input() teeth: Tooth[];
  activeTooth: number = 1;
  toothInit: boolean = true;
  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes.teeth.currentValue && this.toothInit) {
      this.toothClick.emit({number: 1, tooth: this.teeth[1]})
      this.toothInit = false;
    }
  }

  ngOnInit(): void {
  }

  toothClicked($event, tooth: Tooth, number: number) {
    this.activeTooth = number;
    this.toothClick.emit({number, tooth})
  }
}
