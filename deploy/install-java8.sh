#!/bin/bash

source check-tmp.sh

if [ ! -f tmp/jdk-8u73-linux-x64.tar.gz ]; then
  wget --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u73-b02/jdk-8u73-linux-x64.tar.gz -O tmp/jdk-8u73-linux-x64.tar.gz
fi

mkdir -p /opt/jdk
tar -zxf tmp/jdk-8u73-linux-x64.tar.gz -C /opt/jdk
update-alternatives --install /usr/bin/java java /opt/jdk/jdk1.8.0_73/bin/java 2110
update-alternatives --install /usr/bin/javac javac /opt/jdk/jdk1.8.0_73/bin/javac 2110
