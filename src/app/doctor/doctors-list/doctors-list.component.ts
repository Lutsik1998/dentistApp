import { BreakpointObserver } from '@angular/cdk/layout';
import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DoctorInfoResponseModel } from 'src/app/models/doctor';
import { DoctorService } from 'src/app/services/doctor.service';

@Component({
  selector: 'app-doctors-list',
  templateUrl: './doctors-list.component.html',
  styleUrls: ['./doctors-list.component.scss']
})
export class DoctorsListComponent implements OnInit, OnDestroy {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  tableColumns: string[] = [];
  displayedColumns: string[] = ['licence', 'firstName', 'lastName', 'sex', 'email'];
  displayedColumnsMobile: string[] = ['licence', 'firstName', 'lastName'];
  dataSource: MatTableDataSource<DoctorInfoResponseModel>;
  sub: Subscription = new Subscription();

  constructor(private breakpointObserver: BreakpointObserver, private doctorService: DoctorService, private router: Router) { }

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
