import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AdminAuthService } from '../../services/auth/admin-auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './admin-login.html',
  styleUrls: ['./admin-login.css'],
})
export class AdminLogin {
  email = '';
  password = '';
  error: string | null = null;

  constructor(private auth: AdminAuthService, private router: Router) {}

  onSubmit() {
    this.error = null;
    this.auth.login(this.email, this.password).subscribe({
      next: () => {
        // Show success alert
        Swal.fire({
          icon: 'success',
          title: 'Login Successful',
          text: 'Redirecting to the admin dashboard...',
          timer: 2000,
          showConfirmButton: false,
        });

        // Retrieve admin details after login and redirect
        this.auth.me().subscribe({
          next: (me) => this.router.navigate(['/admin']),
          error: () => this.router.navigate(['/admin']),
        });
      },
      error: () => {
        this.error = 'Invalid credentials';

        // Show error alert
        Swal.fire({
          icon: 'error',
          title: 'Login Failed',
          text: 'Invalid Email or password.',
          confirmButtonText: 'Try Again',
        });
      },
    });
  }
}
