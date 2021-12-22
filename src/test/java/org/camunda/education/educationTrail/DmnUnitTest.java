package org.camunda.education.educationTrail;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import java.util.Map;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.extension.junit5.test.ProcessEngineExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ProcessEngineExtension.class)
public class DmnUnitTest {

  @Test
  @Deployment(resources = "decisionDiscount.dmn")
  public void testDiscountSilverInf3() {
    Map<String, Object> variables = withVariables("readerType", "silver", "feedbacksGiven", 2);
    DmnDecisionTableResult decisionResult =
        decisionService().evaluateDecisionTableByKey("decisionDiscount", variables);
    assertThat(decisionResult.getFirstResult()).contains(entry("discount", 10));

  }

  @Test
  @Deployment(resources = "decisionDiscount.dmn")
  public void testDiscountSilverSup3() {
    Map<String, Object> variables = withVariables("readerType", "silver", "feedbacksGiven", 4);
    DmnDecisionTableResult decisionResult =
        decisionService().evaluateDecisionTableByKey("decisionDiscount", variables);
    assertThat(decisionResult.getFirstResult()).contains(entry("discount", 20));

  }

  @Test
  @Deployment(resources = "decisionDiscount.dmn")
  public void testDiscountGoldInf3() {
    Map<String, Object> variables = withVariables("readerType", "gold", "feedbacksGiven", 2);
    DmnDecisionTableResult decisionResult =
        decisionService().evaluateDecisionTableByKey("decisionDiscount", variables);
    assertThat(decisionResult.getFirstResult()).contains(entry("discount", 20));

  }

  @Test
  @Deployment(resources = "decisionDiscount.dmn")
  public void testDiscountGoldSup3() {
    Map<String, Object> variables = withVariables("readerType", "gold", "feedbacksGiven", 5);
    DmnDecisionTableResult decisionResult =
        decisionService().evaluateDecisionTableByKey("decisionDiscount", variables);
    assertThat(decisionResult.getFirstResult()).contains(entry("discount", 30));

  }

  @Test
  @Deployment(resources = "decisionDiscount.dmn")
  public void testDiscountDiamondInf3() {
    Map<String, Object> variables = withVariables("readerType", "diamond", "feedbacksGiven", 2);
    DmnDecisionTableResult decisionResult =
        decisionService().evaluateDecisionTableByKey("decisionDiscount", variables);
    assertThat(decisionResult.getFirstResult()).contains(entry("discount", 30));

  }

  @Test
  @Deployment(resources = "decisionDiscount.dmn")
  public void testDiscountDiamondSup3() {
    Map<String, Object> variables = withVariables("readerType", "diamond", "feedbacksGiven", 6);
    DmnDecisionTableResult decisionResult =
        decisionService().evaluateDecisionTableByKey("decisionDiscount", variables);
    assertThat(decisionResult.getFirstResult()).contains(entry("discount", 50));

  }
}
