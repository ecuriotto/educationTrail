package org.camunda.education.educationTrail.delegates;



import javax.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("deliverBooks")
public class DeliverBooksDelegate implements JavaDelegate {


  private final Logger LOGGER = LoggerFactory.getLogger(DeliverBooksDelegate.class.getName());

  public void execute(DelegateExecution execution) throws Exception {

    Integer booksNumber = (Integer) execution.getVariable("booksNumber");

    LOGGER.info("Deliver books......." + booksNumber);
  }
}
