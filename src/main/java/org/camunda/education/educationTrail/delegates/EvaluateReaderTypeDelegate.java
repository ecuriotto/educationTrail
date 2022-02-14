package org.camunda.education.educationTrail.delegates;

import java.util.Random;
import javax.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("evaluateReaderType")
public class EvaluateReaderTypeDelegate implements JavaDelegate {


  private final Logger LOGGER = LoggerFactory.getLogger(EvaluateReaderTypeDelegate.class.getName());

  public void execute(DelegateExecution execution) throws Exception {
    Random random = new Random();
    int timeToWait = random.nextInt(10000);
    Thread.sleep(timeToWait);
    System.out.println("evaluate Reader Type");
    LOGGER.info("evaluate Reader Type");
  }
}
