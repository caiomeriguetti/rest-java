#!/bin/sh

#backend
cd /home/caiomeriguetti/javasites/rest-java/backend
mvn clean package
cp -a target/rest-java /var/lib/tomcat7/servers-backend/ROOT

#frontend
cd /home/caiomeriguetti/javasites/rest-java/ui
mvn clean package
cp -a target/servers-ui /var/lib/tomcat7/servers-ui/ROOT



