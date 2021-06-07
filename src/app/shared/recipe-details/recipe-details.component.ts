import { ThisReceiver } from '@angular/compiler';
import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models/recipe';
import { RateVisitComponent } from 'src/app/patient/history-view/rate-visit/rate-visit.component';
import { RecipeService } from 'src/app/services/recipe.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ImageShowComponent } from '../image-show/image-show.component';

@Component({
  selector: 'app-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.scss']
})
export class RecipeDetailsComponent implements OnInit, OnDestroy {


  form = this.fb.group({
    recipeNumber: ['', Validators.required],
    codeMFI: ['', [Validators.required, Validators.pattern('((0[1-9])|(1[0-6]))')]],
    drugs: ['', Validators.required],
    dateTime: [''],
    lastDate: ['', Validators.required],
    additionalProperties: ['', Validators.required],
    payment: ['', [Validators.required, Validators.pattern('[0-9][0-9]?$|^100')]],
  })
  sub = new Subscription();
  file: File | null = null;
  imageUrl: SafeUrl;
  edit: boolean = false;
  show: boolean = false;
  recipe: Recipe;
  constructor(private fb: FormBuilder,
              @Inject(MAT_DIALOG_DATA) public data, 
              public dialogRef: MatDialogRef<RateVisitComponent>,
              private recipeService: RecipeService,
              private sanitizer:DomSanitizer,
              private snackbar: SnackbarService,
              private dialog: MatDialog) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    if(this.data.recipe) {
      this.recipe = this.data.recipe;
      this.recipeService.getImage(this.data.visitId, this.recipe.id).subscribe(res => {
        const unsafeImageUrl = URL.createObjectURL(res);
        this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(unsafeImageUrl);
      })
      this.edit = true;
      this.form.patchValue({
        ...this.recipe,
        payment: this.recipe.payment.slice(0,-1)
      })
    }
    if(this.data.show) {
      this.show = true;
      this.form.disable();
    }
  }

  close() {
    this.dialogRef.close();
  }

  editRecipe() {
    if(this.show) {
      this.dialogRef.close();
      return;
    }
    if(this.form.invalid) {
      this.snackbar.error('Formularz nieprawidłowo wypełniony')
      return;
    }
    const data = {
      ...this.recipe,
      ...this.form.getRawValue(),
      payment: this.form.get('payment').value.toString().concat('%'),
    }
    this.sub.add(this.recipeService.editRecipe(this.data.visitId, this.recipe.id, data).subscribe(res => {
      if(this.file) {
        this.sub.add(this.recipeService.addImage(this.data.visitId, this.recipe.id, this.file).subscribe(res => {
          this.snackbar.success('Recepta zmieniona')
          this.dialogRef.close(true);
        }, err => {
          if(err.statusText === "OK") {
            this.snackbar.success('Recepta zmieniona')
            this.dialogRef.close(true);
          }
        }))      
      } else {
        this.snackbar.success('Recepta zmieniona')
        this.dialogRef.close(true);
      }
    }))
    
  }

  showImage() {
    if(!this.show) {
      return;
    }
    const dialogRef = this.dialog.open(ImageShowComponent, {
      data: {
        url: this.imageUrl
      }
    });
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
    
    this.sub.add(this.recipeService.addRecipe(this.data.visitId, data).subscribe(res => {
      this.sub.add(this.recipeService.addImage(this.data.visitId, res.id, this.file).subscribe(res => {
        this.snackbar.success('Recepta dodana')
        this.dialogRef.close(true);
      }))
    }))
  }

  onSelect(event) {
    this.file = event.addedFiles[0];
  }
  
  onRemove() {
    this.file = null
  }
}
