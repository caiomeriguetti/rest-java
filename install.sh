#!/bin/bash

#Distributor ID:	Ubuntu
#Description:	Ubuntu 14.04.2 LTS
#Release:	14.04
#Codename:	trusty

appPath=$(pwd)

tomcatConfigPath="/etc/tomcat7"

sudo apt-get install -y software-properties-common python-software-properties
sudo add-apt-repository ppa:webupd8team/java
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927
sudo apt-get update

#jdk
sudo apt-get install -y oracle-java8-installer
sudo apt-get install -y oracle-java8-set-default

#tomcat7
sudo apt-get install -y tomcat7

#mongodb
echo "deb http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.2 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.2.list
sudo apt-get install -y mongodb-org

#maven
sudo apt-get -y install maven

#npm+node
sudo apt-get -y install npm
sudo apt-get -y install nodejs

sudo apt-key update
sudo apt-get update


