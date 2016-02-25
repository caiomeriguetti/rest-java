System.register(['angular2/core', './backend.service', './server-list.component', './server-info.component'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, backend_service_1, server_list_component_1, server_info_component_1;
    var AppComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (backend_service_1_1) {
                backend_service_1 = backend_service_1_1;
            },
            function (server_list_component_1_1) {
                server_list_component_1 = server_list_component_1_1;
            },
            function (server_info_component_1_1) {
                server_info_component_1 = server_info_component_1_1;
            }],
        execute: function() {
            AppComponent = (function () {
                function AppComponent(backendService, zone) {
                    this.backendService = backendService;
                    this.zone = zone;
                    this.currentScreen = "";
                    this.currentServer = null;
                    this.breadcumbs = null;
                }
                AppComponent.prototype.ngOnInit = function () {
                    this.currentScreen = "server-list";
                };
                /**
                  Event Listeners
                */
                AppComponent.prototype.onClickServer = function (server) {
                    this.currentServer = server;
                    this.currentScreen = "server-info";
                    this.breadcumbs = [{
                            name: "Back"
                        }];
                };
                AppComponent.prototype.onClickBreadCumb = function (data) {
                    if (data.name === "Back") {
                        this.currentScreen = "server-list";
                        this.breadcumbs = null;
                    }
                };
                AppComponent = __decorate([
                    core_1.Component({
                        selector: 'main-app',
                        directives: [server_list_component_1.ServerListComponent, server_info_component_1.ServerInfoComponent],
                        styleUrls: ['styles/app.component.css'],
                        providers: [backend_service_1.BackendService],
                        templateUrl: 'templates/app.component.html'
                    }), 
                    __metadata('design:paramtypes', [backend_service_1.BackendService, core_1.NgZone])
                ], AppComponent);
                return AppComponent;
            }());
            exports_1("AppComponent", AppComponent);
        }
    }
});
//# sourceMappingURL=app.component.js.map