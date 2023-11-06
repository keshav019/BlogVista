import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatStepper } from '@angular/material/stepper';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  @ViewChild('stepper') stepper!: MatStepper;
  registerForm!: FormGroup;
  otpForm!: FormGroup;
  hidePassword = true;
  loading = false;

  constructor(
    private formBuilder: FormBuilder,
    private _snackBar: MatSnackBar,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      firstname: ['', [Validators.required]],
      lastname: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern(
            /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\S+$).*$/
          ),
        ],
      ],
    });

    this.otpForm = this.formBuilder.group({
      email: [''],
      otp: ['', [Validators.required, Validators.pattern(/^\d{6}$/)]],
    });
  }

  // Function to toggle password visibility
  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  // Function to submit the form
  onSubmit() {
    if (this.registerForm.valid) {
      this.loading = true;
      this.authService.register(this.registerForm.value).subscribe(
        (res: User) => {
          this.loading = false;
          this.openSnack('Verification Email Sent !');
          this.otpForm.get('email')?.setValue(res.email);
          this.stepper.next();
        },
        (err: any) => {
          this.loading = false;
          this.openSnack(err.error);
        }
      );
    }
  }

  verifyEmail() {
    if (this.otpForm.valid) {
      this.loading = true;
      console.log(this.otpForm.value);
      this.authService.verifyEmail(this.otpForm.value).subscribe(
        (res: any) => {
          this.loading = false;
          this.openSnack('Account created Successfully !');
          this.router.navigate(['/login']);
        },
        (err: any) => {
          this.loading = false;
          this.openSnack(err.error);
        }
      );
    }
  }

  openSnack(message: string) {
    this._snackBar.open(message, 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
}
