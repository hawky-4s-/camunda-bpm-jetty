This is a distribution of

       Camunda BPM platform v${version.camunda}

visit
       http://docs.camunda.org/


Camunda BPM platform is licensed under the Apache License v2.0
http://www.apache.org/licenses/LICENSE-2.0

The packaged Eclipse Jetty server is dual licensed under Eclipse Public v1.0 license and Apache License v2.0.

==================

Contents:

  lib/
        This directory contains the java libraries for application
        development.

  server/
        This directory contains a preconfigured distribution
        of Jetty with Camunda BPM platform readily
        installed.

        run the
            server/jetty-distribution-${version.jetty}/bin/startup.{bat/sh}
        script to start up the the server.

        After starting the server, you can access the
        following web applications:

        http://localhost:8080/camunda
        http://localhost:8080/engine-rest

  sql/
        This directory contains the create and upgrade sql script
        for the different databases.
        The engine create script contain the engine and history tables.

        Execute the current upgrade script to make the database compatible
        with the newest camunda BPM platform release.

==================

camunda BPM platform version: ${version.camunda}
Jetty Server version: ${version.jetty}

=================
