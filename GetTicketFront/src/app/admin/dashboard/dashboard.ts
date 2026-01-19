import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin/admin';
import { AdminAuthService } from '../../services/auth/admin-auth.service';
import { Client } from '../../models/client';
import { Ticket } from '../../models/ticket';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  adminId: number | null = null;
  orgName: string = '';
  clients: Client[] = [];
  tickets: Ticket[] = [];
  currentNumber: number = 0;
  displayedCurrentNumber: number = 0;
  loading = true;
  error: string | null = null;
  updateLoading = false;

  constructor(private adminService: AdminService, private adminAuthService: AdminAuthService) {}

  ngOnInit(): void {
    // Get admin profile to fetch actual adminId and organization name
    this.adminAuthService.getProfile().subscribe({
      next: (profile: any) => {
        console.log('Admin profile:', profile);
        this.adminId = profile.idAdmin;
        this.orgName = profile.organizationName || '';
        this.displayedCurrentNumber = profile.currentNumber || 0;
        this.currentNumber = profile.currentNumber || 0;
        this.loadData();
      },
      error: (err: any) => {
        console.error('Failed to load admin profile:', err);
        this.error = 'Failed to load admin profile';
        this.loading = false;
      }
    });
  }

  loadData() {
    if (!this.adminId) {
      this.error = 'Admin ID not loaded';
      this.loading = false;
      return;
    }

    this.loading = true;
    console.log('Loading tickets for adminId:', this.adminId);

    this.adminService.getAllTickets(this.adminId).subscribe({
      next: (tickets) => {
        console.log('Loaded tickets:', tickets);
        this.tickets = tickets;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load tickets:', err);
        this.error = 'Failed to load tickets.';
        this.loading = false;
      }
    });
  }

  updateCurrentNumber() {
    if (!this.adminId) {
      this.error = 'Admin ID not loaded';
      return;
    }

    this.updateLoading = true;
    this.adminService.updateCurrentNumber(this.adminId, this.currentNumber).subscribe({
      next: () => {
        this.updateLoading = false;
        this.displayedCurrentNumber = this.currentNumber;
        this.error = null;
        // Reload tickets, keep the displayed number we just set
        this.adminService.getAllTickets(this.adminId!).subscribe({
          next: (tickets) => {
            this.tickets = tickets;
          }
        });
      },
      error: (err) => {
        console.error('Failed to update current number:', err);
        this.error = 'Failed to update current number.';
        this.updateLoading = false;
      }
    });
  }

  deleteTicket(idTicket: number) {
    this.adminService.deleteTicket(idTicket).subscribe({
      next: () => {
        this.loadData();
      },
      error: () => {
        this.error = 'Failed to delete ticket.';
      }
    });
  }
}
