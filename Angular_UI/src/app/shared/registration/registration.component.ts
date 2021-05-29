import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {ParentErrorStateMatcher, PasswordValidator} from '../validators';
import {Patient} from '../../models/patient'
import { AuthService } from './../../services/auth.service';
import { Router } from '@angular/router';

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
  user: Patient;
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
    'phoneNumber': [
      { type: 'required', message: 'Phone number is required' },
      { type: 'pattern', message: 'Enter a valid phone number' }
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


  constructor(private fb: FormBuilder,private authService: AuthService,private router: Router) { }
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

    this.accountDetailsForm = this.fb.group({
      firstName: new FormControl('', Validators.compose([
        Validators.required,
        ])),
      lastName: new FormControl('', Validators.compose([
        Validators.required,

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
      phoneNumber:new FormControl('', Validators.compose([
        Validators.pattern(''),
       ])),

      cardNumber: new FormControl('', Validators.compose([
        Validators.pattern(''),
       ])),
      address: this.fb.group({
        country:new FormControl('', Validators.compose([
          Validators.pattern(''),
         ])),
        region: new FormControl('', Validators.compose([
          Validators.pattern(''),
         ])),
        city: new FormControl('', Validators.compose([
          Validators.pattern(''),
         ])),
        postalCode: new FormControl('', Validators.compose([
          Validators.pattern(''),
         ])),
        street: new FormControl('', Validators.compose([
          Validators.pattern(''),
         ])),
        houseNr: new FormControl('', Validators.compose([
          Validators.pattern(''),
         ])),
        roomNr: new FormControl('', Validators.compose([
          Validators.pattern(''),
         ])),
        information:new FormControl('', Validators.compose([
          Validators.pattern(''),
         ])),
      }),
      matching_passwords: this.matching_passwords_group,
      terms: new FormControl(false, Validators.pattern('true'))
    })

  }
  register(userRegester: Patient){
    this.authService.register(userRegester).subscribe(
      ()=>{
        this.router.navigate(['/login']);
      }
    )
  }
  onSubmitAccountDetails(value){
    this.user = value;
    this.user.password= value.matching_passwords.password;
    this.user.phoneNumber = ['+24312312124'];
    this.user.roles = [];
    var role = this.register(this.user);
    console.log(this.user);
  }

}
