package org.camunda.bpm.extension.jetty.impl;

import org.camunda.bpm.container.RuntimeContainerDelegate;
import org.camunda.bpm.container.impl.RuntimeContainerDelegateImpl;
import org.camunda.bpm.container.impl.deployment.PlatformXmlStartProcessEnginesStep;
import org.camunda.bpm.container.impl.deployment.StopProcessApplicationsStep;
import org.camunda.bpm.container.impl.deployment.StopProcessEnginesStep;
import org.camunda.bpm.container.impl.deployment.jobexecutor.StartJobExecutorStep;
import org.camunda.bpm.container.impl.deployment.jobexecutor.StartManagedThreadPoolStep;
import org.camunda.bpm.container.impl.deployment.jobexecutor.StopJobExecutorStep;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.extension.jetty.impl.deployment.JettyAttachments;
import org.camunda.bpm.extension.jetty.impl.deployment.JettyParseBpmPlatformXmlStep;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.Jetty;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.Container;
import org.eclipse.jetty.util.component.LifeCycle;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by chris on 15.08.2015.
 */
public class JettyBpmPlatformBootstrap extends AbstractLifeCycle.AbstractLifeCycleListener {

  private final static Logger LOGGER = Logger.getLogger(JettyBpmPlatformBootstrap.class.getName());

  protected RuntimeContainerDelegateImpl containerDelegate;

  protected void deployBpmPlatform(LifeCycle event) {

//    final StandardServer server = (StandardServer) event.getSource();

    containerDelegate.getServiceContainer().createDeploymentOperation("deploy BPM platform")
//        .addAttachment(JettyAttachments.SERVER, server)
        .addStep(new JettyParseBpmPlatformXmlStep())
        .addStep(new StartManagedThreadPoolStep())
        .addStep(new StartJobExecutorStep())
        .addStep(new PlatformXmlStartProcessEnginesStep())
        .execute();

    LOGGER.log(Level.INFO, "Camunda BPM platform" + " successfully started on Jetty " + Server.getVersion() +".");

  }


  protected void undeployBpmPlatform(LifeCycle event) {

//    final StandardServer server = (StandardServer) event.getSource();

    containerDelegate.getServiceContainer().createUndeploymentOperation("undeploy BPM platform")
//        .addAttachment(JettyAttachments.SERVER, server)
        .addStep(new StopProcessApplicationsStep())
        .addStep(new StopProcessEnginesStep())
        .addStep(new StopJobExecutorStep())
        .execute();

    LOGGER.log(Level.INFO, "Camunda BPM platform stopped.");

  }

  @Override
  public void lifeCycleStarted(LifeCycle event) {
    containerDelegate = (RuntimeContainerDelegateImpl) RuntimeContainerDelegate.INSTANCE.get();

    deployBpmPlatform(event);
  }



  @Override
  public void lifeCycleStopping(LifeCycle event) {
    undeployBpmPlatform(event);
  }

}
