#!/bin/sh

export JETTY_HOME="$(dirname "$0")/server/jetty-distribution-${version.jetty}"
export JETTY_BASE="$(dirname "$0")/server/jetty-distribution-${version.jetty}/cammunda-base"

BROWSERS="gnome-www-browser x-www-browser firefox chromium chromium-browser google-chrome"

echo "Starting Camunda BPM platform on Jetty Server";

if [ -z "$BROWSER" ]; then
  for executable in $BROWSERS; do
    BROWSER=`which $executable 2> /dev/null`
    if [ -n "$BROWSER" ]; then
      break;
    fi
  done
fi

if [ -z "$BROWSER" ]; then
  (sleep 15; echo -e "We are sorry... We tried all we could do but we couldn't locate your default browser... \nIf you want to see our default website please open your browser and insert this URL:\nhttp://localhost:8080/camunda-welcome/index.html";) &
else
  (sleep 5; $BROWSER "http://localhost:8080/camunda-welcome/index.html";) &
fi

/bin/sh "$(dirname "$0")/server/jetty-distribution-${version.jetty}/bin/startup.sh"
