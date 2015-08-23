Camunda BPM extension - Jetty
=============================

[![Build Status](https://travis-ci.org/hawky-4s-/camunda-bpm-jetty.svg?branch=master)](https://travis-ci.org/hawky-4s-/camunda-bpm-jetty)
[![Build Status](https://drone.io/github.com/hawky-4s-/camunda-bpm-jetty/status.png)](https://drone.io/github.com/hawky-4s-/camunda-bpm-jetty/latest)
[![Stories in Ready](https://badge.waffle.io/hawky-4s-/camunda-bpm-jetty.png?label=ready&title=Ready)](https://waffle.io/hawky-4s-/camunda-bpm-jetty)

This community extension allows to run the Camunda BPM engine as a shared engine on Eclipse's Jetty servlet container like Tomcat.


Usage
-----

```
export JETTY_HOME=/opt/jetty-9.1.3.v20140225/
```

```
mkdir /tmp/demo
cd /tmp/demo
java -jar $JETTY_HOME/start.jar --add-to-startd=http,spdy,jmx,deploy
```

