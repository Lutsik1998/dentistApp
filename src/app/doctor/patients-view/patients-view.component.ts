import { BreakpointObserver } from '@angular/cdk/layout';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-patients-view',
  templateUrl: './patients-view.component.html',
  styleUrls: ['./patients-view.component.scss']
})
export class PatientsViewComponent implements OnInit, AfterViewInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  patients: Patient[] = [
    {cardNumber: '1234', name: 'Scarlett', surname: 'Madron', gender: 'K', email: 'sca@gmail.com'},
    {cardNumber: '2234', name: 'Bill', surname: 'Cooper', gender: 'M', email: 'bill@gmail.com'},
    {cardNumber: '3234', name: 'Hadley', surname: 'Cook', gender: 'M', email: 'had@gmail.com'},
    {cardNumber: '4234', name: 'Alice', surname: 'Reed', gender: 'K', email: 'ali@gmail.com'},    {cardNumber: '1234', name: 'Scarlett', surname: 'Madron', gender: 'K', email: 'sca@gmail.com'},
    {cardNumber: '4234', name: 'Alice', surname: 'Reed', gender: 'K', email: 'ali@gmail.com'},    {cardNumber: '1234', name: 'Scarlett', surname: 'Madron', gender: 'K', email: 'sca@gmail.com'},
    {cardNumber: '4234', name: 'Alice', surname: 'Reed', gender: 'K', email: 'ali@gmail.com'},    {cardNumber: '1234', name: 'Scarlett', surname: 'Madron', gender: 'K', email: 'sca@gmail.com'},
    {cardNumber: '4234', name: 'Alice', surname: 'Reed', gender: 'K', email: 'ali@gmail.com'},    {cardNumber: '1234', name: 'Scarlett', surname: 'Madron', gender: 'K', email: 'sca@gmail.com'},
  ];
  tableColumns: string[] = [];
  displayedColumns: string[] = ['cardNumber', 'name', 'surname', 'gender', 'email'];
  displayedColumnsMobile: string[] = ['cardNumber', 'name', 'surname'];
  dataSource = new MatTableDataSource(this.patients);

  constructor(private breakpointObserver: BreakpointObserver) { }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.breakpointObserver.observe([
      '(max-width: 692px)'
    ]).subscribe(result => {
      if (result.matches) {
        this.tableColumns = this.displayedColumnsMobile;
      } else {
        this.tableColumns = this.displayedColumns;
      }
    });
  }

  applyFilter($event) {
    const filterValue = ($event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}

export interface Patient {
  cardNumber: string;
  name: string;
  surname: string;
  gender: string;
  email: string;
}