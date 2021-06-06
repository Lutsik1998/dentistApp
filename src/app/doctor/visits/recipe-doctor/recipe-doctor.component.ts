import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { VisitService } from 'src/app/services/visit.service';
import { RecipeDetailsComponent } from 'src/app/shared/recipe-details/recipe-details.component';

@Component({
  selector: 'app-recipe-doctor',
  templateUrl: './recipe-doctor.component.html',
  styleUrls: ['./recipe-doctor.component.scss']
})
export class RecipeDoctorComponent implements OnInit {

  visitId: string;
  sub = new Subscription();
  constructor(private route: ActivatedRoute,
              private visitService: VisitService,
              private router: Router,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.visitId = this.route.snapshot.params.id;
    this.sub.add(this.visitService.getVisit(this.visitId).subscribe(res => {
      console.log(res);
    }))
  }

  addRecipe() {
    const dialogRef = this.dialog.open(RecipeDetailsComponent, {
      data: {
        visitId: this.visitId
      }
    });
  }

  back() {
    this.router.navigate(['doctor/visits'])
  }
}
