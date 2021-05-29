import { BreakpointObserver } from '@angular/cdk/layout';
import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { DoctorInfoResponseModel } from 'src/app/models/doctor';
import { DoctorService } from 'src/app/services/doctor.service';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { MatCalendarCellCssClasses } from '@angular/material/datepicker';
import { MatListOption } from '@angular/material/list';
import { Visit } from 'src/app/models/visit';
import { OfficeService } from 'src/app/services/office.service';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user';

import { PatientService } from 'src/app/services/patient.service';
import { VisitService } from 'src/app/services/visit.service';
import { MatTabGroup } from '@angular/material/tabs';
import { MatDialog } from '@angular/material/dialog';
import { DoctorReviewsComponent } from 'src/app/shared/doctor-reviews/doctor-reviews.component';

@Component({
  selector: 'app-doctor-view',
  templateUrl: './doctor-view.component.html',
  styleUrls: ['./doctor-view.component.scss'],
})
export class DoctorViewComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  tableColumns: string[] = [];
  displayedColumns: string[] = [
    'reviews',
    'firstName',
    'lastName',
    'sex',
    'email',
    'visit',
  ];
  displayedColumnsMobile: string[] = [
    'reviews',
    'firstName',
    'lastName',
    'visit',
  ];
  dataSource: MatTableDataSource<DoctorInfoResponseModel>;
  sub: Subscription = new Subscription();
  closeResult = '';
  visitForm: Visit;
  user: User;
  constructor(
    private visitService: VisitService,
    private authService: AuthService,
    private patientService: PatientService,
    private officeService: OfficeService,
    private modalService: NgbModal,
    private breakpointObserver: BreakpointObserver,
    private doctorService: DoctorService,
    public dialog: MatDialog,
  ) {}

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    this.visitForm = {
      dateTimeStart: '',
      dateTimeEnd: '',
      information: '',
      doctorId: '',
      patientId: '',
      officeId: '',
      review:{
        text: '',
        rating:0
      }
    };

    this.officeService.getOffice().subscribe((res) => {
      this.visitForm.officeId = res[0].id;
    });
    this.authService.currentUser.subscribe((res) => {
      this.user = res;
    });
    this.patientService
      .getPatientByEmail(this.user.email)
      .subscribe((patient) => {
        this.visitForm.patientId = patient.id;
      });
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
    this.sub.add(
      this.doctorService.getDoctors().subscribe((res) => {
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
      })
    );
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
  open(content, doctorId) {
    this.visitForm.doctorId = doctorId;
    this.modalService
      .open(content, { ariaLabelledBy: 'modal-basic-title', size: 'lg' })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
          this.selectedDate.setHours(Number(this.selectedTime.substring(0, 2)));
          this.selectedDate.setMinutes(
            Number(this.selectedTime.substring(3, 5))
          );

          this.visitForm.dateTimeStart = this.selectedDate
            .toISOString()
            .substring(0, 16);
          this.selectedDate.setMinutes(
            Number(this.selectedTime.substring(3, 5)) + 20);
          this.visitForm.dateTimeEnd = this.selectedDate
            .toISOString()
            .substring(0, 16);
          console.log(this.visitForm);
          this.visitService.addVisit(this.visitForm).subscribe(() => {
            console.log(this.visitForm);
          });
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  dateClass() {
    return (date: Date): MatCalendarCellCssClasses => {
      if (date.getDate() === 1) {
        return 'special-date';
      } else {
        return;
      }
    };
  }

  typesOfTime: string[] = [
    '10:00',
    '10:30',
    '11:00',
    '11:30',
    '12:00',
    '12:30',
    '13:00',
    '13:30',
    '14:00',
  ];
  selectedDate = new Date();
  selectedTime: string;

  onSelect(event, tabGroup: MatTabGroup) {
    this.selectedDate = event;
    tabGroup._tabs.toArray()[1].disabled = false;
    tabGroup.selectedIndex = 1;
  }
  onGroupsChange(options: MatListOption[], tabGroup: MatTabGroup) {
    // map these MatListOptions to their values
    tabGroup._tabs.toArray()[2].disabled = false;
    tabGroup.selectedIndex = 2;
    options.map((o) => (this.selectedTime = o.value));
  }

  onchange(text) {
    var element = <HTMLInputElement>document.getElementById('save');
    element.disabled = false;
    console.log(text);
    this.visitForm.information = text;
  }
}
