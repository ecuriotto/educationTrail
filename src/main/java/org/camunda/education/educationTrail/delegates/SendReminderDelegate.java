package org.camunda.education.educationTrail.delegates;

import javax.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("sendReminder")
public class SendReminderDelegate implements JavaDelegate {


  private final Logger LOGGER = LoggerFactory.getLogger(SendReminderDelegate.class.getName());

  public void execute(DelegateExecution execution) throws Exception {

    LOGGER.info("Sending reminder.........");
  }
}
