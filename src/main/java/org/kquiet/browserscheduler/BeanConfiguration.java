/*
 * Copyright 2021 P. Kimberly Chang
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

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/** Bean configuration. */
@Configuration
public class BeanConfiguration {
  @Bean
  @ConfigurationProperties(prefix = "browser-scheduler")
  public BrowserSchedulerConfig getBrowserSchedulerConfig() {
    return new BrowserSchedulerConfig();
  }

  @Bean(destroyMethod = "stop")
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public JobController getController(BrowserSchedulerConfig config) {
    return new JobController(config);
  }

  @Bean(destroyMethod = "stop")
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public MonitorFrame getMonitorFrame(BrowserSchedulerConfig config) {
    return new MonitorFrame();
  }
}
