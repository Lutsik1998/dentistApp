import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { PatientInfoResponseModel } from 'src/app/models/patient';
import { PatientService } from 'src/app/services/patient.service';

@Component({
  selector: 'app-patients-view',
  templateUrl: './patients-view.component.html',
  styleUrls: ['./patients-view.component.scss']
})
export class PatientsViewComponent implements OnInit, OnDestroy {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  tableColumns: string[] = [];
  displayedColumns: string[] = ['cardNumber', 'firstName', 'lastName', 'sex', 'email'];
  displayedColumnsMobile: string[] = ['cardNumber', 'firstName', 'lastName'];
  dataSource: MatTableDataSource<PatientInfoResponseModel>;
  sub: Subscription = new Subscription();

  constructor(private breakpointObserver: BreakpointObserver, private patientService: PatientService) { }

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
    this.sub.add(this.patientService.getPatients().subscribe(res => {
      this.dataSource = new MatTableDataSource(res);
      this.dataSource.paginator = this.paginator;
    }))
  }

  applyFilter($event) {
    const filterValue = ($event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
