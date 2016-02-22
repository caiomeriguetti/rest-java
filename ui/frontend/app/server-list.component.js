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
                    this.adding = false;
                    this.saving = false;
                    this.infoMessage = null;
                    this.serverData = null;
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
                    this.adding = true;
                };
                ServerListComponent.prototype.saveServer = function () {
                    var self = this;
                    self.saving = true;
                    this.backendService.saveServer(this.serverData, function (result) {
                        self.saving = false;
                        self.adding = false;
                        self.canAdd = false;
                        if (result.id) {
                            self.listServers.unshift(result);
                        }
                        else {
                        }
                    });
                };
                /**
                  Event Listeners
                */
                ServerListComponent.prototype.onClickAdd = function (ip) {
                    this.showAddForm(ip);
                };
                ServerListComponent.prototype.onClickEditServer = function (server) {
                    this.serverData = server;
                    this.adding = this;
                };
                ServerListComponent.prototype.onClickInfoServer = function (server) {
                    this.serverClicked.emit(server);
                };
                ServerListComponent.prototype.onClickSaveServer = function () {
                    this.saveServer();
                };
                ServerListComponent.prototype.onClickCancelSaving = function () {
                    this.adding = false;
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