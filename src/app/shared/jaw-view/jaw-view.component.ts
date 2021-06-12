import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ToothStatus } from 'src/app/enums/various.enum';
import { Tooth } from 'src/app/models/patient';
import { AuthService } from 'src/app/services/auth.service';
import { JawService } from 'src/app/services/jaw.service';

@Component({
  selector: 'app-jaw-view',
  templateUrl: './jaw-view.component.html',
  styleUrls: ['./jaw-view.component.scss']
})
export class JawViewComponent implements OnInit {
  form = this.fb.group({
    state: ToothStatus.notHealthy,
    information: 'gseg'
  })

  @Input() teeth: Tooth[];
  @Input() patientId: string;
  @Input() isDoc: boolean = false;
  @Output() readonly teethChanged = new EventEmitter();
  activeTooth: number;
  isEditing: boolean = false;

  statusSelect = [
    {value: ToothStatus.healthy, display: "Zdrowy"},
    {value: ToothStatus.inProgress, display: "W trakcie leczenia"},
    {value: ToothStatus.notHealthy, display: "Chory"},
    {value: ToothStatus.removed, display: "Usunięty"}
  ]
  constructor(private fb: FormBuilder, private jawService: JawService, private authService: AuthService) { }

  ngOnInit(): void {
    this.form.disable();
  }

  cancel($event) {
    this.form.disable();
    this.isEditing = false;
    this.teethChanged.emit();
  }

  edit($event) {
    this.form.enable();
    this.isEditing = true;
  }

  save($event) {
    if(!this.patientId || !this.activeTooth) return;
    this.jawService.updateTooth(this.patientId, this.activeTooth.toString(), this.form.getRawValue()).subscribe(res => {
      this.teethChanged.emit();
      this.isEditing = false;
      this.form.disable();
    })
  }

  toothClicked(tooth: {number: number, tooth: Tooth}) {
    this.activeTooth = tooth.number;
    this.form.patchValue({
      ...tooth.tooth
    })
  }
}
