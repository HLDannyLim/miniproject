import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, of } from 'rxjs';
import { environment } from '../environments/environment';
import { Country } from '../models/country';
import { State } from '../models/state';
import { ContactForm } from '../models/contact-form';

@Injectable({
  providedIn: 'root'
})
export class FormService {


  constructor(private httpClient: HttpClient) { }

  saveContactForm(contactForm: ContactForm): Observable<any> {

    console.log(JSON.stringify(contactForm))

    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    
    return this.httpClient.post<ContactForm>(environment.baseUrl+"/api/contactform" ,JSON.stringify(contactForm), {headers: headers});
  }

  getCountries(): Observable<Country[]>{
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    
    return this.httpClient.get<Country[]>(environment.baseUrl+"/api/country", { headers: headers})
  }

  getStates(countryId: number): Observable<State[]>{
    
    const params = new HttpParams()
    .set("countryid", countryId);

    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    
    return this.httpClient.get<State[]>(environment.baseUrl+"/api/state", {params:params, headers: headers})
  }


}

interface GetResponseCountries {
  _embedded:{
    countries:Country[];
  }
}

interface GetResponseStates{
  _embedded:{
    states:State[];
  }
}