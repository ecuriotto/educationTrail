package org.camunda.education.educationTrail;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.mockito.ArgumentMatchers.*;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import org.camunda.education.educationTrail.delegates.DeliverBooksDelegate;
import org.camunda.education.educationTrail.delegates.EvaluateReaderTypeDelegate;
import org.camunda.education.educationTrail.delegates.ProposeSuggestionsDelegate;
import org.camunda.education.educationTrail.delegates.RegisterTimeoutDelegate;
import org.camunda.education.educationTrail.services.DeliverBooksService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;


// import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */


@ExtendWith({SpringExtension.class})
@ExtendWith(ProcessEngineCoverageExtension.class)
public class BookstoreDiscountUnitTest {

  @Mock
  DeliverBooksService deliverBooksService;

  @Deployment(resources = {"bookstorediscounts.bpmn", "decisionDiscount.dmn"})
  @Test
  public void testHappyPath() throws InterruptedException {
    Mocks.register("evaluateReaderType", new EvaluateReaderTypeDelegate());
    Mocks.register("proposeSuggestions", new ProposeSuggestionsDelegate());
    Mocks.register("deliverBooks", new DeliverBooksDelegate(deliverBooksService));

    when(deliverBooksService.deliver(anyInt())).thenReturn(true);

    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("readerType", "silver");
    variables.put("feedbacksGiven", "3");
    variables.put("booksNumber", 2);



    ProcessInstance processInstance =
        runtimeService().startProcessInstanceByKey("BookstoreDiscount", variables);

    assertThat(processInstance).isStarted().isWaitingAt("waitingCustomer");

    EventSubscription subscription = runtimeService().createEventSubscriptionQuery()
        .processInstanceId(processInstance.getId()).eventName("Message_booksBought").singleResult();


    System.out.println("subscription....");
    System.out.println(subscription.getEventName());
    runtimeService().messageEventReceived(subscription.getEventName(),
        subscription.getExecutionId());

    assertThat(processInstance).isEnded();

  }

  @Deployment(resources = {"bookstorediscounts.bpmn", "decisionDiscount.dmn"})
  // @Deployment(resources = "bookstorediscounts.bpmn")
  @Test
  public void testUserRejection() throws InterruptedException {
    Mocks.register("evaluateReaderType", new EvaluateReaderTypeDelegate());
    Mocks.register("proposeSuggestions", new ProposeSuggestionsDelegate());


    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("readerType", "silver");
    variables.put("feedbacksGiven", "3");



    ProcessInstance processInstance =
        runtimeService().startProcessInstanceByKey("BookstoreDiscount", variables);

    assertThat(processInstance).isStarted().isWaitingAt("waitingCustomer");

    EventSubscription subscription = runtimeService().createEventSubscriptionQuery()
        .processInstanceId(processInstance.getId()).eventName("Message_rejection").singleResult();


    System.out.println("subscription....");
    System.out.println(subscription.getEventName());
    runtimeService().messageEventReceived(subscription.getEventName(),
        subscription.getExecutionId());

    assertThat(processInstance).isEnded();

  }

  @Deployment(resources = {"bookstorediscounts.bpmn", "decisionDiscount.dmn"})
  // @Deployment(resources = "bookstorediscounts.bpmn")
  @Test
  public void testUserTimeout() {

    Mocks.register("evaluateReaderType", new EvaluateReaderTypeDelegate());
    Mocks.register("proposeSuggestions", new ProposeSuggestionsDelegate());
    Mocks.register("registerTimeout", new RegisterTimeoutDelegate());

    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("readerType", "silver");
    variables.put("feedbacksGiven", "3");


    ProcessInstance processInstance =
        runtimeService().startProcessInstanceByKey("BookstoreDiscount", variables);

    assertThat(processInstance).isStarted().isWaitingAt("waitingCustomer");

    EventSubscription subscription = runtimeService().createEventSubscriptionQuery()
        .processInstanceId(processInstance.getId()).eventName("Message_cancelation").singleResult();


    System.out.println("subscription....");
    System.out.println(subscription.getEventName());
    runtimeService().messageEventReceived(subscription.getEventName(),
        subscription.getExecutionId());

    assertThat(processInstance).isEnded();

  }



}
