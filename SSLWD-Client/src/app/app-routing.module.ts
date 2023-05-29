import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { ServicesComponent } from './components/services/services.component';
import { PortfolioComponent } from './components/portfolio/portfolio.component';
import { BlogComponent } from './components/blog/blog.component';
import { ContactComponent } from './components/contact/contact.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { SuccessComponent } from './components/success/success.component';
import { FailureComponent } from './components/failure/failure.component';

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'about', component: AboutComponent},
  {path: 'services', component: ServicesComponent},
  {path: 'portfolio', component: PortfolioComponent},
  {path: 'checkout', component: CheckoutComponent},
  {path: 'checkout/success/:id', component: SuccessComponent},
  {path: 'checkout/success/', component: SuccessComponent},
  {path: 'checkout/failure', component: FailureComponent},
  {path: 'category/:id/:name', component: PortfolioComponent},
  {path: 'blog', component: BlogComponent},
  {path: 'contact', component: ContactComponent},
  // Empty path       -  redirect to /products
  {path: '', redirectTo: '/home', pathMatch:'full'},
  // generic wildcard/match anything that didnt match above routes and redirect to /products
  {path: '**', redirectTo: '/home', pathMatch:'full'},
];

// const routes: Routes = [
//   {path: 'order-history', component: OrderHistoryComponent, canActivate: [OktaAuthGuard],
//                 data: {onAuthRequired: sendToLoginPage}},

//   {path: 'members', component: MembersPageComponent, canActivate: [OktaAuthGuard],
//                 data: {onAuthRequired: sendToLoginPage}},

//   {path: 'login/callback', component: OktaCallbackComponent},
//   {path: 'login', component: LoginComponent},

//   {path: 'checkout', component: CheckoutComponent},
//   {path: 'cart-details', component: CartDetailsComponent},
//   {path: 'products/:id', component: ProductDetailsComponent},
//   {path: 'search/:keyword', component: ProductListComponent},
//   {path: 'category/:id/:name', component: ProductListComponent},
//   {path: 'category', component: ProductListComponent},
//   {path: 'products', component: ProductListComponent},
//   // Empty path       -  redirect to /products
//   {path: '', redirectTo: '/products', pathMatch:'full'},
//   // generic wildcard/match anything that didnt match above routes and redirect to /products
//   {path: '**', redirectTo: '/products', pathMatch:'full'},
// ]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
