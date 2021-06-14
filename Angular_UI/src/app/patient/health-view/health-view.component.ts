import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Tooth } from 'src/app/models/patient';
import { AuthService } from 'src/app/services/auth.service';
import { JawService } from 'src/app/services/jaw.service';

@Component({
  selector: 'app-health-view',
  templateUrl: './health-view.component.html',
  styleUrls: ['./health-view.component.scss']
})
export class HealthViewComponent implements OnInit, OnDestroy {

  patientId: string;
  sub = new Subscription();
  teeth: Tooth[];
  constructor(private jawService: JawService, private authService: AuthService) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    this.patientId = this.authService.getId().slice(1,-1);
    this.sub.add(this.jawService.getJaw(this.patientId).subscribe(res => {
      this.teeth = res;
    }))
  }
}
