import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { VisitListItemModel, VisitResponseModel } from 'src/app/models/visit';
import { AuthService } from 'src/app/services/auth.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { VisitService } from 'src/app/services/visit.service';
import { ConfirmationDialogComponent } from 'src/app/shared/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-history-view',
  templateUrl: './history-view.component.html',
  styleUrls: ['./history-view.component.scss']
})
export class HistoryViewComponent implements OnInit, OnDestroy {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  tableColumns: string[] = [];
  displayedColumns: string[] =['start', 'end', 'info', 'delete'];
  displayedColumnsMobile: string[] = ['start', 'delete'];
  dataSource: MatTableDataSource<VisitResponseModel>;

  sub = new Subscription();
  patientId: string;

  constructor(private visitService: VisitService,
              private authService: AuthService,
              private snackBar: SnackbarService,
              public dialog: MatDialog,
              private breakpointObserver: BreakpointObserver) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    this.sub.add(
      this.breakpointObserver
        .observe(['(max-width: 692px)'])
        .subscribe((result) => {
          if (result.matches) {
            this.tableColumns = this.displayedColumnsMobile;
          } else {
            this.tableColumns = this.displayedColumns;
          }
        })
    );
    this.sub.add(this.authService.currentUser.subscribe(res => {
      this.patientId = res.id.slice(1,-1);
    }))
    this.getData();
  }

  getData() {
    this.sub.add(this.visitService.getVisits().subscribe((res: VisitResponseModel[]) => {
      res = res.filter(ele => ele.patientId === this.patientId);
      let itemList: VisitListItemModel[] = res.map((ele: VisitResponseModel) => {
        return {...ele, dateTimeEnd: this.visitService.dateObject(ele.dateTimeEnd), dateTimeStart: this.visitService.dateObject(ele.dateTimeStart)}
      })
      itemList = this.sortVisits(itemList);
   
      res = itemList.map((ele: VisitListItemModel) => {
        return {...ele, dateTimeEnd: ele.dateTimeEnd.toLocaleString(), dateTimeStart: ele.dateTimeStart.toLocaleString()}
      })
      this.dataSource = new MatTableDataSource(res)
      this.dataSource.paginator = this.paginator;
    }))
  }

  applyFilter($event) {
    const filterValue = ($event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  sortVisits(list: VisitListItemModel[]): VisitListItemModel[] {
    return list.sort((a, b) => {
      if (a.dateTimeEnd.getTime() > b.dateTimeEnd.getTime()) {
        return -1;
      }
      if (a.dateTimeEnd.getTime() < b.dateTimeEnd.getTime()) {
          return 1;
      }
      return 0;
    })
  }

  openDetails(id: string) {
    console.log(id)
  }

  deleteVisit(id: string) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {
        text: "Czy na pewno chcesz usunąć wizytę?"
      }
    });
    this.sub.add(dialogRef.afterClosed().subscribe(res => {
      if(res) {
        this.sub.add(this.visitService.deleteVisit(id).subscribe(res => {
          this.snackBar.success('Wizyta usunięta');
          this.getData();
        }, err => {
          this.snackBar.error('Wizyta nie została usunięta');
        }))
      }
    }))
  }
}
