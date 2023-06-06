import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Loader } from '@googlemaps/js-api-loader';
import { CheckoutService } from 'src/app/services/checkout.service';
import { FormService } from 'src/app/services/form.service';
import { CustomValidators } from 'src/app/validators/custom-validators';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit{
  constructor(
    private router: Router,
    private formSvc: FormService,
    private fb: FormBuilder) { }

    title ="google-maps";
    private map!: google.maps.Map;


  ngOnInit(): void {
    // google map
    let loader = new Loader({
      apiKey:'AIzaSyC5P7eH-Iy3o2YevySbRvpFumw9w_69cv0'
    });
    loader.load().then(()=>{
      console.log("loaded map")
      const location = {
        lat: 1.3807531315207104,
        lng:103.74831603799787
      };

      this.map = new google.maps.Map(document.getElementById('map')as HTMLInputElement,{
        center: location,
        zoom: 16
      })

      const marker = new google.maps.Marker({
        position:location,
        map:this.map
      })
    })


    this.form = this.fb.group(
      {
        name: new FormControl('', [Validators.required, Validators.minLength(2), CustomValidators.notOnlyWhitespace]),
        phone: new FormControl('', [Validators.required, CustomValidators.notOnlyWhitespace, Validators.pattern('[0-9]*')]),
        email: new FormControl('', [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
        subject: new FormControl('', [Validators.required, Validators.minLength(2), CustomValidators.notOnlyWhitespace]),
        message: new FormControl('', [Validators.required, Validators.minLength(2), CustomValidators.notOnlyWhitespace]),
      }
      )
  }

    form!: FormGroup;

    async onSubmit(){
    if (this.form.invalid) {
      // touching all fields triggers the display of the error messages
      this.form.markAllAsTouched();
      return;
    }

    const contactForm = this.form.value;
    this.formSvc.saveContactForm(contactForm).subscribe({
      next: (response: any) => {
          alert(`Your query has been received.\nQuery tracking number: ${response.orderTrackingNumber}`);
          this.router.navigate(['/']);
      },
      error: (err: any) => {
        alert(`There was an error: ${err.message}`);

      }
    });
  }
}
