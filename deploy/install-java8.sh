#!/bin/bash

if [ ! -f jdk-8u73-linux-x64.tar.gz ]; then
  wget --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u73-b02/jdk-8u73-linux-x64.tar.gz
fi

mkdir /opt/jdk
tar -zxf jdk-8u73-linux-x64.tar.gz -C /opt/jdk
update-alternatives --install /usr/bin/java java /opt/jdk/jdk1.8.0_73/bin/java 2110
update-alternatives --install /usr/bin/javac javac /opt/jdk/jdk1.8.0_73/bin/javac 2110
