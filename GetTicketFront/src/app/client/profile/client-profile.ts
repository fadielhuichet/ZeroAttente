import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientAuthService } from '../../services/auth/client-auth.service';

@Component({
  selector: 'app-client-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './client-profile.html',
  styleUrls: ['./client-profile.css']
})
export class ClientProfile implements OnInit {
  nom = '';
  prenom = '';
  username = '';
  currentPassword = '';
  newPassword = '';
  confirmPassword = '';
  
  loading = true;
  saving = false;
  error: string | null = null;
  success: string | null = null;

  constructor(
    private clientAuth: ClientAuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    console.log('ClientProfile component initialized');
    this.clientAuth.isLoggedIn().subscribe((logged) => {
      console.log('Is logged in:', logged);
    });
    this.loadProfile();
  }

  loadProfile() {
    this.loading = true;
    this.clientAuth.getProfile().subscribe({
      next: (profile: any) => {
        console.log('Profile loaded:', profile);
        this.nom = profile.nom || '';
        this.prenom = profile.prenom || '';
        this.username = profile.username || '';
        this.loading = false;
        this.error = null;
      },
      error: (err: any) => {
        console.error('Profile load error:', err);
        console.error('Error status:', err.status);
        console.error('Error message:', err.message);
        if (err.status === 401) {
          this.error = 'Unauthorized - Please log in again';
        } else if (err.status === 404) {
          this.error = 'Profile not found';
        } else {
          this.error = err.error || 'Failed to load profile';
        }
        this.loading = false;
      }
    });
  }

  onSubmit() {
    this.error = null;
    this.success = null;

    if (this.newPassword && this.newPassword !== this.confirmPassword) {
      this.error = 'New passwords do not match';
      return;
    }

    if (this.newPassword && !this.currentPassword) {
      this.error = 'Current password is required to change password';
      return;
    }

    this.saving = true;
    const updateData: any = {
      nom: this.nom,
      prenom: this.prenom,
      username: this.username
    };

    if (this.newPassword) {
      updateData.currentPassword = this.currentPassword;
      updateData.newPassword = this.newPassword;
    }

    this.clientAuth.updateProfile(updateData).subscribe({
      next: () => {
        this.success = 'Profile updated successfully';
        this.saving = false;
        this.currentPassword = '';
        this.newPassword = '';
        this.confirmPassword = '';
      },
      error: (err: any) => {
        this.error = err.error || 'Failed to update profile';
        this.saving = false;
      }
    });
  }
}
