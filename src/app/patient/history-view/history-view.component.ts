import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { elementAt } from 'rxjs/operators';
import { VisitListItemModel, VisitResponseModel } from 'src/app/models/visit';
import { AuthService } from 'src/app/services/auth.service';
import { VisitService } from 'src/app/services/visit.service';

@Component({
  selector: 'app-history-view',
  templateUrl: './history-view.component.html',
  styleUrls: ['./history-view.component.scss']
})
export class HistoryViewComponent implements OnInit, OnDestroy {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  tableColumns: string[] = ['start', 'end', 'info'];
  dataSource: MatTableDataSource<VisitResponseModel>;

  sub = new Subscription();
  patientId: string;

  constructor(private visitService: VisitService, private authService: AuthService) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    this.sub.add(this.authService.currentUser.subscribe(res => {
      this.patientId = res.id.slice(1,-1);
      console.log(this.patientId)
    }))
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
}
