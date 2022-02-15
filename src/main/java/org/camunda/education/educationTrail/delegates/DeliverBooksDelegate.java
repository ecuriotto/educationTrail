package org.camunda.education.educationTrail.delegates;



import java.util.Random;
import javax.inject.Named;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("deliverBooks")
public class DeliverBooksDelegate implements JavaDelegate {


  private final Logger LOGGER = LoggerFactory.getLogger(DeliverBooksDelegate.class.getName());

  public void execute(DelegateExecution execution) throws Exception {
    Random random = new Random();
    int timeToWait = random.nextInt(10000);
    Thread.sleep(timeToWait);
    Integer booksNumber = (Integer) execution.getVariable("booksNumber");

    int possibilityForError = random.nextInt(2);
    if (possibilityForError == 1) {
      throw new BpmnError("deliverBooksError", "Error with delivering books");
    }

    LOGGER.info("Deliver books......." + booksNumber);
  }
}
