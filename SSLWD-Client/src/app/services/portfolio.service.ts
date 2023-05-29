import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Portfolio } from '../models/portfolio';
import { environment } from '../environments/environment';
import { Category } from '../models/category';

@Injectable({
  providedIn: 'root'
})
export class PortfolioService {

  constructor(private httpClient: HttpClient) { }

  getPortfolios(currentCategoryId: number) :Observable<Portfolio[]>{

      const params = new HttpParams()
      .set("categoryId", currentCategoryId);
  
      const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
  
      
      return this.httpClient.get<Portfolio[]>(environment.baseUrl+"/api/portfoliobycategory", {params:params , headers: headers})
    

  }

  getPortfolio(name: string) :Observable<Portfolio>{
    const params = new HttpParams()
    .set("name", name);

    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

    
    return this.httpClient.get<Portfolio>(environment.baseUrl+"/api/portfoliobyname", {params:params , headers: headers})
  } 

  getCategory() :Observable<Category[]>{

    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

    
    return this.httpClient.get<Category[]>(environment.baseUrl+"/api/category", { headers: headers})
  } 


}
