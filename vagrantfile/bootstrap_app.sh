#!/usr/bin/env bash
# java -version
# mkdir /home/vagrant/jmp7
# git clone https://github.com/ruslantkachuk/JMP7_InfrastructureAsCode.git /home/vagrant/jmp7
# cd /home/vagrant/jmp7/vagrant
# gradle build
# rm -r /opt/tomcat/webapps/ROOT
# unzip -o /home/vagrant/jmp7/vagrant/build/libs/jmp.war -d /home/vagrant/jmp7/vagrant/build/libs/jmp
# ln -s /home/vagrant/jmp7/vagrant/build/libs/jmp /opt/tomcat/webapps/ROOT
# /opt/tomcat/bin/startup.sh
sudo systemctl start jenkins.service
sudo systemctl enable jenkins.service
