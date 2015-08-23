@echo off

set "JETTY_HOME=%CD%\server\jetty-distribution-${version.jetty}"

echo "starting Camunda BPM platform ${version.camunda} on Jetty ${version.jetty}"

cd server\jetty-distribution-${version.jetty}\bin\
start startup.bat

ping -n 5 localhost > NULL
start http://localhost:8080/camunda-welcome/index.html
