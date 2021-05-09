import { Pipe, PipeTransform } from '@angular/core';
import { Gender } from '../enums/various.enum';

@Pipe({
  name: 'gender'
})
export class GenderPipe implements PipeTransform {

  transform(value: string): string {
    switch(value) {
      case Gender.man:
        return 'M'
      case Gender.woman:
        return 'K'
    }
    return null;
  }
}
