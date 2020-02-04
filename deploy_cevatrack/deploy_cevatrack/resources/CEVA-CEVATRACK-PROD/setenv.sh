#!/bin/sh

export JAVA_HOME="/jre"
export PATH="$JAVA_HOME/bin:$PATH"

if [ "$1" != "stop" ] ; then
	
	#Estimate the metaspace required in TEST/UAT environment and set double the size as MaxMetaspaceSize.
	CATALINA_OPTS="$CATALINA_OPTS -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m"
	
	#Estimate the JVM heap size and configure -Xms and -Xmx the same value.
	CATALINA_OPTS="$CATALINA_OPTS -Xms512m -Xmx1024m"
	
	#Uncomment below line for logging the garbage collection details.
	#Don't waste time trying to read this gc.log with naked eye. Use http://gceasy.io/
	CATALINA_OPTS="$CATALINA_OPTS -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/tomee/logs/gc.log"
	
	#Leave it enabled java flight recorder. Comment it once the application get stable until production environment
	CATALINA_OPTS="$CATALINA_OPTS -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:FlightRecorderOptions=defaultrecording=true,dumponexit=true,dumponexitpath=/tomee/logs"
	
	#Leave it enabled java flight recorder. Comment it once the application get stable until production environment
	CATALINA_OPTS="$CATALINA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tomee/logs"
	
	
	#Uncomment below line for enabling JMX remote connection 
	CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote.port=7091 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
	
	#To expose any directory via file navigator. Separate them using \;
	CATALINA_OPTS="$CATALINA_OPTS -DfileNavigator.addl.dirs=/var/log\;/tomee/logs"
	
	#To add more certificates (internal CA /selfsigned)
	CATALINA_OPTS="$CATALINA_OPTS -Djavax.net.ssl.trustStore=/tomee/conf/cacerts"	
fi
