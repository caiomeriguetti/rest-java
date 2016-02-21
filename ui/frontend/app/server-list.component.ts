import {Component, OnInit, NgZone, Output, EventEmitter} from 'angular2/core';
import {BackendService} from './backend.service';

declare var $:any;

@Component({
    selector: 'server-list',
    directives: [],
    styleUrls:['styles/server-list.component.css'],
    providers: [BackendService],
    templateUrl: 'templates/server-list.component.html'
})

export class ServerListComponent implements OnInit {

  listServers = [];

  @Output() serverClicked: EventEmitter<any> = new EventEmitter();

  constructor (private backendService: BackendService, 
               private zone: NgZone) {
    
  }

  ngOnInit () {
    var self = this;
    this.backendService.loadServers(function (servers) {
      self.listServers = servers;
    });
  }

  /**
    Event Listeners
  */

  onClickServer(server) {
    this.serverClicked.emit(server);
  }
  
}
