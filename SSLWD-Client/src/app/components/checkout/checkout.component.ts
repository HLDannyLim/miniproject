import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/app/environments/environment';
import { Country } from 'src/app/models/country';
import { PaymentInfo } from 'src/app/models/payment-info';
import { State } from 'src/app/models/state';
import { CheckoutService } from 'src/app/services/checkout.service';
import { FormService } from 'src/app/services/form.service';
import { CustomValidators } from 'src/app/validators/custom-validators';


@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  form!: FormGroup;
  states: State[] = [];
  countries: Country[] = [];
  priceId: string = "";

  quantity = 1;

  // initialize Stripe Api
  stripe = Stripe(environment.stripePublishableKey);

  cardElement: any;
  displayError: any = "";


  constructor(
    private router: Router,
    private formSvc: FormService,
    private checkoutSvc: CheckoutService,
    private fb: FormBuilder) { }


  ngOnInit(): void {

    this.setupStripePaymentForm();

    this.form = this.fb.group(
      {
        firstName: new FormControl('', [Validators.required, Validators.minLength(2), CustomValidators.notOnlyWhitespace]),
        lastName: new FormControl('', [Validators.required, Validators.minLength(2), CustomValidators.notOnlyWhitespace]),
        email: new FormControl('', [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
        address1: new FormControl('', [Validators.required, Validators.minLength(2), CustomValidators.notOnlyWhitespace]),
        address2: new FormControl('', [Validators.required, Validators.minLength(2), CustomValidators.notOnlyWhitespace]),
        phone: new FormControl('', [Validators.required, CustomValidators.notOnlyWhitespace, Validators.pattern('[0-9]*')]),
        state: new FormControl('', [Validators.required]),
        country: new FormControl('', [Validators.required]),
        city: new FormControl('', [Validators.required, Validators.minLength(2), CustomValidators.notOnlyWhitespace]),
        zip: new FormControl('', [Validators.required, Validators.minLength(2), CustomValidators.notOnlyWhitespace]),
        service: new FormControl('', [Validators.required])
      }
    )

    this.formSvc.getCountries().subscribe(
      data => {
        console.log("Retrieved countries: " + JSON.stringify(data));
        this.countries = data;
      }
    )

  }


  setupStripePaymentForm() {
    // get a handle to stripe elements
    var elements = this.stripe.elements();
    // create a card element .. and hide the zip-code field
    this.cardElement = elements.create('card', { hidePostalCode: true });
    // add an instance of card UI component into the 'card-element' div
    this.cardElement.mount('#card-element');
    // add event binding for the 'change' event on the card element
    this.cardElement.on('change', (event: any) => {
      // get a handle to card-error element
      this.displayError = document.getElementById('card-errors');
      if (event.complete) {
        this.displayError.textContent = "";
      } else if (event.error) {
        // show validation error to customer
        this.displayError.textContent = event.error.message;
      }
    });
  }

  // constructor(public movieName:string, public posterName: string, public rating: number,
  //   public commentText: string){}

  onSubmit() {
    if (this.form.invalid) {
      // touching all fields triggers the display of the error messages
      this.form.markAllAsTouched();
      return;
    }

    if (this.form.value.service === 'b') {
      this.priceId = "price_1N6r9kBXu20qYSKPXwWPaK2N";
    } else if (this.form.value.service === 's') {
      this.priceId = "price_1N6rDKBXu20qYSKPAfz1kJR9";
    } else if (this.form.value.service === 'p') {
      this.priceId = "price_1N6rMIBXu20qYSKPNWnHgJM0";
    }

    const customerDetails = this.form.value;
    const country = this.form.value.country;
    const state = this.form.value.state;
    customerDetails.country = country.code2;
    customerDetails.state = state.name;
    console.log(JSON.stringify(customerDetails));



    // if valid form then 
    // - create payment intent
    // - confirm card payment
    // - place order

    if (!this.form.invalid && this.displayError.textContent === "") {

      // this.isDisabled = true;

      this.checkoutSvc.createPaymentIntent(customerDetails).subscribe(
        (paymentIntentResponse) => {
          // send credit card data directly to stripe.com
          this.stripe.confirmCardPayment(paymentIntentResponse.clientSecret,
            {
              payment_method: {
                card: this.cardElement,
                billing_details: {
                  email: customerDetails.email,
                  name: `${customerDetails.firstName} ${customerDetails.lastName}`,
                  phone: customerDetails.phone,
                  // serviceType: customerDetails.service,
                  address: {
                    line1: customerDetails.address1,
                    line2: customerDetails.address2,
                    city: customerDetails.city,
                    state: customerDetails.state,
                    postal_code: customerDetails.zip,
                    country: customerDetails.country
                  }
                }
              }
            }, { handleActions: false })
            .then((result: any) => {
              if (result.error) {
                //inform the customer there was an error
                alert(`There was an error: ${result.error.message}`);
                // this.isDisabled = false ;
              } else {
                //call rest api via the checkoutSvc
                this.checkoutSvc.placeOrder(customerDetails).subscribe({
                  next: (response: any) => {
                    alert(`Your order has been received.\nOrder tracking number: ${response.orderTrackingNumber}`);
                    this.router.navigate(['/success']);
                    // // reset cart
                    // this.resetCart();
                    // this.storagee.clear;
                    // this.isDisabled = false ;
                  },
                  error: (err: any) => {
                    alert(`There was an error: ${err.message}`);
                    // this.isDisabled = false ;
                  }
                });
              }
            });
        }
      );
    } else {
      this.form.markAllAsTouched();
      return;
    }

  }

  getStates() {
    const countryId = this.form.value.country.id;
    console.log(` country id: ${countryId}`);
    this.formSvc.getStates(countryId).subscribe(
      data => {
        console.log("Retrieved states: " + JSON.stringify(data));
        this.states = data;
      }
    );
  }

}
