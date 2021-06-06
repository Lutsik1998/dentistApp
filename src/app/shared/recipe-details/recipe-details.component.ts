import { ThisReceiver } from '@angular/compiler';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RateVisitComponent } from 'src/app/patient/history-view/rate-visit/rate-visit.component';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.scss']
})
export class RecipeDetailsComponent implements OnInit {


  form = this.fb.group({
    recipeNumber: ['', Validators.required],
    codeMFI: ['', [Validators.required, Validators.pattern('((0[1-9])|(1[0-6]))')]],
    drugs: ['', Validators.required],
    dateTime: [''],
    lastDate: ['', Validators.required],
    additionalProperties: ['', Validators.required],
    payment: ['', [Validators.required, Validators.pattern('[0-9][0-9]?$|^100')]],
  })

  constructor(private fb: FormBuilder,
              @Inject(MAT_DIALOG_DATA) public data, 
              public dialogRef: MatDialogRef<RateVisitComponent>,
              private recipeService: RecipeService) { }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close();
  }


  save() {
    if(this.form.invalid || !this.file) {
      console.log(this.form)
      return;
    }
    const data = {
      ...this.form.getRawValue(),
      dateTime: (new Date()).toISOString().slice(0,16),
      payment: this.form.get('payment').value.toString().concat('%')
    }
    
    this.recipeService.addRecipe(this.data.visitId, data).subscribe(res => {
      console.log(res)
      this.recipeService.addImage(this.data.visitId, res.id, this.file).subscribe(res => {
        console.log(res)
      })
    })
  }

  file: File | null = null;

  onSelect(event) {
    console.log(event);
    this.file = event.addedFiles[0];
  }
  
  onRemove() {
    this.file = null
  }
}
