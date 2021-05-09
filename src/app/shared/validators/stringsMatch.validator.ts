import { ValidatorFn, AbstractControl, ValidationErrors } from "@angular/forms";

export const StringsMatch = (stringOne: string, stringTwo: string): ValidatorFn =>
    (controls: AbstractControl): ValidationErrors | null => {
        const str = controls.get(stringOne);
        const strCompare = controls.get(stringTwo);
        if(str?.value !== strCompare?.value) {
            return { stringMismatch: true };
        }

        return null;
    };