#!/bin/bash
#
# $Id:$ 
#
# set -x



# Installing Crontabs

if [ -x /usr/bin/crontab ]; then
  echo ""
  echo "Installing Crontab (if any)"
  if [ -f  ${APPROOT}/${PACKAGE}/cron/${DEPTYPE}.crontab ]; then 
    dos2unix ${APPROOT}/${PACKAGE}/cron/${DEPTYPE}.crontab 2>/dev/null
    crontab ${APPROOT}/${PACKAGE}/cron/${DEPTYPE}.crontab
  fi
fi

if [ -f ${APPROOT}/${PACKAGE}/resources/${DEPTYPE}/setenv.sh ]; then
	cp -f ${APPROOT}/${PACKAGE}/resources/${DEPTYPE}/setenv.sh /tomee/bin/setenv.sh
fi

dos2unix /tomee/bin/*.sh

#Set deployment type information in system properties
#empty to start in new line
echo "" >> /tomee/conf/system.properties

echo "environment.properties.home=${APPROOT}/${PACKAGE}/properties" >> /tomee/conf/system.properties
echo "environment.code=${DEPTYPE}" >> /tomee/conf/system.properties
echo "deployment.build.id=`cat ${APPROOT}/${PACKAGE}/BUILD_ID`" >> /tomee/conf/system.properties

#Needed once to clean up the existing logback.xml
rm /tomee/conf/logback.xml
rm /tomee/lib/postgresql-9.4-1204-jdbc42.jar

if [ -f ${APPROOT}/${PACKAGE}/resources/${DEPTYPE}/logging.properties ]; then
	cp -f ${APPROOT}/${PACKAGE}/resources/${DEPTYPE}/logging.properties /tomee/conf/.
fi

if [ -f ${APPROOT}/${PACKAGE}/resources/${DEPTYPE}/manager-context.xml ]; then
	cp -f ${APPROOT}/${PACKAGE}/resources/${DEPTYPE}/manager-context.xml /tomee/webapps/manager/META-INF/context.xml
fi

rm /tomee/webapps/FileNavigator.war