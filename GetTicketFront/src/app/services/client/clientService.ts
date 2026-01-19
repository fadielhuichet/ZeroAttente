import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Admin} from '../../models/admin';
import {Ticket} from '../../models/ticket';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  apiUrl='/client'

  constructor(private http:HttpClient) {}



public getAllOrganisations():Observable<Admin[]>{
    return this.http.get<Admin[]>(this.apiUrl+"/organisations");

}

public getOrganisation(id: number | undefined):Observable<Admin>{
    return this.http.get<Admin>(this.apiUrl+`/organisation/${id}`)
}

public getTicket(id:number,idC:number):Observable<Ticket>{
    return this.http.post<Ticket>(this.apiUrl+`/organisation/${id}/get-ticket/${idC}`,{})
}

public searchOrganizationByNameAndLocation(name: string, location: string): Observable<Admin[]> {
    return this.http.get<Admin[]>(`/Admin/search?name=${encodeURIComponent(name)}&location=${encodeURIComponent(location)}`);
}
}
