FROM ubuntu:16.04

RUN apt-get update && apt-get install -y software-properties-common python-software-properties \
    && add-apt-repository ppa:webupd8team/java \
    && apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927 \
    && echo "deb http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.2 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-3.2.list \
    && apt-get update

RUN apt-get install -y tomcat7
RUN apt-get install -y mongodb-org
RUN echo yes | apt-get install -y oracle-java8-installer \
    && echo yes |  apt-get install -y oracle-java8-set-default
RUN apt-get -y install maven
RUN apt-get -y install supervisor

COPY supervisor.conf /etc/supervisor/conf.d/supervisor.conf
COPY . /app

RUN cd /app/backend && mvn clean package \
    && mkdir -p /var/lib/tomcat7/servers-backend \
    && rm -rf /var/lib/tomcat7/servers-backend/ROOT.war \
    && rm -rf /var/lib/tomcat7/servers-backend/ROOT \
    && cp -a target/rest-java.war /var/lib/tomcat7/servers-backend/ROOT.war \
    && cp /app/server.xml.template /etc/tomcat7/server.xml
