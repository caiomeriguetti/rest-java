import {Component, OnInit, NgZone, Output, EventEmitter, ElementRef} from 'angular2/core';
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
  canAdd = false;
  adding = false;
  saving = false;
  infoMessage = null;
  serverData = null;

  @Output() serverClicked: EventEmitter<any> = new EventEmitter();

  constructor (private backendService: BackendService, 
               private zone: NgZone,
               private element: ElementRef) {
    
  }

  ngOnInit () {
    var self = this;
    this.backendService.loadServers(function (servers) {
      self.listServers = servers;
    });
  }

  filterList () {
    var element = $(this.element.nativeElement);
    var val = element.find(".search-input").val();
    element.find(".app-server").each(function (index, item) {
      var name = $(item).find(".ip").html();
      name += $(item).find(".name").html();
      if (name.toLowerCase().indexOf(val.toLowerCase()) >= 0) {
        $(item).show();
      } else {
        $(item).hide();
      }
    });

    if (element.find(".app-server:visible").length === 0 && val != "") {
      this.canAdd = true;
    } else {
      this.canAdd = false;
    }
  }

  showAddForm (ip) {
    this.serverData = {ip: ip};
    this.adding = true;
  }

  saveServer () {
    var self = this;
    self.saving = true;
    this.backendService.saveServer(this.serverData, function (result) {
      self.saving = false;
      self.adding = false;
      self.canAdd = false;

      if (result.id) {
        self.listServers.unshift(result);
      } else {

      }
    });
  }

  /**
    Event Listeners
  */
  onClickAdd (ip) {
    this.showAddForm(ip);
  }

  onClickEditServer (server) {
    this.serverData = server;
    this.adding = this;
  }

  onClickInfoServer (server) {
    this.serverClicked.emit(server);
  }

  onClickSaveServer () {
    this.saveServer();
  }

  onClickCancelSaving () {
    this.adding = false;
  }

  onSerachKeywordChange () {
    this.filterList();
  }
  
}
