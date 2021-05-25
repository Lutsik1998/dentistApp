import { Component, forwardRef, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, NG_VALUE_ACCESSOR, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReviewService } from 'src/app/services/review.service';
import { SnackbarService } from 'src/app/services/snackbar.service';

@Component({
  selector: 'app-rate-visit',
  templateUrl: './rate-visit.component.html',
  styleUrls: ['./rate-visit.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => RateVisitComponent),
      multi: true
    }
  ]
})
export class RateVisitComponent implements OnInit {

  form = this.fb.group({
    text: [this.data.visit.review?.text],
    rating: [this.data.visit.review?.rating]
  })

  isLoading: boolean = false;

  constructor(@Inject(MAT_DIALOG_DATA) public data, 
              public dialogRef: MatDialogRef<RateVisitComponent>,
              private fb: FormBuilder,
              private reviewService: ReviewService,
              private snackbar: SnackbarService) { }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close();
  }

  delete() {
    this.isLoading = true;
    this.reviewService.deleteVisit(this.data.visit.id).subscribe(res => {
      this.snackbar.success('Ocena usunięta');
      this.isLoading = false;
      this.dialogRef.close();
    }, err => {
      this.snackbar.error('Ocena nie została usunięta');
      this.isLoading = false;
    })
  }

  save() {
    this.isLoading = true;
    this.reviewService.rateVisit(this.data.visit.id, this.form.getRawValue()).subscribe(res => {
      this.snackbar.success('Wizyta oceniona')
      this.isLoading = false;
      this.dialogRef.close();
    }, err => {
      this.snackbar.error('Ocena nie powiodła się')
      this.isLoading = false;
    })
  }
}
