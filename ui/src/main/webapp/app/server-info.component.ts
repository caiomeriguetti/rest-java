import {Component, OnInit, NgZone, Input, ElementRef} from 'angular2/core';
import {BackendService} from './backend.service';

declare var $:any;

@Component({
    selector: 'server-info',
    directives: [],
    styleUrls:['styles/server-info.component.css'],
    providers: [],
    templateUrl: 'templates/server-info.component.html'
})

export class ServerInfoComponent implements OnInit {
  
  private _server;

  @Input() set server(val) {
    this._server = val;
    this.loadPackages();
  };

  get server() {
    return this._server;
  }

  packages = null;
  canInstall = false;
  infoMessage = null;
  loading = false;
  installing = false;

  constructor (private backendService: BackendService, 
               private zone: NgZone,
               private element: ElementRef) {
    
  }

  ngOnInit () {
    
  }

  addPackage (name) {
    this.packages.push({name: name});
  }

  setLoadingPackage(name, loading) {
    var result = $.grep(this.packages, function(e){ 
      return e.name == name; 
    });

    result[0].loading = loading;
  }

  removePackage (name) {
    $(this.element.nativeElement).find("[data-packname=\""+name+"\"]").remove();
  }

  delPackage (packageName) {
    var self = this;
    var confirmed = confirm("Are you sure?");
    if (confirmed) {
      self.setLoadingPackage(packageName, true);
      this.backendService.delPackage(this._server.id, packageName, function (result) {
        self.setLoadingPackage(packageName, true);
        if (result.code === 1) {
          self.infoMessage = {
            text: "The package "+packageName+" was deleted.",
            type: "success"
          };
          self.removePackage(packageName); 
          self.filterList();
          self.canInstall = true;
        } else {
          self.infoMessage = {
            text: "Problem deleting the package",
            type: "danger"
          };
        }

        setTimeout(function () {
          self.infoMessage = null
        }, 5000);
      });
    }
  }

  loadPackages () {
    var self = this;
    self.loading = true;
    this.backendService.loadPackages(this._server.id, function (packages) {
      self.packages = packages;
      self.filterList();
      self.loading = false;
    });
  }

  installPackage(name) {
    var self = this;
    var confirmed = confirm("Are you sure?");
    if (confirmed) {
      self.loading = true;
      self.installing = true;
      this.backendService.installPackage(this._server.id, name, function (result) {
        self.loading = false;
        self.installing = false;
        if (result.code === 1) {
          self.infoMessage = {
            text: "The package "+name+" was installed.",
            type: "success"
          };
          self.addPackage(name);
          self.canInstall = false;
        } else {
          self.infoMessage = {
            text: "Problem installing the package",
            type: "danger"
          };
        }

        setTimeout(function () {
          self.infoMessage = null
        }, 5000);
      });
    }
  }

  filterList () {
    var element = $(this.element.nativeElement);
    var val = element.find(".search-input").val();
    element.find(".app-package").each(function (index, item) {
      var name = $(item).find(".name").html();
      if (name.toLowerCase().indexOf(val.toLowerCase()) >= 0) {
        $(item).show();
      } else {
        $(item).hide();
      }
    });

    if (element.find(".app-package:visible").length === 0 && val != "") {
      this.canInstall = true;
    } else {
      this.canInstall = false;
    }
  }

  /**
    Event Listeners
  */

  onClickDel (packageName) {
    this.delPackage(packageName);
  }

  onSerachKeywordChange () {
    this.filterList();
  }
  
}
