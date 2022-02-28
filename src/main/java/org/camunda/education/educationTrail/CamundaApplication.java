package org.camunda.education.educationTrail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;



@SpringBootApplication
@EnableProcessApplication("educationTrail")
public class CamundaApplication {

  public static void main(String... args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    SpringApplication.run(CamundaApplication.class, args);
  }

}
