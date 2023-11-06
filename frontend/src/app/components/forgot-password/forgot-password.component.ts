import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatStepper } from '@angular/material/stepper';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css'],
})
export class ForgotPasswordComponent {
  @ViewChild('stepper') stepper!: MatStepper;
  loading = false;
  emailForm!: FormGroup;
  resetForm!: FormGroup;
  hidePassword = true;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private _snackBar: MatSnackBar,
    private _Router: Router
  ) {}

  ngOnInit() {
    this.emailForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
    });

    this.resetForm = this.formBuilder.group({
      email: [''],
      otp: ['', [Validators.required, Validators.pattern(/^\d{6}$/)]],
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
  }

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  requestOTP() {
    if (this.emailForm.valid) {
      this.loading = true;
      this.authService.forgotPassword(this.emailForm.value.email).subscribe(
        (res: any) => {
          console.log(res);
          this.loading = false;
          this.openSnack('Verification Email Sent !');
          this.resetForm.get('email')?.setValue(this.emailForm.value.email);
          this.openSnack('Verification Email Sent !');
          this.stepper.next();
        },
        (err: any) => {
          this.loading = false;
          console.log(err.error);
          this.openSnack(err.error);
        }
      );
    }
  }

  resetPassword() {
    if (this.resetForm.valid) {
      this.loading = true;
      this.authService.resetPassword(this.resetForm.value).subscribe(
        (res: any) => {
          this.loading = false;
          this.openSnack('Password changed successfully !');
          this._Router.navigate(['/login']);
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
