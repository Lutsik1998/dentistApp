import { BreakpointObserver } from '@angular/cdk/layout';
import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserRole } from 'src/app/enums/various.enum';
import { DoctorInfoResponseModel } from 'src/app/models/doctor';
import { AuthService } from 'src/app/services/auth.service';
import { DoctorService } from 'src/app/services/doctor.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ConfirmationDialogComponent } from 'src/app/shared/confirmation-dialog/confirmation-dialog.component';
import { DoctorReviewsComponent } from 'src/app/shared/doctor-reviews/doctor-reviews.component';

@Component({
  selector: 'app-doctors-list',
  templateUrl: './doctors-list.component.html',
  styleUrls: ['./doctors-list.component.scss']
})
export class DoctorsListComponent implements OnInit, OnDestroy {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  tableColumns: string[] = ['reviews', 'firstName', 'lastName', 'sex', 'email'];
  dataSource: MatTableDataSource<DoctorInfoResponseModel>;
  sub: Subscription = new Subscription();

  get isAdmin(): boolean {
    return this.auth.getRole() == UserRole.admin;
  }

  isLoggedIn(ele: DoctorInfoResponseModel): boolean {
    return ele.id == this.auth.getId().slice(1,-1);
  }

  constructor(private doctorService: DoctorService, 
              private router: Router, 
              public dialog: MatDialog,
              private snackBar: SnackbarService,
              private auth: AuthService) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    if(this.isAdmin) {
      this.tableColumns.push('delete');
    }
    this.getData();
  }

  getData() {
    this.sub.add(this.doctorService.getDoctors().subscribe(res => {
      this.dataSource = new MatTableDataSource(res)
      this.dataSource.paginator = this.paginator;
    }))
  }

  seeReviews(doctor: DoctorInfoResponseModel) {
    const dialogRef = this.dialog.open(DoctorReviewsComponent, {
      data: {
        doctor
      },
      maxHeight: '90vh',
    });
  }

  applyFilter($event) {
    const filterValue = ($event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  addDoctor() {
    this.router.navigate(['/doctor/add-doctor'])
  }

  deleteDoctor(id: string) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {
        text: "Czy na pewno chcesz usunąć lekarza?"
      }
    });
    this.sub.add(dialogRef.afterClosed().subscribe(res => {
      if(res) {
        this.sub.add(this.doctorService.deleteDoctor(id).subscribe(res => {
          this.snackBar.success('Lekarz usunięty')
          this.getData();
        }, err => {
          this.snackBar.error('Lekarz nie został usunięty')
        }))
      }
    }))
  }

  openDetails(id: string) {
    console.log(id)
  }
}
