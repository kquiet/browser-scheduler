/*
 * Copyright 2019 P. Kimberly Chang
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.kquiet.browserscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Application launcher.
 *
 * @author Kimberly
 */
@SpringBootApplication(scanBasePackages = {"org.kquiet.browserscheduler",
    "${browser-scheduler.springComponentScanBasePackages:}"})
public class Launcher implements CommandLineRunner {

  @Autowired
  private BrowserSchedulerConfig browserSchedulerConfig;
  @Autowired
  private ApplicationContext applicationContext;

  /**
   * Start the application with/without gui according to config.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(Launcher.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    if (browserSchedulerConfig.isGuiEnable()) {
      System.setProperty("java.awt.headless", "false");
      MonitorFrame launchedObj = applicationContext.getBean(MonitorFrame.class);
      launchedObj.setVisible(true);
    } else {
      System.setProperty("java.awt.headless", "true");
      JobController controller = applicationContext.getBean(JobController.class);
      controller.start();
    }
  }
}
