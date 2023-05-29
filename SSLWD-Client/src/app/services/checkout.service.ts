import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CustomerDetails } from '../models/customer-details';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { PaymentInfo } from '../models/payment-info';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {
  constructor(private httpClient:HttpClient) { }

  private paymentIntentUrl = environment.baseUrl+'/api/payment-intent';

  placeOrder(customerDetails: CustomerDetails): Observable<any> {

    console.log(JSON.stringify(customerDetails))

    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    
    return this.httpClient.post<CustomerDetails>(environment.baseUrl+"/api/orderdetails" ,JSON.stringify(customerDetails), {headers: headers});

  }

  createPaymentIntent(customerDetails: CustomerDetails): Observable<any> {
    return this.httpClient.post<CustomerDetails>(this.paymentIntentUrl, customerDetails);
  }
}
