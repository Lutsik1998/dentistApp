import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'stringDate'
})
export class StringDatePipe implements PipeTransform {

  transform(value: string): string {
    const temp = new Date(value);
    console.log(temp)
    return temp.toLocaleString();
  }

}
