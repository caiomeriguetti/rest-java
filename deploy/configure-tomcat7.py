#!/usr/bin/python

import sys
from bs4 import BeautifulSoup as Soup

serverXmlPath = sys.argv[1]

xmlContent=""
xmlFile = open(serverXmlPath, 'r+')
xmlContent=xmlFile.read()
xmlFile.close()

bs = Soup(xmlContent, "xml")

server = bs.find("Server")

backendService = bs.find("Service", {"name": "servers-backend"})
serversUiService = bs.findAll("Service", {"name": "servers-ui"})

if backendService:
	backendService.extract()

if len(serversUiService):
	for uiService in serversUiService:
		uiService.extract()

server.append(Soup("""
<Service name="servers-backend">
  <Connector port="8282" protocol="org.apache.coyote.http11.Http11NioProtocol" />
  <Engine name="Catalina82" defaultHost="localhost">
    <Host name="localhost" appBase="servers-backend" unpackWARs="true" autoDeploy="true" />
  </Engine>
</Service>
""", "xml").find("Service"))

server.append(Soup("""
<Service name="servers-ui">
	<Connector port="7171" protocol="org.apache.coyote.http11.Http11NioProtocol"/>
	<Engine defaultHost="localhost" name="Catalina71">
	  <Host appBase="servers-ui" autoDeploy="true" name="localhost" unpackWARs="true"/>
	</Engine>
</Service>
""", "xml").find("Service"))

server.append(Soup("""
<Service name="servers-ui">
  <Connector port="7070" protocol="org.apache.coyote.http11.Http11NioProtocol"/>
  <Engine defaultHost="localhost" name="Catalina70">
   <Host appBase="servers-ui" autoDeploy="true" name="localhost" unpackWARs="true"/>
  </Engine>
</Service>
""", "xml").find("Service"))

finalContent = bs.prettify("utf-8")
xmlFile = open(serverXmlPath, 'w')
xmlFile.write(finalContent)
xmlFile.close()
