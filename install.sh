#!/bin/bash

#Distributor ID:	Ubuntu
#Description:	Ubuntu 14.04.2 LTS
#Release:	14.04
#Codename:	trusty

function appdir {
  pwd
}

appPath=$(appdir)

tomcatConfigPath="/etc/tomcat7"

#jdk
sudo apt-get install -y default-jdk

#tomcat7
sudo apt-get install -y tomcat7
if [ ! -f $tomcatConfigPath/server.xml.backup ]; then
  sudo cp $tomcatConfigPath/server.xml $tomcatConfigPath/server.xml.backup
fi

sudo cp $appPath/server.xml.template $tomcatConfigPath/server.xml

#mongodb
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927
echo "deb http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.2 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.2.list
sudo apt-get update
sudo apt-get install -y mongodb-org

#maven
sudo apt-get -y install maven
