package org.camunda.education.educationTrail;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.junit5.test.ProcessEngineExtension;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import org.camunda.education.educationTrail.delegates.EvaluateReaderTypeDelegate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */

@ExtendWith({SpringExtension.class})
@ExtendWith(ProcessEngineCoverageExtension.class)
public class ProcessUnitTest {

  @Deployment(resources = {"bookstorediscounts.bpmn, decisionDiscount.dmn"})
  @Test
  public void testHappyPath() {
    Mocks.register("evaluateReaderType", new EvaluateReaderTypeDelegate());

    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("readerType", "silver");
    variables.put("feedbacksGiven", "3");


    ProcessInstance processInstance =
        runtimeService().startProcessInstanceByKey("BookstoreDiscount", variables);

    assertThat(processInstance).isStarted().isWaitingAt("waitingCustomer");

    EventSubscription subscription = runtimeService().createEventSubscriptionQuery()
        .processInstanceId(processInstance.getId()).eventType("waitingCustomer").singleResult();

    runtimeService().messageEventReceived(subscription.getEventName(),
        subscription.getExecutionId());


    assertThat(processInstance).isEnded();



  }

}
