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
  saving = false;
  infoMessage = null;
  infoMessageModal = null;
  serverData = null;
  validationErrors = {
    ip: null, user: null, password: null
  };

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
    this.showEditModal();
  }

  validateServerInfo () {
    this.validationErrors = {
      ip: null,
      user: null,
      password: null
    };
    var hasError = false;
    if (!this.serverData.ip) {
      this.validationErrors.ip = "Please fill the ip";
      hasError = true;
    }

    if (!this.serverData.user) {
      this.validationErrors.user = "Please fill the username";
      hasError = true;
    }

    if (!this.serverData.password) {
      this.validationErrors.password = "Please fill the password";
      hasError = true;
    }

    if (hasError) {
      var msg = [];
      for (var i in this.validationErrors) {
        
        if (!this.validationErrors[i]) continue;

        msg.push(this.validationErrors[i]);
      }

      this.infoMessageModal = {
        text: msg.join("<br/>"),
        type: "danger"
      };
    }

    return hasError;
  }

  saveServer () {
    var self = this;

    var hasError = this.validateServerInfo();
    if (hasError) {
      return;
    }

    var hideModal = function () {
      setTimeout(function () {
        self.infoMessageModal = null
      }, 5000);
    };

    self.saving = true;
    this.backendService.saveServer(this.serverData, function (ok, result) {
      self.saving = false;
      self.canAdd = false;

      if (ok) {
        self.infoMessageModal = {
          text: result.text,
          type: "success"
        };
        if (result.data) {
          self.serverData.id = result.data;
          self.listServers.unshift(self.serverData);
          this.serverData = null;
        }
      } else {
        self.infoMessageModal = {
          text: result.text,
          type: "danger"
        };
      }

      hideModal();
    });
  }

  removeServerFromList (server) {
    $(this.element.nativeElement).find("[data-serverid=\""+server.id+"\"]").remove();
  }

  delServer(server) {
    var self = this;
    server.deleting = true;
    this.backendService.delServer(server.id, function (ok, result) {
      server.deleting = false;
      if (ok) {
        self.infoMessage = {
          text: result.text,
          type: "success"
        };
        
        self.removeServerFromList(server);

      } else {
        self.infoMessage = {
          text: result.text,
          type: "danger"
        };
      }

      setTimeout(function () {
        self.infoMessage = null
      }, 5000);

    });
  }

  hideEditModal() {
    $(this.element.nativeElement).find("#edit-server").modal("hide");
  }

  showEditModal() {
    this.infoMessageModal = null;
    $(this.element.nativeElement).find("#edit-server").modal("show");
  }

  /**
    Event Listeners
  */
  onClickAdd (ip) {
    this.showAddForm(ip);
  }

  onClickEditServer (server) {
    this.serverData = server;
    this.showEditModal();
  }

  onClickInfoServer (server) {
    this.serverClicked.emit(server);
  }

  onClickSaveServer () {
    this.saveServer();
  }

  onClickDel (server) {
    if (confirm("Are you sure you want to delete this server?")) {
      this.delServer(server);
    }
  }

  onClickCancelSaving () {
    this.hideEditModal();
  }

  onSerachKeywordChange () {
    this.filterList();
  }
  
}
