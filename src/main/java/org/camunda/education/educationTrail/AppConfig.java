package org.camunda.education.educationTrail;

import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.camunda.cawemo.plugin.CawemoEnginePlugin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;

@Configuration
public class AppConfig {

  @Bean
  @Order(Ordering.DEFAULT_ORDER + 1)
  public static ProcessEnginePlugin cawemoEnginePlugin() {
    CawemoEnginePlugin plugin = new CawemoEnginePlugin();
    plugin.setCawemoUrl("https://cawemo.com");
    plugin.setUserId("Enrico Cawemo api key");
    plugin.setApiKey("gc8gDnItqq8jyNkrkdVlqI2CBCaaMAKg5dKNjG5XNFeFSUWhBo9P6G1Z80i4DWMN3rww4RqC");
    plugin.setProjectName("TestIntegration");
    plugin.setAuthMode("BASIC"); // or "QUERY_PARAM"
    plugin.setCustomBasicAuth(false);

    return plugin;
  }
}
