import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AdminAuthService } from '../../services/auth/admin-auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-admin-signup',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './admin-signup.html',
  styleUrl: './admin-signup.css',
})
export class AdminSignup {
  email = '';
  password = '';
  passwordConfirm = '';
  organizationName = '';
  organizationLocation = '';
  organizationCode = '';
  phone = '';
  error: string | null = null;

  constructor(private auth: AdminAuthService, private router: Router) {}

  onSubmit() {
    this.error = null;
    if (!this.password) {
      this.error = 'Password required';
      return;
    }
    if (this.password !== this.passwordConfirm) {
      this.error = 'Passwords do not match';
      return;
    }
    if (!this.email) {
      this.error = 'Email required';
      return;
    }
    const payload = {
      email: this.email,
      password: this.password,
      organizationName: this.organizationName,
      organizationLocation: this.organizationLocation,
      organizationCode: this.organizationCode,
      phone: this.phone
    };
    this.auth.signup(payload).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Sign up Successful',
          text: 'Redirecting to the admin login',
          timer: 2000,
          showConfirmButton: false,
        });
        this.router.navigate(['/admin/login'])
      },
      error: (err) => {
        this.error = err?.error || 'Signup failed';

        Swal.fire({
          icon: 'error',
          title: 'Login Failed',
          text: 'Signup failed',
          confirmButtonText: 'Try Again',
        });
      }
    });
  }
}

