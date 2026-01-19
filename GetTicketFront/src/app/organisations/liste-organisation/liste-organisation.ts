import {Component, OnInit} from '@angular/core';
import {ClientService} from '../../services/client/clientService';
import {CommonModule} from '@angular/common';
import {Admin} from '../../models/admin';
import {Router, RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-liste-organisation',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    FormsModule
  ],
  templateUrl: './liste-organisation.html',
  styleUrls: ['./liste-organisation.css'],
})
export class ListeOrganisation implements OnInit{

  constructor(private clientService:ClientService,private router:Router) {
  }
  organisations:Admin[]=[];
  searchName: string = '';
  searchLocation: string = '';

  ngOnInit(): void {

    this.clientService.getAllOrganisations().subscribe(data=>{
      this.organisations=data;
    })
    }

  searchOrganisations() {
    this.clientService.searchOrganizationByNameAndLocation(this.searchName, this.searchLocation).subscribe(data => {
      this.organisations = data;
    });
  }

}
