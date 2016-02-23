import {Component, OnInit, NgZone} from 'angular2/core';
import {BackendService} from './backend.service';
import {ServerListComponent} from './server-list.component';
import {ServerInfoComponent} from './server-info.component';

declare var $:any;

@Component({
    selector: 'main-app',
    directives: [ServerListComponent, ServerInfoComponent],
    styleUrls:['styles/app.component.css'],
    providers: [BackendService],
    templateUrl: 'templates/app.component.html'
})

export class AppComponent implements OnInit {

  currentScreen = "";
  currentServer = null;
  breadcumbs = null;

  constructor (private backendService: BackendService, 
               private zone: NgZone) {
    
  }

  ngOnInit () {
    this.currentScreen = "server-list";
  }

  /**
    Event Listeners
  */

  onClickServer(server) {
    this.currentServer = server;
    this.currentScreen = "server-info";

    this.breadcumbs = [{
      name: "Back"
    }]
  }

  onClickBreadCumb(data) {
    if (data.name === "Back") {
      this.currentScreen = "server-list";
      this.breadcumbs = null;
    }
  }

  
}
