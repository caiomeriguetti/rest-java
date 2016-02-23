System.register(['angular2/core'], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1;
    var BackendService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            }],
        execute: function() {
            BackendService = (function () {
                function BackendService() {
                    this.backendUrl = "http://localhost:8080/rest-java";
                }
                BackendService.prototype.saveServer = function (data, onLoad) {
                    var url;
                    if (data.id) {
                        url = this.url("/api/servers/" + data.id);
                    }
                    else {
                        url = this.url("/api/servers/");
                    }
                    $.ajax({
                        url: url,
                        method: "put",
                        data: data,
                        success: function (r) {
                            if (onLoad) {
                                onLoad(r);
                            }
                        }, error: function () {
                        }
                    });
                };
                BackendService.prototype.delServer = function (id, onLoad) {
                    $.ajax({
                        url: this.url("/api/servers/" + id),
                        method: "delete",
                        success: function (r) {
                            if (onLoad) {
                                onLoad(r);
                            }
                        }, error: function () {
                        }
                    });
                };
                BackendService.prototype.loadServers = function (onLoadServers) {
                    $.ajax({
                        url: this.url("/api/servers"),
                        method: "get",
                        success: function (r) {
                            if (onLoadServers) {
                                onLoadServers(r);
                            }
                        }, error: function (xhr, ajaxOptions, thrownError) {
                        }
                    });
                };
                BackendService.prototype.installPackage = function (serverId, packageName, onLoad) {
                    $.ajax({
                        url: this.url("/api/servers/" + serverId + "/" + packageName),
                        method: "put",
                        success: function (r) {
                            if (onLoad) {
                                onLoad(r);
                            }
                        }, error: function () {
                        }
                    });
                };
                BackendService.prototype.delPackage = function (serverId, packageName, onLoad) {
                    $.ajax({
                        url: this.url("/api/servers/" + serverId + "/" + packageName),
                        method: "delete",
                        success: function (r) {
                            if (onLoad) {
                                onLoad(r);
                            }
                        }, error: function () {
                        }
                    });
                };
                BackendService.prototype.loadPackages = function (serverId, onLoad) {
                    $.ajax({
                        url: this.url("/api/servers/" + serverId + "/packages"),
                        type: "get",
                        success: function (r) {
                            if (onLoad) {
                                onLoad(r);
                            }
                        }, error: function () {
                        }
                    });
                };
                BackendService.prototype.url = function (uri) {
                    return this.backendUrl + uri;
                };
                BackendService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [])
                ], BackendService);
                return BackendService;
            })();
            exports_1("BackendService", BackendService);
        }
    }
});
//# sourceMappingURL=backend.service.js.map