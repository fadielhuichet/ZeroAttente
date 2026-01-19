import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ClientAuthService } from '../../services/auth/client-auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-client-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './client-login.html',
  styleUrl: './client-login.css',
})
export class ClientLogin {
  username = '';
  password = '';
  error: string | null = null;

  constructor(private auth: ClientAuthService, private router: Router) {}

  onSubmit() {
    this.error = null;
    this.auth.login(this.username, this.password).subscribe({
      next: () => {

        Swal.fire({
          icon: 'success',
          title: 'Login Successful',
          text: 'Redirecting to the client organisations',
          timer: 2000,
          showConfirmButton: false,
        });
        this.auth.me().subscribe({
          next: (me) => this.router.navigate(['/organisations']),
          error: () => this.router.navigate(['/organisations'])
        });
      },
      error: (err) => {
        this.error = 'Invalid credentials';
          Swal.fire({
            icon: 'error',
            title: 'Login Failed',
            text: 'Invalid username or password.',
            confirmButtonText: 'Try Again',
        });
      }
    });
  }
}

