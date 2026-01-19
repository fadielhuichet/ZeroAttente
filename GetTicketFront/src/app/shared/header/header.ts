import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterLinkActive, Router} from '@angular/router';
import {CommonModule} from '@angular/common';
import {AdminAuthService} from '../../services/auth/admin-auth.service';
import {ClientAuthService} from '../../services/auth/client-auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive,
    CommonModule
  ],
  templateUrl: './header.html',
  styleUrls: ['./header.css'],
})
export class Header implements OnInit{
   isAdmin = false;
   isClient = false;
   isLoggedIn = false;
   username = '';
   showRoleModal = false;
   roleAction: 'login' | 'signup' | null = null;

   constructor(private router: Router, private adminAuth: AdminAuthService, private clientAuth: ClientAuthService) {}

  ngOnInit(): void {
    // Subscribe to login state changes
    this.adminAuth.isLoggedIn().subscribe(() => this.refreshSession());
    this.clientAuth.isLoggedIn().subscribe(() => this.refreshSession());
    // Initial session check
    this.refreshSession();
  }

  private refreshSession() {
    this.adminAuth.me().subscribe({
      next: (user) => {
        this.isLoggedIn = true;
        this.isAdmin = true;
        this.isClient = false;
        this.username = user?.email || '';
      },
      error: () => {
        this.clientAuth.me().subscribe({
          next: (user) => {
            this.isLoggedIn = true;
            this.isAdmin = false;
            this.isClient = true;
            this.username = user?.username || '';
          },
          error: () => {
            this.isLoggedIn = false;
            this.isAdmin = false;
            this.isClient = false;
            this.username = '';
          }
        });
      }
    });
  }

  goOrganisations(event?: Event): void {
    if (event) {
      event.preventDefault();
    }
    this.router.navigateByUrl('/organisations');
  }

  protected logout() {
    if (this.isAdmin) {
      this.adminAuth.logout().subscribe(() => {
        this.isLoggedIn = false;
        this.isAdmin = false;
        this.isClient = false;
        this.username = '';
        this.router.navigate(['/admin/login']);
      });
    } else if (this.isClient) {
      this.clientAuth.logout().subscribe(() => {
        this.isLoggedIn = false;
        this.isAdmin = false;
        this.isClient = false;
        this.username = '';
        this.router.navigate(['/client/login']);
      });
    }
  }

  openRoleModal(action: 'login' | 'signup') {
    this.roleAction = action;
    this.showRoleModal = true;
  }

  closeRoleModal() {
    this.showRoleModal = false;
    this.roleAction = null;
  }

  selectRole(role: 'admin' | 'client') {
    if (this.roleAction === 'login') {
      this.router.navigate([`/${role}/login`]);
    } else if (this.roleAction === 'signup') {
      this.router.navigate([`/${role}/signup`]);
    }
    this.closeRoleModal();
  }

  protected readonly console = console;
}
