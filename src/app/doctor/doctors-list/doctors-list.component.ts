import { BreakpointObserver } from '@angular/cdk/layout';
import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DoctorInfoResponseModel } from 'src/app/models/doctor';
import { DoctorService } from 'src/app/services/doctor.service';
import { DoctorReviewsComponent } from 'src/app/shared/doctor-reviews/doctor-reviews.component';

@Component({
  selector: 'app-doctors-list',
  templateUrl: './doctors-list.component.html',
  styleUrls: ['./doctors-list.component.scss']
})
export class DoctorsListComponent implements OnInit, OnDestroy {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  tableColumns: string[] = [];
  displayedColumns: string[] = ['reviews', 'firstName', 'lastName', 'sex', 'email'];
  displayedColumnsMobile: string[] = ['reviews', 'firstName', 'lastName'];
  dataSource: MatTableDataSource<DoctorInfoResponseModel>;
  sub: Subscription = new Subscription();

  constructor(private breakpointObserver: BreakpointObserver, private doctorService: DoctorService, private router: Router, public dialog: MatDialog) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    this.sub.add(this.breakpointObserver.observe([
      '(max-width: 692px)'
    ]).subscribe(result => {
      if (result.matches) {
        this.tableColumns = this.displayedColumnsMobile;
      } else {
        this.tableColumns = this.displayedColumns;
      }
    }));
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

  openDetails(id: string) {
    console.log(id)
  }
}
