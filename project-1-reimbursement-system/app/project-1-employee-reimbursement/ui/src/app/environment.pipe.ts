import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'environment'
})
export class EnvironmentPipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    return null;
  }

}
