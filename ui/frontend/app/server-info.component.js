System.register(['angular2/core', './backend.service'], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, backend_service_1;
    var ServerInfoComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (backend_service_1_1) {
                backend_service_1 = backend_service_1_1;
            }],
        execute: function() {
            ServerInfoComponent = (function () {
                function ServerInfoComponent(backendService, zone, element) {
                    this.backendService = backendService;
                    this.zone = zone;
                    this.element = element;
                    this.packages = null;
                    this.canInstall = false;
                    this.infoMessage = null;
                    this.loading = false;
                }
                Object.defineProperty(ServerInfoComponent.prototype, "server", {
                    get: function () {
                        return this._server;
                    },
                    set: function (val) {
                        this._server = val;
                        this.loadPackages();
                    },
                    enumerable: true,
                    configurable: true
                });
                ;
                ServerInfoComponent.prototype.ngOnInit = function () {
                };
                ServerInfoComponent.prototype.addPackage = function (name) {
                    this.packages.push({ name: name });
                };
                ServerInfoComponent.prototype.removePackage = function (name) {
                    var result = $.grep(this.packages, function (e) {
                        return e.name != name;
                    });
                    this.packages = result;
                };
                ServerInfoComponent.prototype.delPackage = function (packageName) {
                    var self = this;
                    var confirmed = confirm("Are you sure?");
                    self.loading = true;
                    if (confirmed) {
                        this.backendService.delPackage(this._server.id, packageName, function (result) {
                            self.loading = false;
                            if (result.code === 1) {
                                self.infoMessage = {
                                    text: "The package " + packageName + " was deleted.",
                                    type: "success"
                                };
                                self.removePackage(packageName);
                                self.filterList();
                                self.canInstall = true;
                            }
                            else {
                                self.infoMessage = {
                                    text: "Problem deleting the package",
                                    type: "danger"
                                };
                            }
                            setTimeout(function () {
                                self.infoMessage = null;
                            }, 5000);
                        });
                    }
                };
                ServerInfoComponent.prototype.loadPackages = function () {
                    var self = this;
                    self.loading = true;
                    this.backendService.loadPackages(this._server.id, function (packages) {
                        self.packages = packages;
                        self.filterList();
                        self.loading = false;
                    });
                };
                ServerInfoComponent.prototype.installPackage = function (name) {
                    var self = this;
                    self.loading = true;
                    var confirmed = confirm("Are you sure?");
                    if (confirmed) {
                        this.backendService.installPackage(this._server.id, name, function (result) {
                            self.loading = false;
                            if (result.code === 1) {
                                self.infoMessage = {
                                    text: "The package " + name + " was installed.",
                                    type: "success"
                                };
                                self.addPackage(name);
                                self.canInstall = false;
                            }
                            else {
                                self.infoMessage = {
                                    text: "Problem installing the package",
                                    type: "danger"
                                };
                            }
                            setTimeout(function () {
                                self.infoMessage = null;
                            }, 5000);
                        });
                    }
                };
                ServerInfoComponent.prototype.filterList = function () {
                    var element = $(this.element.nativeElement);
                    var val = element.find(".search-input").val();
                    element.find(".app-package").each(function (index, item) {
                        var name = $(item).find(".name").html();
                        if (name.toLowerCase().indexOf(val.toLowerCase()) >= 0) {
                            $(item).show();
                        }
                        else {
                            $(item).hide();
                        }
                    });
                    if (element.find(".app-package:visible").length === 0 && val != "") {
                        this.canInstall = true;
                    }
                    else {
                        this.canInstall = false;
                    }
                };
                /**
                  Event Listeners
                */
                ServerInfoComponent.prototype.onClickDel = function (packageName) {
                    this.delPackage(packageName);
                };
                ServerInfoComponent.prototype.onSerachKeywordChange = function () {
                    this.filterList();
                };
                __decorate([
                    core_1.Input(), 
                    __metadata('design:type', Object), 
                    __metadata('design:paramtypes', [Object])
                ], ServerInfoComponent.prototype, "server", null);
                ServerInfoComponent = __decorate([
                    core_1.Component({
                        selector: 'server-info',
                        directives: [],
                        styleUrls: ['styles/server-info.component.css'],
                        providers: [],
                        templateUrl: 'templates/server-info.component.html'
                    }), 
                    __metadata('design:paramtypes', [backend_service_1.BackendService, core_1.NgZone, core_1.ElementRef])
                ], ServerInfoComponent);
                return ServerInfoComponent;
            })();
            exports_1("ServerInfoComponent", ServerInfoComponent);
        }
    }
});
//# sourceMappingURL=server-info.component.js.map