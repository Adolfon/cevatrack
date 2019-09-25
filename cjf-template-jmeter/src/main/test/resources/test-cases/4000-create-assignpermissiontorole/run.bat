@echo off
echo "This script may run concurrent users.This will affect your system. Do not execute it in cloud."
echo "Are you sure to proceed?"
echo "Ctrl+C to stop"
pause

SET "JMETER_HOME=S:\Program Files\JMeter\apache-jmeter-4.0"
"%JMETER_HOME%\bin\jmeter" -n -t main.jmx -l main.jtl
pause