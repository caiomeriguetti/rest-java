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
                function ServerListComponent(backendService, zone) {
                    this.backendService = backendService;
                    this.zone = zone;
                    this.listServers = [];
                    this.serverClicked = new core_1.EventEmitter();
                }
                ServerListComponent.prototype.ngOnInit = function () {
                    var self = this;
                    this.backendService.loadServers(function (servers) {
                        self.listServers = servers;
                    });
                };
                /**
                  Event Listeners
                */
                ServerListComponent.prototype.onClickServer = function (server) {
                    this.serverClicked.emit(server);
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
                    __metadata('design:paramtypes', [backend_service_1.BackendService, core_1.NgZone])
                ], ServerListComponent);
                return ServerListComponent;
            })();
            exports_1("ServerListComponent", ServerListComponent);
        }
    }
});
//# sourceMappingURL=server-list.component.js.map