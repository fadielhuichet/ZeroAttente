import { Component, OnInit } from '@angular/core';
import { ClientAuthService } from '../../services/auth/client-auth.service';
import { Client } from '../../models/client';
import { Ticket } from '../../models/ticket';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-my-ticket',
  imports: [CommonModule],
  templateUrl: './my-ticket.html',
  styleUrl: './my-ticket.css',
})
export class MyTicket implements OnInit {
  client: Client | null = null;
  ticket: Ticket | null = null;
  loading = true;
  error: string | null = null;

  constructor(private clientAuth: ClientAuthService) {}

  ngOnInit(): void {
    this.clientAuth.me().subscribe({
      next: (client) => {
        // Map clientId to idClient for compatibility
        if (client && client.clientId !== undefined) {
          client.idClient = client.clientId;
        }
        this.client = client;
        this.ticket = client.ticket || null;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Could not load your ticket.';
        this.loading = false;
      }
    });
  }

  protected loadTicket() {

  }
}
