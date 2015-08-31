package org.camunda.bpm.extension.jetty.impl.deployment;

import org.camunda.bpm.container.impl.deployment.AbstractParseBpmPlatformXmlStep;
import org.camunda.bpm.container.impl.spi.DeploymentOperation;
import org.camunda.bpm.engine.ProcessEngineException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

/**
 * Created by chris on 16.08.2015.
 */
public class JettyParseBpmPlatformXmlStep extends AbstractParseBpmPlatformXmlStep {

  public static final String JETTY_HOME = "jetty.home";
  public static final String JETTY_BASE = "jetty.base";

  @Override
  public URL getBpmPlatformXmlStream(DeploymentOperation operationContext) {
    URL fileLocation = lookupBpmPlatformXml();

    if (fileLocation == null) {
      fileLocation = lookupBpmPlatformXmlFromJettyDirectory();
    }

    return fileLocation;
  }

  public URL lookupBpmPlatformXmlFromJettyDirectory(String directory) {
    String bpmPlatformFileLocation = directory + File.separator + "etc" + File.separator + BPM_PLATFORM_XML_FILE;

    try {
      URL fileLocation = checkValidFileLocation(bpmPlatformFileLocation);

      if (fileLocation != null) {
        LOGGER.log(Level.INFO, "Found Camunda BPM platform configuration in JETTY_BASE/JETTY_HOME conf directory [" + bpmPlatformFileLocation + "] at " + fileLocation.toString());
      }

      return fileLocation;
    } catch (MalformedURLException e) {
      throw new ProcessEngineException("'" + bpmPlatformFileLocation + "' is not a valid Camunda BPM platform configuration resource location.", e);
    }
  }

  public URL lookupBpmPlatformXmlFromJettyDirectory() {
    String jettyDir = System.getProperty(JETTY_BASE);

    if (jettyDir != null) {
      return lookupBpmPlatformXmlFromJettyDirectory(jettyDir);
    } else {
      jettyDir = System.getProperty(JETTY_HOME);
      return lookupBpmPlatformXmlFromJettyDirectory(jettyDir);
    }
  }

}
