import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { DoctorInfoResponseModel } from 'src/app/models/doctor';
import { Review } from 'src/app/models/visit';
import { ReviewService } from 'src/app/services/review.service';

@Component({
  selector: 'app-doctor-reviews',
  templateUrl: './doctor-reviews.component.html',
  styleUrls: ['./doctor-reviews.component.scss']
})
export class DoctorReviewsComponent implements OnInit {

  sub = new Subscription();
  doctor: DoctorInfoResponseModel;
  reviews: Review[] = [];
  avg: number | null = null;

  constructor(@Inject(MAT_DIALOG_DATA) public data, 
    public dialogRef: MatDialogRef<DoctorReviewsComponent>,
    private reviewService: ReviewService) { }

  ngOnInit(): void {
    this.doctor = this.data.doctor;
    this.sub.add(this.reviewService.getReviews(this.doctor.id).subscribe(res => {
      this.reviews = res;
      if(this.reviews.length) {
        this.avg = this.reviews.reduce((total, next) => total + next.rating, 0) / this.reviews.length;
      }
    }))
  }

}
