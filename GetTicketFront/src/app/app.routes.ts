import { Routes } from '@angular/router';
import {Home} from './home/home';
import {ListeOrganisation} from './organisations/liste-organisation/liste-organisation';
import {Dashboard} from './admin/dashboard/dashboard';
import {MyTicket} from './client/my-ticket/my-ticket';
import {AdminLogin} from './auth/admin-login/admin-login';
import {ClientLogin} from './auth/client-login/client-login';
import {AdminSignup} from './auth/admin-signup/admin-signup';
import {ClientSignup} from './auth/client-signup/client-signup';
import {OrganisationDetails} from './organisations/organisation-details/organisation-details';
import {AdminProfile} from './admin/profile/admin-profile';
import {ClientProfile} from './client/profile/client-profile';
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CredentialsInterceptor} from './interceptors/credentials-interceptor';
import {App} from './app';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: Home },
  { path: 'organisations', component: ListeOrganisation },
  { path: 'organisation-details/:id', component: OrganisationDetails },
  { path: 'admin/dashboard', component: Dashboard },
  { path: 'admin/profile', component: AdminProfile },
  { path: 'client/my-ticket', component: MyTicket },
  { path: 'client/profile', component: ClientProfile },
  { path: 'admin/login', component: AdminLogin },
  { path: 'client/login', component: ClientLogin },
  { path: 'admin/signup', component: AdminSignup },
  { path: 'client/signup', component: ClientSignup },
  { path: 'admin', redirectTo: '/admin/dashboard', pathMatch: 'full' },
  { path: '', redirectTo: '/organisations', pathMatch: 'full' }
];
@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    App,
    AdminLogin,
    ClientLogin,
    AdminSignup,
    ClientSignup,
    ListeOrganisation,
    OrganisationDetails,
    AdminProfile,
    ClientProfile
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass:CredentialsInterceptor ,
      multi: true
    }
  ]
})
export class AppRoutingModule { }
