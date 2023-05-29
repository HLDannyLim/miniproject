import { CustomerDetails } from "./customer-details";

export class PaymentInfo {
    constructor(public amount?:number,
        public currency?: string,
        public orderDetails?: CustomerDetails){}
}
