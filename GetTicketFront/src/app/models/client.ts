import {Ticket} from './ticket';

export interface Client {
  idClient:number;
  username:string;
  nom:string;
  prenom:string;
  adminId:number;
  ticketId?:number;
  ticket:Ticket;

}
