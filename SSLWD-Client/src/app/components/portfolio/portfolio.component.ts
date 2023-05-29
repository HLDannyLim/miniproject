import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Category } from 'src/app/models/category';
import { Portfolio } from 'src/app/models/portfolio';
import { PortfolioService } from 'src/app/services/portfolio.service';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.css']
})
export class PortfolioComponent implements OnInit {

  
  portfolios: Portfolio[] = [] ;
  portfolio!: Portfolio;
  categories!: Category[];

  currentCategoryId:number = 1;
  previousCategoryId: number = 1;
  currentCategoryName!: string;


  // AvtivatedRoute - use for accessing route parameters
  constructor(private portfolioSvc: PortfolioService,
    private route: ActivatedRoute){}


  ngOnInit(): void {
      this.listCategory();
      console.log(this.categories);
      this.route.paramMap.subscribe(()=> {
        this.listProducts();
      });
  }

  
  listProducts() {
     //check if "id" parameter is available
    //                             use the activated route->
    //                             state of the route at the given moment in time-> 
    //                             Map of all the route parameters-> 
    //                             read the id parameter-> from routerLink in html
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');

    if(hasCategoryId){
      //get the "id" param string. convert string to a number using the "+" symbol
      // get('id') -> this tell compiler that the object is not null
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!;

      // get the "name" param string
      this.currentCategoryName = this.route.snapshot.paramMap.get('name')!;
    }
    else{
      // not category id available ... default to category id 1
      this.currentCategoryId = 0;
      this.currentCategoryName = '';
    }


    // now get the products for the given category id
    this.portfolioSvc.getPortfolios(this.currentCategoryId).subscribe(
      data => {
        console.log('portfolios =' + JSON.stringify(data));
        this.portfolios = data 
      }
    );
  
  }


  listCategory() {
        this.portfolioSvc.getCategory().subscribe(
      data => {
        // console.log('movie review =' + JSON.stringify(data));
        this.categories = data 
      }
    );
  }


}
