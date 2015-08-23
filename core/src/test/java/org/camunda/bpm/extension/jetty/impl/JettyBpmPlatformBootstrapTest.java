package org.camunda.bpm.extension.jetty.impl;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.ProcessApplicationService;
import org.camunda.bpm.ProcessEngineService;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;
import org.eclipse.jetty.jndi.NamingUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.File;
import java.net.URI;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.notNullValue;

public class JettyBpmPlatformBootstrapTest {

  private static final Logger LOG = Log.getLogger(JettyBpmPlatformBootstrapTest.class);

  private static final String[] CONFIG_FILES = {"src/main/config/etc/camunda.xml", "src/test/resources/etc/jetty.xml"};

  private static Server server;
  private static URI serverHttpURI;

  static {
     NamingUtil.__log.setDebugEnabled(true);
  }

  @Before
  public void startServer() throws Exception {

    server = new Server(0);

    for (String configFile : CONFIG_FILES) {
      XmlConfiguration configuration = new XmlConfiguration(
        new File(configFile).toURI().toURL());
      configuration.configure(server);
    }

    server.start();

    String host = ((ServerConnector)server.getConnectors()[0]).getHost();
    if (host == null)
    {
      host = "localhost";
    }
    int port = ((ServerConnector)server.getConnectors()[0]).getLocalPort();
    serverHttpURI = new URI(String.format("http://%s:%d/",host,port));
  }

  @After
  public void stopServer()
  {
    try
    {
      server.stop();
    }
    catch (Exception e)
    {
      LOG.warn(e);
    }
  }

  @Test
  public void camundaBpmPlatformStartup() throws Exception {
    // datasource
    InitialContext ic = new InitialContext();
    DataSource myDS = (DataSource)ic.lookup("jdbc/ProcessEngine");
    assertThat(myDS, notNullValue());

    // default engine
    ProcessEngine defaultProcessEngine = BpmPlatform.getDefaultProcessEngine();
    assertThat(defaultProcessEngine, notNullValue());

    // JNDI - ProcessEngineService
    ProcessEngineService processEngineService = (ProcessEngineService) ic.lookup("global/camunda-bpm-platform/process-engine/ProcessEngineService");
    assertThat(processEngineService, notNullValue());
    assertThat(processEngineService.getDefaultProcessEngine(), equalTo(defaultProcessEngine));

    // JNDI - ProcessApplicationService
    ProcessApplicationService processApplicationService = (ProcessApplicationService) ic.lookup("global/camunda-bpm-platform/process-engine/ProcessEngineService");
    assertThat(processApplicationService, notNullValue());
    assertThat(processApplicationService.getProcessApplicationNames(), empty());
  }
}
