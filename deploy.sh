#!/bin/bash

function appdir {
	pwd
}

appPath=$(appdir)
tomcatAppsDir="/var/lib/tomcat7"
tomcatConfigPath="/etc/tomcat7"

#jdk
sudo apt-get install default-jdk

#tomcat7
sudo apt-get install -y tomcat7
sudo cp $tomcatConfigPath/server.xml $tomcatConfigPath/server.xml.backup
sudo cp $appPath/server.xml.template $tomcatConfigPath/server.xml
sudo service tomcat7 restart

#mongodb
sudo apt-get install -y mongodb-org

#maven
sudo apt-get install maven

#backend
cd $appPath/backend
mvn clean package
sudo mkdir -p $tomcatAppsDir/servers-backend/
sudo chmod 777 -R $tomcatAppsDir/servers-backend/
sudo cp -a target/rest-java.war $tomcatAppsDir/servers-backend/ROOT.war

#frontend
cd $appPath/ui/src/main/webapp
sudo npm install
cd $appPath/ui
sudo npm install
mvn clean package
sudo mkdir -p $tomcatAppsDir/servers-ui/
sudo chmod 777 -R $tomcatAppsDir/servers-ui/
sudo cp -a target/servers-ui.war $tomcatAppsDir/servers-ui/ROOT.war



