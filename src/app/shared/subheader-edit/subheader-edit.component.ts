import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-subheader-edit',
  templateUrl: './subheader-edit.component.html',
  styleUrls: ['./subheader-edit.component.scss']
})
export class SubheaderEditComponent implements OnInit {

  @Input() header: string = '';
  @Input() edit: boolean = false;
  @Output() isEditing = new EventEmitter();
  @Output() isSaved = new EventEmitter();
  @Output() isCanceled = new EventEmitter();
  constructor() { }

  ngOnInit(): void {
  }

  save($event) {
    this.isSaved.emit($event);
  }

  cancel($event) {
    this.isCanceled.emit($event);
  }

  editEnable($event) {
    this.isEditing.emit($event);
  }
}
