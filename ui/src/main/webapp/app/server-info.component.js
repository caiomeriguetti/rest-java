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
                    this.installing = false;
                    this.deleting = false;
                    this.packagesDict = {};
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
                    var names = name.split(" ");
                    for (var i = 0; i < names.length; i++) {
                        if (!this.packagesDict[names[i]]) {
                            this.packages.push({ name: names[i] });
                            this.packagesDict[names[i]] = true;
                        }
                    }
                };
                ServerInfoComponent.prototype.setLoadingPackage = function (name, loading) {
                    var result = $.grep(this.packages, function (e) {
                        return e.name == name;
                    });
                    if (result[0]) {
                        result[0].loading = loading;
                    }
                };
                ServerInfoComponent.prototype.removePackage = function (name) {
                    var names = name.split(" ");
                    for (var i = 0; i < names.length; i++) {
                        this.packagesDict[names[i]] = false;
                        $(this.element.nativeElement).find("[data-packname=\"" + names[i] + "\"]").remove();
                    }
                };
                ServerInfoComponent.prototype.delPackage = function (packageName) {
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
                        for (var i = 0; i < packages.length; i++) {
                            self.packagesDict[packages[i].name] = true;
                        }
                        self.packages = packages;
                        self.filterList();
                        self.loading = false;
                    });
                };
                ServerInfoComponent.prototype.installPackage = function (name) {
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
                    var val = $.trim(element.find(".search-input").val());
                    var vals = val.split(" ");
                    element.find(".app-package").each(function (index, item) {
                        var name = $(item).find(".name").html();
                        var hide = true;
                        for (var i = 0; i < vals.length; i++) {
                            if (name.toLowerCase().indexOf(vals[i].toLowerCase()) >= 0 && vals[i] != "" && vals[i] != null) {
                                hide = false;
                            }
                        }
                        if (!hide || val === "") {
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
                ServerInfoComponent.prototype.onClickRefresh = function () {
                    this.loadPackages();
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