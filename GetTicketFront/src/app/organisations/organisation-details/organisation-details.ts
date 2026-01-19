import {Component, OnInit} from '@angular/core';
import {ClientService} from '../../services/client/clientService';
import {Ticket} from '../../models/ticket';
import {Admin} from '../../models/admin';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {NgIf} from '@angular/common';
import {Client} from '../../models/client';
import {ClientAuthService} from '../../services/auth/client-auth.service';



@Component({
  selector: 'app-organisation-details',
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './organisation-details.html',
  styleUrl: './organisation-details.css',
})
export class OrganisationDetails implements OnInit {
  private idOrg: string | null = null;
  public organisation: Admin|null = null;
  public client: Client|null = null;
  ticket:Ticket|null=null
  public loadingTicket = false;
  public ticketError: string | null = null;
  public loadingOrganisation = true;
  constructor(
    private clientService: ClientService,
    private route: ActivatedRoute,
    private clientAuth: ClientAuthService
  ) {}

  ngOnInit(): void {
    this.idOrg = this.route.snapshot.paramMap.get("id");

    // Fetch current client
    this.clientAuth.me().subscribe({
      next: (client) => {
        // Map clientId to idClient for compatibility
        if (client && client.clientId !== undefined) {
          client.idClient = client.clientId;
        }
        this.client = client;
        console.log('Client loaded:', this.client);
      },
      error: () => {
        this.client = null;
        console.log('No client loaded');
      }
    });

    if (this.idOrg) {
      const idNumber = Number(this.idOrg);
      this.clientService.getOrganisation(idNumber).subscribe(data => {
        this.organisation = data;
        console.log('Organisation loaded:', this.organisation);
      });
    }
  }

  private isValidId(id: any): id is number {
    return typeof id === 'number' && !isNaN(id);
  }

  getTicket(adminId?: number | null, clientId?: number | null) {
    if (!this.isValidId(adminId) || !this.isValidId(clientId)) {
      this.ticketError = 'Invalid organisation or client ID.';
      return;
    }

    this.loadingTicket = true;
    this.ticketError = null;
    this.ticket = null;

    this.clientService.getTicket(adminId, clientId).subscribe({
      next: (data: Ticket) => {
        this.ticket = data;
        this.loadingTicket = false;
      },
      error: (err) => {
        console.error('Failed to get ticket', err);
        this.ticketError = 'Failed to load ticket.';
        this.loadingTicket = false;
      }
    });
  }

}
