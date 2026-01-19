import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminAuthService } from '../../services/auth/admin-auth.service';

@Component({
  selector: 'app-admin-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-profile.html',
  styleUrls: ['./admin-profile.css']
})
export class AdminProfile implements OnInit {
  email = '';
  organizationName = '';
  organizationLocation = '';
  phone = '';
  currentPassword = '';
  newPassword = '';
  confirmPassword = '';
  
  loading = true;
  saving = false;
  error: string | null = null;
  success: string | null = null;

  constructor(
    private adminAuth: AdminAuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile() {
    this.loading = true;
    this.adminAuth.getProfile().subscribe({
      next: (profile: any) => {
        this.email = profile.email || '';
        this.organizationName = profile.organizationName || '';
        this.organizationLocation = profile.organizationLocation || '';
        this.phone = profile.phone || '';
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load profile';
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
      organizationName: this.organizationName,
      organizationLocation: this.organizationLocation,
      phone: this.phone
    };

    if (this.newPassword) {
      updateData.currentPassword = this.currentPassword;
      updateData.newPassword = this.newPassword;
    }

    this.adminAuth.updateProfile(updateData).subscribe({
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
