import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  constructor(private snackBar: MatSnackBar) { }

  success(message: string) {
    this.snackBar.open(message, 'Sukces', {
      duration: 2000,
      panelClass: 'snack-success',
    })
  }

  error(message: string) {
    this.snackBar.open(message, 'Błąd', {
      duration: 2000,
      panelClass: 'snack-error',
    })
  }

  info(message: string) {
    this.snackBar.open(message, 'Informacja', {
      duration: 2000,
      panelClass: 'snack-info',
    })
  }
}
