#!/usr/bin/env bash
# Install wget zip unzip git
sudo yum -y update
sudo yum -y install wget
sudo yum -y install zip
sudo yum -y install unzip
sudo yum -y install git
sudo yum -y install psmisc
cd /opt/

# Install JAVA 8
echo "Install JAVA 8 ..."
sudo mkdir /opt/java
sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.rpm" -P /opt/java/
sudo yum localinstall -y /opt/java/jdk-8u131-linux-x64.rpm

# Install Jenkins
echo "Install Jenkins ..."
sudo wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat-stable/jenkins.repo
sudo rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key
sudo yum -y install jenkins

# Copy Jenkins plugins, jobs and other from artifactory
echo "Copy Jenkins plugins, jobs and other from artifactory ..."
#sudo rm -r -f /var/lib/jenkins/*
sudo mkdir /opt/jenkins
sudo curl -L "https://dl.bintray.com/ruslantkachuk/jmp_jenkins/jenkins.zip" -o /opt/jenkins/jenkins.zip
sudo curl -L "https://dl.bintray.com/ruslantkachuk/jmp_jenkins/jenkins2.zip" -o /opt/jenkins/jenkins2.zip
sudo unzip -d /var/lib/jenkins /opt/jenkins/jenkins.zip
sudo unzip -d /var/lib/jenkins /opt/jenkins/jenkins2.zip

# Install Gradle 3.5
# echo "Install Gradle 3.5 ..."
# wget --quiet https://services.gradle.org/distributions/gradle-3.5-all.zip
# mkdir /opt/gradle
# unzip -d /opt/gradle gradle-3.5-all.zip
# Install Tomcat 8
# echo "Install Tomcat 8.5.14 ..."
# wget --quiet http://archive.apache.org/dist/tomcat/tomcat-8/v8.5.14/bin/apache-tomcat-8.5.14.tar.gz
# tar xvf apache-tomcat-8.5.14.tar.gz
# mv apache-tomcat-8.5.14 tomcat
# Download HSQLDB JDBC Driver
# echo "Install HSQLDB JDBC Driver ..."
# wget --quiet https://sourceforge.net/projects/hsqldb/files/hsqldb/hsqldb_2_4/hsqldb-2.4.0.zip/download?use_mirror=pilotfiber -O hsqldb-2.4.0.zip
# mkdir /opt/hsqldb
# unzip -d /opt/hsqldb hsqldb-2.4.0.zip
# cp /opt/hsqldb/hsqldb-2.4.0/hsqldb/lib/hsqldb.jar /opt/tomcat/lib
