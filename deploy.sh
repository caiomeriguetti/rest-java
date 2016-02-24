#!/bin/bash

#Distributor ID:	Ubuntu
#Description:	Ubuntu 14.04.2 LTS
#Release:	14.04
#Codename:	trusty

function appdir {
  pwd
}

appPath=$(appdir)
tomcatAppsDir="/var/lib/tomcat7"

#backend
cd $appPath/backend
sudo mvn clean package
sudo mkdir -p $tomcatAppsDir/servers-backend/
sudo chmod 777 -R $tomcatAppsDir/servers-backend/
sudo cp -a target/rest-java.war $tomcatAppsDir/servers-backend/ROOT.war

#frontend
cd $appPath/ui/src/main/webapp
sudo npm install
cd $appPath/ui
sudo mvn clean package
sudo mkdir -p $tomcatAppsDir/servers-ui/
sudo chmod 777 -R $tomcatAppsDir/servers-ui/
sudo cp -a target/servers-ui.war $tomcatAppsDir/servers-ui/ROOT.war
sudo service tomcat7 restart
