import {Injectable} from 'angular2/core';

declare var $:any;
declare var CONTEXT_PATH:any;

@Injectable()
export class BackendService {

	private backendUrl: string = CONTEXT_PATH;

	public saveServer(data, onLoad) {
		var url = this.url("/api/servers");
		if (data.id) {
			url += "/"+data.id;
		}

		$.ajax({
			url: url,
			method: "post",
			data: data,
			success: function (r) {
				if (onLoad) {
					onLoad(true, r);
				}
			},error: function (){
				if (onLoad) onLoad(false);
			}
		});
	}

	public delServer(id, onLoad) {
		$.ajax({
			url: this.url("/api/servers/"+id),
			method: "delete",
			success: function (r) {
				if (onLoad) {
					onLoad(true, r);
				}
			},error: function (){
				if (onLoad) onLoad(false);
			}
		});
	}

	public loadServers (onLoadServers) {
		$.ajax({
			url: this.url("/api/servers"),
			method: "get",
			success: function (r) {
				if (onLoadServers) {
					onLoadServers(r);
				}
			},error: function (xhr, ajaxOptions, thrownError){
			}
		});
	}

	public installPackage(serverId, packageName, onLoad) {
		$.ajax({
			url: this.url("/api/servers/"+serverId+"/install/"+packageName),
			method: "put",
			success: function (r) {
				if (onLoad) {
					onLoad(true, r);
				}
			},error: function (){
				if (onLoad) {
					onLoad(false);
				}
			}
		});
	}

	public delPackage(serverId, packageName, onLoad) {
		$.ajax({
			url: this.url("/api/servers/"+serverId+"/uninstall/"+packageName),
			method: "delete",
			success: function (r) {
				if (onLoad) {
					onLoad(true, r);
				}
			},error: function (){
				if (onLoad) {
					onLoad(false);
				}
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