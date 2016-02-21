import {Injectable} from 'angular2/core';

declare var $:any;

@Injectable()
export class BackendService {

	private backendUrl: string = "http://localhost:8080/rest-java";

	public loadServers (onLoadServers) {
		$.ajax({
			url: this.url("/api/servers"),
			method: "get",
			success: function (r) {
				if (onLoadServers) {
					onLoadServers(r);
				}
			},error: function (){

			}
		});
	}

	public installPackage(serverId, packageName, onLoad) {
		$.ajax({
			url: this.url("/api/servers/"+serverId+"/"+packageName),
			method: "put",
			success: function (r) {
				if (onLoad) {
					onLoad(r);
				}
			},error: function (){

			}
		});
	}

	public delPackage(serverId, packageName, onLoad) {
		$.ajax({
			url: this.url("/api/servers/"+serverId+"/"+packageName),
			method: "delete",
			success: function (r) {
				if (onLoad) {
					onLoad(r);
				}
			},error: function (){

			}
		});
	}

	public loadPackages (serverId, onLoad) {
		$.ajax({
			url: this.url("/api/servers/"+serverId+"/packages"),
			type: "get",
			success: function (r) {
				if (onLoad) {
					onLoad(r);
				}
			},error: function (){

			}
		});
	}

	private url(uri) {
		return this.backendUrl + uri;
	}

}