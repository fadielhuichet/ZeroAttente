import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../../models/client';
import { Ticket } from '../../models/ticket';
import { Admin } from '../../models/admin';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private base = '/Admin';

  constructor(private http: HttpClient) {}

  getAllClients(orgName: String): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.base}/clients-by-organisation`);
  }

  getAllTickets(adminId: number): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.base}/tickets?idAdmin=${adminId}`);
  }

  updateCurrentNumber(adminId: number, newNumber: number): Observable<any> {
    return this.http.put(`${this.base}/updateCurrentNumberTicket/${adminId}`, newNumber);
  }

  getClientsWithTicketForAdmin(organisationName: string) : Observable<Client[]> {
    const encodedName = encodeURIComponent(organisationName || '');
    return this.http.get<Client[]>(`${this.base}/clients-by-organisation?organisationName=${encodedName}`);
  }
  deleteTicket(ticketId: number):Observable<any> {
    return this.http.delete(`${this.base}/delete/${ticketId}`, { withCredentials: true });
  }

  searchOrganizationByNameAndLocation(name: string, location: string): Observable<Admin[]> {
    return this.http.get<Admin[]>(`${this.base}/search?name=${encodeURIComponent(name)}&location=${encodeURIComponent(location)}`);
  }
}
