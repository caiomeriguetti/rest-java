#!/bin/bash

#Distributor ID:	Ubuntu
#Description:	Ubuntu 14.04.2 LTS
#Release:	14.04
#Codename:	trusty

function appdir {
  pwd
}

export JAVA_HOME="/opt/jdk/jdk1.8.0_73/"

appPath="$(appdir)/.."
tomcatInstancePath="$appPath/tomcat-instance"
serverXmlPath="$tomcatInstancePath/conf/server.xml"

echo "============== CONFIGURING TOMCAT7 ==============="
sudo service tomcat7 stop
cd $appPath
if [ ! -f tomcatConfigPath ]; then
  sudo fuser -k 8080/tcp
  sudo fuser -k 8005/tcp
  tomcat7-instance-create "tomcat-instance"
fi

cd "$appPath/deploy"
sudo ./configure-tomcat7.py $serverXmlPath

cd $tomcatInstancePath
sudo ./bin/startup.sh

#backend
echo "================ BUILDING BACKEND ==============="
cd $appPath/backend
sudo mvn clean package
echo "================ DEPLOYING BACKEND INTO TOMCAT ==============="
sudo mkdir -p $tomcatInstancePath/servers-backend/
sudo rm -rf $tomcatInstancePath/servers-backend/ROOT.war
sudo rm -rf $tomcatInstancePath/servers-backend/ROOT
sudo cp -a target/rest-java.war $tomcatInstancePath/servers-backend/ROOT.war


#frontend
echo "================ BUILDING FRONTEND ==============="
cd $appPath/ui/src/main/webapp
sudo npm install
cd $appPath/ui
sudo mvn clean package
echo "================ DEPLOYING FRONTEND INTO TOMCAT ==============="
sudo mkdir -p $tomcatInstancePath/servers-ui/
sudo mkdir -p $tomcatInstancePath/servers-ui2/
sudo rm -rf $tomcatInstancePath/servers-ui/ROOT.war
sudo rm -rf $tomcatInstancePath/servers-ui/ROOT
sudo rm -rf $tomcatInstancePath/servers-ui2/ROOT.war
sudo rm -rf $tomcatInstancePath/servers-ui2/ROOT
sudo cp -a target/servers-ui.war $tomcatInstancePath/servers-ui/ROOT.war
sudo cp -a target/servers-ui.war $tomcatInstancePath/servers-ui2/ROOT.war

echo "================ RESTARTING MONGODB INSTANCE ==============="
source init-mongodb.sh

sudo apt-key update
sudo apt-get update
