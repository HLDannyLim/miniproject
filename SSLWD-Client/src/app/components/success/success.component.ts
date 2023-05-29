import { Component, OnInit } from '@angular/core';
import { Stripe,loadStripe } from '@stripe/stripe-js';
import { environment } from 'src/app/environments/environment';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit{


  async ngOnInit(): Promise<void> {

    const stripe = await Stripe('pk_test_51MwnT8BXu20qYSKPYoDhOXOEsdt8F7E8YZzupcP7ZWz5OH3X9vxexSoCrXprR7rIg1aG66MoX70c5nAHuevaiYQv00teJTRiOF');
    
    stripe.checkout.sessions.listLineItems(
    'cs_test_a1296R67Cuj6vM823HO8jl1T0NxRKFTkJfLLWv3EIK8dOG4TU8T1HxwPWI',
    { limit: 5 },
    function(err: any, lineItems: any) {
      console.log(lineItems)
    }
    );
  }



}
