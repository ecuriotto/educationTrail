package org.camunda.education.educationTrail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.education.educationTrail.delegates.ConnectSystemDelegate;
import org.camunda.education.educationTrail.delegates.EvaluateReaderTypeDelegate;
import org.camunda.education.educationTrail.delegates.ProposeSuggestionsDelegate;
import org.camunda.education.educationTrail.delegates.RegisterTimeoutDelegate;
import org.camunda.education.educationTrail.delegates.SendReminderDelegate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Deployment(resources = "reader.bpmn")
@ExtendWith(ProcessEngineCoverageExtension.class)
public class ReaderWithScenarioTest {

  public static final String PROCESS_KEY = "ReaderBuysBooks";
  public static final String TASK_SUBMIT_FEEDBACKS = "Task_SubmitFeedbacks";
  public static final String TASK_BUY_BOOKS = "Task_BuyBooks";
  public static final String MSG_GET_SUGGESTIONS = "MSG_getSuggestions";
  public static final String TIM_WAIT_3DAYS = "TIM_wait3Days";

  public static final String END_EVENT_REMINDER = "END_reminder";
  public static final String END_MSG_EVENT_NOT_BOUGHT = "EndMsgEvent_NotBought";
  public static final String END_MSG_EVENT_BOUGHT = "EndMsgEvent_Bought";
  public static final String END_EVENT_NO_FEEDBACKS = "END_noFeedbacks";

  public static final String VAR_FEEDBACK_RECEIVED = "feedbackReceived";
  public static final String VAR_BOOKS_BOUGHT = "booksBought";

  @Mock
  private ProcessScenario testReaderBuyBooks;

  @Mock
  private ConnectSystemDelegate connectToSystem;

  @Mock
  private SendReminderDelegate sendReminder;

  @Mock
  private RegisterTimeoutDelegate registerTimeout;


  @BeforeEach
  public void defaultScenario() {

    Mocks.register("connectToSystem", new ConnectSystemDelegate());
    Mocks.register("sendReminder", new SendReminderDelegate());
    Mocks.register("registerTimeout", new RegisterTimeoutDelegate());


    MockitoAnnotations.openMocks(this);

    // Happy-Path
    when(testReaderBuyBooks.waitsAtUserTask(TASK_SUBMIT_FEEDBACKS)).thenReturn(task -> {
      task.complete(withVariables(VAR_FEEDBACK_RECEIVED, true));
    });

    when(testReaderBuyBooks.waitsAtMessageIntermediateCatchEvent(MSG_GET_SUGGESTIONS))
        .thenReturn(msg -> {
          msg.receive();
        });


  }

  @Test
  public void shouldExecuteHappyPath() {

    when(testReaderBuyBooks.waitsAtUserTask(TASK_BUY_BOOKS)).thenReturn(task -> {
      task.complete(withVariables(VAR_BOOKS_BOUGHT, true));
    });

    Scenario.run(testReaderBuyBooks).startByKey(PROCESS_KEY).execute();

    verify(testReaderBuyBooks).hasFinished(END_MSG_EVENT_BOUGHT);
  }

  @Test
  public void shouldExecuteNoFeedbacks() {
    when(testReaderBuyBooks.waitsAtUserTask(TASK_SUBMIT_FEEDBACKS)).thenReturn(task -> {
      task.complete(withVariables(VAR_FEEDBACK_RECEIVED, false));
    });

    Scenario.run(testReaderBuyBooks).startByKey(PROCESS_KEY).execute();

    verify(testReaderBuyBooks).hasFinished(END_EVENT_NO_FEEDBACKS);
  }

  @Test
  public void shouldExecuteNoBooksBought() {

    when(testReaderBuyBooks.waitsAtUserTask(TASK_BUY_BOOKS)).thenReturn(task -> {
      task.complete(withVariables(VAR_BOOKS_BOUGHT, false));
    });

    Scenario.run(testReaderBuyBooks).startByKey(PROCESS_KEY).execute();

    verify(testReaderBuyBooks).hasFinished(END_MSG_EVENT_NOT_BOUGHT);
  }

  @Test
  public void shouldRemindAndFinish() {


    LocalDateTime now = LocalDateTime.now();
    LocalDateTime threeDays = now.plus(3, ChronoUnit.DAYS);


    when(testReaderBuyBooks.waitsAtUserTask(TASK_BUY_BOOKS)).thenReturn(task -> {

      /*
       * when(testReaderBuyBooks.waitsAtTimerIntermediateEvent(TIM_WAIT_3DAYS)).thenReturn(timer ->
       * {
       * 
       * List<Job> jobList = jobQuery().processInstanceId(task.getExecutionId()).list();
       * System.out.println("********** jobList ***************" + jobList.toString());
       * 
       * assertThat(job().getDuedate())
       * .isCloseTo(threeDays.atZone(ZoneId.systemDefault()).toInstant(), 300000); execute(job());
       * 
       * });
       */

    });



    Scenario.run(testReaderBuyBooks).startByKey(PROCESS_KEY).execute();

    verify(testReaderBuyBooks).hasFinished(END_EVENT_REMINDER);
  }

}
