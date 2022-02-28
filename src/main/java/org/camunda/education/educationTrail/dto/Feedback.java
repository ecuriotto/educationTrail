package org.camunda.education.educationTrail.dto;

public class Feedback {

  private String readerType;
  private Integer feedbacksGiven;

  public String getReaderType() {
    return readerType;
  }

  public void setReaderType(String readerType) {
    this.readerType = readerType;
  }

  public Integer getFeedbacksGiven() {
    return feedbacksGiven;
  }

  public void setFeedbacksGiven(Integer feedbacksGiven) {
    this.feedbacksGiven = feedbacksGiven;
  }


}
