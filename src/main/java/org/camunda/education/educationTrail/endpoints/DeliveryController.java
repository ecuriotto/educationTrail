package org.camunda.education.educationTrail.endpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.camunda.education.educationTrail.dto.DeliveryResponseMessage;
import org.camunda.education.educationTrail.dto.Feedback;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveryService/api")
public class DeliveryController {

  @RequestMapping(value = "/feedbacks", method = RequestMethod.POST)
  public DeliveryResponseMessage createFeedback(@RequestBody Feedback feedback) {
    System.out.println("rest create Feedback called!!!!");
    System.out.println(feedback.getReaderType());
    DeliveryResponseMessage deliveryResponseMessage = new DeliveryResponseMessage();
    deliveryResponseMessage.setMessage(
        "Very good I like it " + feedback.getReaderType() + " - " + feedback.getFeedbacksGiven());
    deliveryResponseMessage.setaRandomNumber(new Random().nextInt());
    return deliveryResponseMessage;
  }

  @RequestMapping(value = "/feedbacks", method = RequestMethod.GET)
  public List<Feedback> getFeedbacks() {
    List<Feedback> myList = new ArrayList();
    Feedback first = new Feedback();
    first.setReaderType("silver");
    first.setFeedbacksGiven(2);
    myList.add(first);
    return myList;
  }
}
