package org.camunda.education.educationTrail.services;

import java.util.Random;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class DeliverBooksService {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

  public Boolean deliver(int booksNumber) throws InterruptedException {

    Random random = new Random();
    int timeToWait = random.nextInt(10000);
    Thread.sleep(timeToWait);

    int possibilityForError = random.nextInt(2);
    if (possibilityForError == 1) {
      throw new BpmnError("deliverBooksError", "Error with delivering books");
    }
    LOGGER.info("Delivering books......." + booksNumber);
    return true;


  }

}
