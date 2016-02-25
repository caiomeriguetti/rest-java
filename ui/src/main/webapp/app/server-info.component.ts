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
  deleting = false;
  packagesDict = {};

  constructor (private backendService: BackendService,
               private zone: NgZone,
               private element: ElementRef) {

  }

  ngOnInit () {

  }

  addPackage (name) {
    var names = name.split(" ");
    for (var i=0; i < names.length; i++) {
      if (!this.packagesDict[names[i]]) {
        this.packages.push({name: names[i]});
        this.packagesDict[names[i]] = true;
      }
    }
  }

  setLoadingPackage(name, loading) {
    var result = $.grep(this.packages, function(e){
      return e.name == name;
    });

    if (result[0]) {
      result[0].loading = loading;
    }
  }

  removePackage (name) {
    var names = name.split(" ");
    for (var i=0; i<names.length; i++) {
      this.packagesDict[names[i]] = false;
      $(this.element.nativeElement).find("[data-packname=\""+names[i]+"\"]").remove();
    }
  }

  delPackage (packageName) {
    var self = this;
    var confirmed = confirm("Are you sure?");
    if (confirmed) {
      self.setLoadingPackage(packageName, true);
      self.deleting = true;
      self.loading = true;
      this.backendService.delPackage(this._server.id, packageName, function (ok, result) {
        self.setLoadingPackage(packageName, false);
        self.deleting = false;
        self.loading = false;
        if (ok) {
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

      for (var i=0; i < packages.length; i++) {
        self.packagesDict[packages[i].name] = true;
      }

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
      this.backendService.installPackage(this._server.id, name, function (ok, result) {
        self.loading = false;
        self.installing = false;
        if (ok) {
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
    var val = $.trim(element.find(".search-input").val());
    var vals = val.split(" ");
    element.find(".app-package").each(function (index, item) {
      var name = $(item).find(".name").html();

      var hide = true;
      for (var i=0; i < vals.length; i++) {
        if (name.toLowerCase().indexOf(vals[i].toLowerCase()) >= 0 && vals[i] != "" && vals[i] != null) {
          hide = false;
        }
      }

      if (!hide || val === "") {
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

  onClickRefresh () {
    this.loadPackages();
  }

  onSerachKeywordChange () {
    this.filterList();
  }

}
