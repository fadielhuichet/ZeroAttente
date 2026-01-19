import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ClientAuthService } from '../../services/auth/client-auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-client-signup',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './client-signup.html',
  styleUrl: './client-signup.css',
})
export class ClientSignup {
  username = '';
  password = '';
  passwordConfirm = '';
  nom = '';
  prenom = '';
  error: string | null = null;

  constructor(private auth: ClientAuthService, private router: Router) {}

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
    if (!this.username) {
      this.error = 'Username required';
      return;
    }
    const payload = {
      username: this.username,
      password: this.password,
      nom: this.nom,
      prenom: this.prenom
    };
    this.auth.signup(payload).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Sign up Successful',
          text: 'Redirecting to the client login',
          timer: 2000,
          showConfirmButton: false,
        });
        this.router.navigate(['/client/login'])
      },
      error: (err) => {
        this.error = err?.error || 'Signup failed';
      }
    });
  }
}

