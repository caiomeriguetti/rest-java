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
    var ServerListComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (backend_service_1_1) {
                backend_service_1 = backend_service_1_1;
            }],
        execute: function() {
            ServerListComponent = (function () {
                function ServerListComponent(backendService, zone, element) {
                    this.backendService = backendService;
                    this.zone = zone;
                    this.element = element;
                    this.listServers = [];
                    this.canAdd = false;
                    this.saving = false;
                    this.infoMessage = null;
                    this.infoMessageModal = null;
                    this.serverData = null;
                    this.validationErrors = {
                        ip: null, user: null, password: null
                    };
                    this.serverClicked = new core_1.EventEmitter();
                }
                ServerListComponent.prototype.ngOnInit = function () {
                    var self = this;
                    this.backendService.loadServers(function (servers) {
                        self.listServers = servers;
                    });
                };
                ServerListComponent.prototype.filterList = function () {
                    var element = $(this.element.nativeElement);
                    var val = element.find(".search-input").val();
                    element.find(".app-server").each(function (index, item) {
                        var name = $(item).find(".ip").html();
                        name += $(item).find(".name").html();
                        if (name.toLowerCase().indexOf(val.toLowerCase()) >= 0) {
                            $(item).show();
                        }
                        else {
                            $(item).hide();
                        }
                    });
                    if (element.find(".app-server:visible").length === 0 && val != "") {
                        this.canAdd = true;
                    }
                    else {
                        this.canAdd = false;
                    }
                };
                ServerListComponent.prototype.showAddForm = function (ip) {
                    this.serverData = { ip: ip };
                    this.showEditModal();
                };
                ServerListComponent.prototype.validateServerInfo = function () {
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
                            if (!this.validationErrors[i])
                                continue;
                            msg.push(this.validationErrors[i]);
                        }
                        this.infoMessageModal = {
                            text: msg.join("<br/>"),
                            type: "danger"
                        };
                    }
                    return hasError;
                };
                ServerListComponent.prototype.saveServer = function () {
                    var self = this;
                    var hasError = this.validateServerInfo();
                    if (hasError) {
                        return;
                    }
                    var hideModal = function () {
                        setTimeout(function () {
                            self.infoMessageModal = null;
                        }, 5000);
                    };
                    self.saving = true;
                    this.backendService.saveServer(this.serverData, function (ok, result) {
                        self.saving = false;
                        self.canAdd = false;
                        if (ok && (result.id || result.code === 1)) {
                            self.infoMessageModal = {
                                text: "Server info saved.",
                                type: "success"
                            };
                            if (result.id) {
                                self.listServers.unshift(result);
                            }
                        }
                        else {
                            self.infoMessageModal = {
                                text: "Problem ocurred.",
                                type: "danger"
                            };
                        }
                        hideModal();
                    });
                };
                ServerListComponent.prototype.removeServerFromList = function (server) {
                    $(this.element.nativeElement).find("[data-serverid=\"" + server.id + "\"]").remove();
                };
                ServerListComponent.prototype.delServer = function (server) {
                    var self = this;
                    server.deleting = true;
                    this.backendService.delServer(server.id, function (result) {
                        server.deleting = false;
                        if (result.code === 1) {
                            self.infoMessage = {
                                text: "Server deleted.",
                                type: "success"
                            };
                            self.removeServerFromList(server);
                        }
                        else {
                            self.infoMessage = {
                                text: "Problem ocurred.",
                                type: "danger"
                            };
                        }
                        setTimeout(function () {
                            self.infoMessage = null;
                        }, 5000);
                    });
                };
                ServerListComponent.prototype.hideEditModal = function () {
                    $(this.element.nativeElement).find("#edit-server").modal("hide");
                };
                ServerListComponent.prototype.showEditModal = function () {
                    this.infoMessageModal = null;
                    $(this.element.nativeElement).find("#edit-server").modal("show");
                };
                /**
                  Event Listeners
                */
                ServerListComponent.prototype.onClickAdd = function (ip) {
                    this.showAddForm(ip);
                };
                ServerListComponent.prototype.onClickEditServer = function (server) {
                    this.serverData = server;
                    this.showEditModal();
                };
                ServerListComponent.prototype.onClickInfoServer = function (server) {
                    this.serverClicked.emit(server);
                };
                ServerListComponent.prototype.onClickSaveServer = function () {
                    this.saveServer();
                };
                ServerListComponent.prototype.onClickDel = function (server) {
                    if (confirm("Are you sure you want to delete this server?")) {
                        this.delServer(server);
                    }
                };
                ServerListComponent.prototype.onClickCancelSaving = function () {
                    this.hideEditModal();
                };
                ServerListComponent.prototype.onSerachKeywordChange = function () {
                    this.filterList();
                };
                __decorate([
                    core_1.Output(), 
                    __metadata('design:type', core_1.EventEmitter)
                ], ServerListComponent.prototype, "serverClicked", void 0);
                ServerListComponent = __decorate([
                    core_1.Component({
                        selector: 'server-list',
                        directives: [],
                        styleUrls: ['styles/server-list.component.css'],
                        providers: [backend_service_1.BackendService],
                        templateUrl: 'templates/server-list.component.html'
                    }), 
                    __metadata('design:paramtypes', [backend_service_1.BackendService, core_1.NgZone, core_1.ElementRef])
                ], ServerListComponent);
                return ServerListComponent;
            })();
            exports_1("ServerListComponent", ServerListComponent);
        }
    }
});
//# sourceMappingURL=server-list.component.js.map