#!/bin/bash

#Distributor ID:	Ubuntu
#Description:	Ubuntu 14.04.2 LTS
#Release:	14.04
#Codename:	trusty

appPath=$(pwd)

tomcatConfigPath="/etc/tomcat7"

sudo apt-get install -y software-properties-common python-software-properties
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927
echo "deb http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.2 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.2.list
sudo apt-get update

#python
sudo ./install-python.sh

#jdk
sudo ./install-java8.sh

#tomcat7
sudo apt-get install -y tomcat7

#mongodb
sudo apt-get install -y mongodb-org

#maven
sudo apt-get -y install maven

#npm+node
sudo ./install-node.sh

sudo apt-key update
sudo apt-get update


