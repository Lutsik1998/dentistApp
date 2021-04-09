import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {ParentErrorStateMatcher, PasswordValidator} from '../validators';
@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})

export class RegistrationComponent implements OnInit {

  accountDetailsForm: FormGroup;

  matching_passwords_group: FormGroup;
  parentErrorStateMatcher = new ParentErrorStateMatcher();
  genders =  ['Mężczyzna','Kobieta'];

  account_validation_messages = {
    'firstName': [
      { type: 'required', message: 'first Name is required' },
      { type: 'pattern', message: 'Enter a valid first Name' }
    ],
    'lastName': [
      { type: 'required', message: 'last Name is required' },
      { type: 'pattern', message: 'Enter a valid last Name' }
    ],
    'email': [
      { type: 'required', message: 'Email is required' },
      { type: 'pattern', message: 'Enter a valid email' }
    ],
    'pesel': [
      { type: 'required', message: 'pesel is required' },
      { type: 'pattern', message: 'Enter a valid pesel' }
    ],
    'birthDate': [
      { type: 'required', message: 'Date of birth is required' },
      { type: 'pattern', message: 'Enter a valid Date of birth' }
    ],
    'confirm_password': [
      { type: 'required', message: 'Confirm password is required' },
      { type: 'areEqual', message: 'Password mismatch' }
    ],
    'password': [
      { type: 'required', message: 'Password is required' },
      { type: 'minlength', message: 'Password must be at least 5 characters long' },
      { type: 'pattern', message: 'Your password must contain at least one uppercase, one lowercase, and one number' }
    ],
    'terms': [
      { type: 'pattern', message: 'You must accept terms and conditions' }
    ]
  }


  constructor(private fb: FormBuilder) { }
  ngOnInit(): void {
    this.createForms();
  }
  createForms() {
    // matching passwords validation
    this.matching_passwords_group = new FormGroup({
      password: new FormControl('', Validators.compose([
        Validators.minLength(5),
        Validators.required,
        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$')
      ])),
      confirm_password: new FormControl('', Validators.required)
    }, (formGroup: FormGroup) => {
      return PasswordValidator.areEqual(formGroup);
    });

    // user details form validations

    // user links form validations
    this.accountDetailsForm = this.fb.group({
      firstName: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('[a-zA-Z]')
        ])),
      lastName: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('[a-zA-Z]')
       ])),
      pesel: new FormControl('', Validators.compose([
        Validators.maxLength(25),
        Validators.minLength(5),
        Validators.pattern(''),
        Validators.required
       ])),
      birthDate:new FormControl('', Validators.compose([
        Validators.pattern(''),
        Validators.required
       ])),
      sex:new FormControl('', Validators.compose([
        Validators.pattern(''),
       ])),
      email: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')
      ])),
      phoneNumber: new FormControl('', Validators.compose([
        Validators.pattern(''),
       ])),
      cardNumber: new FormControl('', Validators.compose([
        Validators.pattern(''),
       ])),
      addres: new FormControl('', Validators.compose([
        Validators.pattern(''),
       ])),
      matching_passwords: this.matching_passwords_group,
      terms: new FormControl(false, Validators.pattern('true'))
    })

  }

  onSubmitAccountDetails(value){
    console.log(value);
  }

}
