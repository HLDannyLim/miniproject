import { Component, OnInit, HostListener, ViewChild } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'SSLWD-Client';

  constructor(){}
  ngOnInit(): void {
  }

  @ViewChild('cursor') refCursor:any;
  @HostListener('document:mousemove',['$event'])
  onMouseMove(event:any){
    this.refCursor.nativeElement.style.left = (event.pageX-23) + "px";
    this.refCursor.nativeElement.style.top = (event.pageY-23) + "px";
  }
}
