package com.globo.teste.tests;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.globo.teste.model.Server;


public class ServersResourceTest {
	
	private static String serverUrl = Env.getBackendUrl();
	
	private Client client;
	private WebTarget webTarget;
	
	@BeforeClass
	public void setUp() {
		client = ClientBuilder.newClient();
		webTarget = client.target(serverUrl);
	}
	
	@Test
	public void testInstallListRemovePackageOnServer() {
		Server onlineServer = new Server();
		onlineServer.setIp(Env.getTestServerIp());
		onlineServer.setUser(Env.getTestServerUsername());
		onlineServer.setPassword(Env.getTestServerPassword());
		
		//adding a server with valid credentials( and that also is online)
		
		MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
		
		formData.add("ip", onlineServer.getIp());
		formData.add("user", onlineServer.getUser());
		formData.add("password", onlineServer.getPassword());
		formData.add("distribution", onlineServer.getDistribution());
		
		Invocation.Builder request = invocation(webTarget.path("servers"));
		Response response = request.post(Entity.form(formData));
		
		String entity = response.readEntity(String.class);
		JSONObject responseObject = new JSONObject(entity);
		
		//installing the package on the server
		String serverId = responseObject.getString("id");
		request = invocation(webTarget.path("servers/"+serverId+"/iotop"));
		
		response = request.put(Entity.form(new MultivaluedHashMap<String, String>()));
		
		Assert.assertEquals(response.getStatus(), 200);
		
		//getting the list of packages
		request = invocation(webTarget.path("servers/"+serverId+"/packages"));
		response = request.get();
		
		entity = response.readEntity(String.class);
		
		Assert.assertNotNull(entity);
		
		JSONArray packageList = new JSONArray(entity);
		
		Assert.assertNotNull(packageList);
		Assert.assertTrue(packageList.length() >= 1);
		
		//deleting the package from the server
		request = invocation(webTarget.path("servers/"+serverId+"/iotop"));
		response = request.delete();

		Assert.assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void testDeleteServer() {
		
		//adding
		Invocation.Builder request = invocation(webTarget.path("servers"));
		
		MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
		
		Server serverData = new Server();
		serverData.setIp("192.168.100.103");
		serverData.setUser("sampleusername");
		serverData.setPassword("thisisthepassword");
		serverData.setDistribution("Debian");
		
		formData.add("ip", serverData.getIp());
		formData.add("user", serverData.getUser());
		formData.add("password", serverData.getPassword());
		formData.add("distribution", serverData.getDistribution());
		
		Response response = request.post(Entity.form(formData));
		String entity = response.readEntity(String.class);
		JSONObject responseObject = new JSONObject(entity);
		String id = responseObject.getString("id");

		//deleting
		request = invocation(webTarget.path("servers/"+id));
		response = request.delete();
		
		Assert.assertEquals(response.getStatus(), 200);
		
	}
	
	@Test
	public void testListServers() throws Exception {
		Invocation.Builder request = invocation(webTarget.path("servers"));
		Response resp = request.get();
		
		String serversJson = resp.readEntity(String.class);
		Assert.assertNotNull(serversJson);
		Assert.assertTrue(serversJson.length() >= 2); // at least the "[]" string
		
		JSONArray servers = new JSONArray(serversJson);
		Assert.assertNotNull(servers);
		
		servers.length();
	}
	
	@Test
	public void testAddUpdateAndGetById() throws Exception {
		
		//adding new server
		Invocation.Builder request = invocation(webTarget.path("servers"));
		
		MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
		
		Server serverData = new Server();
		serverData.setIp("192.168.100.103");
        serverData.setUser("sampleusername");
        serverData.setPassword("thisisthepassword");
        serverData.setDistribution("Debian");
		
		formData.add("ip", serverData.getIp());
		formData.add("user", serverData.getUser());
		formData.add("password", serverData.getPassword());
		formData.add("distribution", serverData.getDistribution());
		
		Response response = request.post(Entity.form(formData));
	
		String entity = response.readEntity(String.class);
		
		Assert.assertNotNull(entity);
		Assert.assertTrue(entity.length() > 0);
		
		JSONObject responseObject = new JSONObject(entity);
		String id = responseObject.getString("id");
		
		Assert.assertNotNull(id);
		Assert.assertTrue(responseObject.getString("id").length() > 3);
		
		//updating
		request = invocation(webTarget.path("servers/"+id));
		formData = new MultivaluedHashMap<String, String>();

		serverData = new Server();
		serverData.setIp("103.100.168.192");
		serverData.setUser("username");
		serverData.setPassword("password");
		serverData.setDistribution("Fedora");
		
		formData.add("ip", serverData.getIp());
		formData.add("user", serverData.getUser());
		formData.add("password", serverData.getPassword());
		formData.add("distribution", serverData.getDistribution());
		
		response = request.post(Entity.form(formData));
		Assert.assertEquals(response.getStatus(), 200);
		
		//getting by id to check if update was done successfully
		request = invocation(webTarget.path("servers/"+id));
		response = request.get();
		
		entity = (String)response.readEntity(String.class);
		responseObject = new JSONObject(entity);
		
		Assert.assertEquals(responseObject.getString("user"), serverData.getUser());
		Assert.assertEquals(responseObject.getString("ip"), serverData.getIp());
		Assert.assertEquals(responseObject.getString("distribution"), serverData.getDistribution());
		Assert.assertEquals(responseObject.getString("password"), serverData.getPassword());
	}
	
	private Invocation.Builder invocation(WebTarget target) {
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		invocationBuilder.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36");
		invocationBuilder.header("Content-Type", "text/plain; charset=utf-8");
		invocationBuilder.header("Accept", "*/*");
		invocationBuilder.header("Accept-Encoding", "gzip, deflate, sdch");
		invocationBuilder.header("Accept-Language", "en-US,en;q=0.8");
		
		return invocationBuilder;
	}

}
