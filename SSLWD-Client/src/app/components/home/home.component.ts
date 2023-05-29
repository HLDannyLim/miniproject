import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Category } from 'src/app/models/category';
import { Portfolio } from 'src/app/models/portfolio';
import { PortfolioService } from 'src/app/services/portfolio.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  portfolios: Portfolio[] = [] ;
  portfolio!: Portfolio;


  // AvtivatedRoute - use for accessing route parameters
  constructor(private portfolioSvc: PortfolioService){}


  ngOnInit(): void {
      this.listPortfolio();
      console.log(this.portfolios);
  }

  listPortfolio(){

    this.portfolioSvc.getPortfolio("google").subscribe(
      data => {
        // console.log('movie review =' + JSON.stringify(data));
        this.portfolios.push(data);
      }
    );
    this.portfolioSvc.getPortfolio("youtube").subscribe(
      data => {
        // console.log('movie review =' + JSON.stringify(data));
        this.portfolios.push(data);
      }
    );
    this.portfolioSvc.getPortfolio("shopee").subscribe(
      data => {
        // console.log('movie review =' + JSON.stringify(data));
        this.portfolios.push(data);
      }
    );

  }

}

