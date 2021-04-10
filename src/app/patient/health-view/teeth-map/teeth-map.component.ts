import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-teeth-map',
  templateUrl: './teeth-map.component.html',
  styleUrls: ['./teeth-map.component.scss']
})
export class TeethMapComponent implements OnInit {

  @Output() toothClick = new EventEmitter();
  constructor() { }

  ngOnInit(): void {
  }

  toothClicked($event, nr) {
    this.toothClick.emit(nr)
  }
}
