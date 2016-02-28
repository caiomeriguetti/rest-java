#!/bin/bash

#Distributor ID:	Ubuntu
#Description:	Ubuntu 14.04.2 LTS
#Release:	14.04
#Codename:	trusty

function appdir {
  pwd
}

tomcatConfigPath="/etc/tomcat7"
appPath=$(appdir)
tomcatAppsDir="/var/lib/tomcat7"

if [ ! -f $tomcatConfigPath/server.xml.backup ]; then
  sudo cp $tomcatConfigPath/server.xml $tomcatConfigPath/server.xml.backup
fi

sudo cp $appPath/server.xml.template $tomcatConfigPath/server.xml

#backend
echo "================ BUILDING BACKEND ==============="
cd $appPath/backend
sudo mvn clean package
echo "================ DEPLOYING BACKEND INTO TOMCAT ==============="
sudo mkdir -p $tomcatAppsDir/servers-backend/
sudo rm -rf $tomcatAppsDir/servers-backend/ROOT.war
sudo rm -rf $tomcatAppsDir/servers-backend/ROOT
sudo cp -a target/rest-java.war $tomcatAppsDir/servers-backend/ROOT.war


#frontend
echo "================ BUILDING FRONTEND ==============="
cd $appPath/ui/src/main/webapp
sudo npm install
cd $appPath/ui
sudo mvn clean package
echo "================ DEPLOYING FRONTEND INTO TOMCAT ==============="
sudo mkdir -p $tomcatAppsDir/servers-ui/
sudo rm -rf $tomcatAppsDir/servers-ui/ROOT.war
sudo rm -rf $tomcatAppsDir/servers-ui/ROOT
sudo cp -a target/servers-ui.war $tomcatAppsDir/servers-ui/ROOT.war
echo "================ RELOADING TOMCAT ==============="
sudo service tomcat7 restart
sudo apt-key update
sudo apt-get update
