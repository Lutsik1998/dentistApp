import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models/recipe';
import { VisitResponseModel } from 'src/app/models/visit';
import { DoctorService } from 'src/app/services/doctor.service';
import { RecipeService } from 'src/app/services/recipe.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { VisitService } from 'src/app/services/visit.service';
import { ConfirmationDialogComponent } from 'src/app/shared/confirmation-dialog/confirmation-dialog.component';
import { RecipeDetailsComponent } from 'src/app/shared/recipe-details/recipe-details.component';

@Component({
  selector: 'app-receipt-view',
  templateUrl: './receipt-view.component.html',
  styleUrls: ['./receipt-view.component.scss']
})
export class ReceiptViewComponent implements OnInit, OnDestroy {

  visitId: string;
  visit: VisitResponseModel
  recipeList: Recipe[] = [];
  sub = new Subscription();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  tableColumns: string[] =['nr', 'dateTime', 'lastDate', 'view'];
  dataSource: MatTableDataSource<Recipe>;

  constructor(private route: ActivatedRoute,
              private visitService: VisitService,
              private router: Router,
              private dialog: MatDialog,
              private doctorService: DoctorService,) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    this.visitId = this.route.snapshot.params.id;
    this.getData();
  }

  getData() {
    this.sub.add(this.visitService.getVisit(this.visitId).subscribe(res => {
      this.visit = res;
      console.log(this.visit)
      // this.sub.add(this.doctorService.getDoctorById(this.visit.doctorId).subscribe(res => {
      //   console.log(res)
      // }))
      if(!res.recipes) {
        return;
      }
      this.recipeList = res.recipes.filter(ele => ele != null);
      this.dataSource = new MatTableDataSource(this.recipeList)
      this.dataSource.paginator = this.paginator;
    }))
  }

  
  applyFilter($event) {
    const filterValue = ($event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  showRecipe(recipe: Recipe) {
    const dialogRef = this.dialog.open(RecipeDetailsComponent, {
      data: {
        visitId: this.visitId,
        show: true,
        recipe,
      }
    });
  }

  back() {
    this.router.navigate(['patient/history'])
  }
}
