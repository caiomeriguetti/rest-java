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
sudo rm -rf $tomcatAppsDir/servers-backend/ROOT.war
sudo rm -rf $tomcatAppsDir/servers-backend/ROOT
sudo cp -a target/rest-java.war $tomcatAppsDir/servers-backend/ROOT.war

#frontend
cd $appPath/ui/src/main/webapp
sudo npm install
cd $appPath/ui
sudo mvn clean package
sudo mkdir -p $tomcatAppsDir/servers-ui/
sudo rm -rf $tomcatAppsDir/servers-ui/ROOT.war
sudo rm -rf $tomcatAppsDir/servers-ui/ROOT
sudo cp -a target/servers-ui.war $tomcatAppsDir/servers-ui/ROOT.war
sudo service tomcat7 force-reload
