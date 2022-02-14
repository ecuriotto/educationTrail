package org.camunda.education.educationTrail.delegates;



import java.util.Random;
import javax.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("proposeSuggestions")
public class ProposeSuggestionsDelegate implements JavaDelegate {


  private final Logger LOGGER = LoggerFactory.getLogger(ProposeSuggestionsDelegate.class.getName());

  public void execute(DelegateExecution execution) throws Exception {
    Random random = new Random();
    int timeToWait = random.nextInt(100000);
    Thread.sleep(timeToWait);
    LOGGER.info("Propose suggestions.......");
  }
}
