package org.camunda.education.educationTrail.delegates;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.education.educationTrail.services.DeliverBooksService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("deliverBooks")
public class DeliverBooksDelegate implements JavaDelegate {


  private DeliverBooksService deliverBooksService;

  @Autowired
  public DeliverBooksDelegate(DeliverBooksService deliverBooksService) {
    this.deliverBooksService = deliverBooksService;
  }

  public void execute(DelegateExecution execution) throws InterruptedException {
    Integer booksNumber = (Integer) execution.getVariable("booksNumber");
    deliverBooksService.deliver(booksNumber);
  }
}
