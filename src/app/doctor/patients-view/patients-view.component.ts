import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, NgModule, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatCalendarCellCssClasses } from '@angular/material/datepicker';
import { MatDialog } from '@angular/material/dialog';
import { MatListOption } from '@angular/material/list';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatTabGroup } from '@angular/material/tabs';
import { Router } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { UserRole } from 'src/app/enums/various.enum';
import { PatientInfoResponseModel } from 'src/app/models/patient';
import { CurrentUser, User } from 'src/app/models/user';
import { Visit } from 'src/app/models/visit';
import { AuthService } from 'src/app/services/auth.service';
import { DoctorService } from 'src/app/services/doctor.service';
import { OfficeService } from 'src/app/services/office.service';
import { PatientService } from 'src/app/services/patient.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { VisitService } from 'src/app/services/visit.service';
import { ConfirmationDialogComponent } from 'src/app/shared/confirmation-dialog/confirmation-dialog.component';
@Component({
  selector: 'app-patients-view',
  templateUrl: './patients-view.component.html',
  styleUrls: ['./patients-view.component.scss'],
})

export class PatientsViewComponent implements OnInit, OnDestroy {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  tableColumns: string[] = [
    'cardNumber',
    'firstName',
    'lastName',
    'sex',
    'email',
    'visit',
  ];
  dataSource: MatTableDataSource<PatientInfoResponseModel>;
  sub: Subscription = new Subscription();
  closeResult = '';
  visitForm: Visit;
  user: CurrentUser;
  listFreeTime: Visit[]=[];
  get isAdmin(): boolean {
    return this.authService.getRole() == UserRole.admin;
  }
  listFreeDays: string[]=[];
  tzoffset = (new Date()).getTimezoneOffset() * 60000;
  constructor(
    private visitService: VisitService,
    private officeService: OfficeService,
    private authService: AuthService,
    private patientService: PatientService,
    private doctorService: DoctorService,
    private modalService: NgbModal,
    private router: Router,
    private snackbar: SnackbarService,
    public dialog: MatDialog
  ) {}

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    if(this.isAdmin) {
      this.tableColumns.push('delete')
    }
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
    this.doctorService
    .getDoctorByEmail(this.user.email)
    .subscribe((doctor) => {
      this.visitForm.doctorId = doctor.id;
    });
    this.getData();
  }

  getData() {
    this.sub.add(
      this.patientService.getPatients().subscribe((res) => {
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
      })
    );
  }

  applyFilter($event) {
    const filterValue = ($event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  addPatient() {
    this.router.navigate(['doctor/add-patient']);
  }

  deletePatient(id: string) {

    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {
        text: "Czy na pewno chcesz usunąć pacjenta?"
      }
    });
    this.sub.add(dialogRef.afterClosed().subscribe(res => {
      if(res) {
        this.sub.add(this.patientService.deletePatient(id).subscribe(res => {
          this.snackbar.success('Pacjent usunięty')
          this.getData();
        }, err => {
          this.snackbar.error('Pacjent nie został usunięty')
        }))
      }
    }))
  }

  openDetails(id: string) {
    this.router.navigate([`doctor/patient-details/${id}`]);
  }

  open(content, patientId) {
    this.visitForm.patientId = patientId;
    this.visitService.getFreeDays(this.visitForm.doctorId,new Date().toISOString().split('T')[0],90,20).subscribe((res) =>{
      this.listFreeDays = res;
      
    });
    this.modalService
      .open(content, { ariaLabelledBy: 'modal-basic-title', size: 'lg' })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
          this.selectedDate.setHours(Number(this.visitForm.dateTimeStart.substring(0, 2)));
          this.selectedDate.setMinutes(
            Number(this.visitForm.dateTimeStart.substring(3, 5))
          );

          this.visitForm.dateTimeStart = (new Date(this.selectedDate.getTime() - this.tzoffset)) 
          .toISOString()
          .substring(0, 16);
          this.selectedDate.setHours(Number(this.visitForm.dateTimeEnd.substring(0, 2)));
          this.selectedDate.setMinutes(
            Number(this.visitForm.dateTimeEnd.substring(3, 5))
          );
          this.visitForm.dateTimeEnd =(new Date(this.selectedDate.getTime() - this.tzoffset)) 
          .toISOString()
          .substring(0, 16);
          console.log(this.visitForm);
          this.visitService.addVisit(this.visitForm).subscribe(() => {
          });
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
  }
  myFilter = (d: Date): boolean => {

    //console.log( (new Date("2021-06-10").getTime() - this.tzoffset),d.getTime(),d,(new Date("2021-06-10").getTime() - this.tzoffset) ==d.getTime());
    //console.log(this.listFreeDays.some(e=> e == d.toISOString().substring(0,10)),(d.getDay() !== 0 && d.getDay() !== 6),d.toISOString().substring(0,10));
    //== d.toISOString().substring(0,10)
    d = new Date(d.getTime() - this.tzoffset);
    return (d.getDay() !== 0 && d.getDay() !== 6) && this.listFreeDays.some(e=> 
    new Date(e).toISOString().substring(0,10) == d.toISOString().substring(0,10));
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

  selectedDate = new Date();
  selectedTime: string;
  typesOfTime: Map<string, boolean>;
  keys: Array<string>;
  onSelect(event, tabGroup: MatTabGroup) {
    this.selectedDate = event;
    this.typesOfTime = new  Map<string, boolean>([
      ['08:00',false],
      ['08:30',false],
      ['09:00',false],
      ['09:30',false],
      ['10:00',false],
      ['10:30',false],
      ['11:00',false],
      ['11:30',false],
      ['12:00',false],
      ['12:30',false],
      ['13:00',false],
      ['13:30',false],
      ['14:00',false],
      ['15:00',false],
      ['15:30',false],
      ['16:00',false],
      ['16:30',false],
      ['17:00',false],
      ['17:30',false],
      ['18:00',false],
      ['18:30',false],
      ['19:00',false],
      ['19:30',false]
    ]);
    this.keys= Array.from( this.typesOfTime.keys());
    var dateTimeStart = new Date(this.selectedDate);
    var dateTimeEnd = new Date(this.selectedDate);
    dateTimeStart.setHours(0);
    dateTimeEnd.setDate(dateTimeStart.getDate()+1);
    console.log(dateTimeStart.toISOString().substring(0,16), dateTimeEnd.toISOString().substring(0,16));
    this.visitService.getFreeTime(this.visitForm.doctorId,dateTimeStart.toISOString().substring(0,16),dateTimeEnd.toISOString()).subscribe((res) =>{
      this.listFreeTime = res;
      console.log(this.listFreeTime);
      for (let i = 0; i < this.listFreeTime.length; i++) {
        for (let j = 0; j < this.keys.length; j++) {
          var temp = new Date(this.listFreeTime[i].dateTimeStart);
          temp.setHours(Number(this.keys[j].substring(0,2))+2);
          temp.setMinutes(Number(this.keys[j].substring(3,5)));
          var start =new Date(this.listFreeTime[i].dateTimeStart);
          var end = new Date(this.listFreeTime[i].dateTimeEnd);
          start = (new Date(start.getTime() - this.tzoffset));
          end = (new Date(end.getTime()- this.tzoffset));

          if (temp.getTime() >= start.getTime() && temp.getTime() <= end.getTime()) {
            console.log(this.typesOfTime.get(this.keys[j]));
            this.typesOfTime.set(this.keys[j],true);
         
          }
        }
       }
      
    })
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
  timeStartChange(timestart:string){
    console.log(timestart);
    this.visitForm.dateTimeStart = timestart;
  }
  timeEndChange(timeend:string){
    console.log(timeend);
    this.visitForm.dateTimeEnd = timeend;
  }
  selectionModeActive:boolean = false;
  flag:number = 0;
  mouseoverFlag:boolean = true;
  mousedownEvent(event:any,time:string){
    if (this.flag == 0) {
      this.visitForm.dateTimeStart = time;
      console.log( this.visitForm.dateTimeStart);
      this.flag = 1;
    }
    else if (this.flag == 1) {
      this.visitForm.dateTimeEnd = time;
      console.log( this.visitForm.dateTimeEnd);
      this.flag = 0;
    }
    event.target.style.background = 'green';
    console.log(event);
    this.selectionModeActive = !this.selectionModeActive;
  }
  mouseoverEvent(event:any){
    if(this.selectionModeActive){
      event.target.style.background = 'green';
      this.mouseoverFlag = false;
    }


  }
}
