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

package org.kquiet.jobscheduler.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kquiet.jobscheduler.BeanConfiguration;
import org.kquiet.jobscheduler.JobBase;
import org.kquiet.jobscheduler.JobController;
import org.kquiet.jobscheduler.JobSchedulerConfig.JobConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Controller unit test.
 *
 * @author Kimberly
 */
@SpringBootTest(classes = {BeanConfiguration.class})
@EnableConfigurationProperties
class IntegrationTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationTest.class);
  @Autowired private JobController controller;

  @BeforeAll
  public static void setUpClass() {}

  @AfterAll
  public static void tearDownClass() {}

  @BeforeEach
  public void setUp() {}

  @AfterEach
  public void tearDown() {}

  @Test
  void customJobTest() {
    CountDownLatch latch = new CountDownLatch(2);
    List<String> parameterValueList = Collections.synchronizedList(new ArrayList<>());
    TestJobBase job1 = new TestJobBase("TestJob1", latch, parameterValueList);
    TestJobBase job2 = new TestJobBase("TestJob2", latch, parameterValueList);
    job1.setJobController(controller);
    job2.setJobController(controller);
    controller.start(Arrays.asList(job2, job1));

    boolean temp = false;
    try {
      temp = latch.await(2000, TimeUnit.SECONDS);
    } catch (Exception ex) {
      System.err.println(ex.toString());
    }
    controller.stop();
    boolean jobDone = temp;
    assertAll(
        () -> assertTrue(jobDone),
        () -> assertEquals(2, parameterValueList.size(), "Wrong parameter value size"),
        () ->
            assertEquals(
                "TestJob1,TestJob2",
                String.join(",", parameterValueList.stream().sorted().collect(Collectors.toList())),
                "Wrong parameter value sequence"));
  }

  static class TestJobBase extends JobBase {
    private CountDownLatch latch = null;
    private List<String> parameterValueList = null;

    public TestJobBase(String name, CountDownLatch latch, List<String> parameterValueList) {
      super(newJobConfig(name));
      this.latch = latch;
      this.parameterValueList = parameterValueList;
    }

    @Override
    public void run() {
      LOGGER.info("[{}] starts", this.getJobName());
      parameterValueList.add(getParameter("testParameter"));
      LOGGER.info(
          "[{}] done with testParameter={}", this.getJobName(), this.getParameter("testParameter"));
      if (latch != null) {
        latch.countDown();
      }
    }

    public static JobConfig newJobConfig(String name) {
      JobConfig config = new JobConfig();
      config.setName(name);
      config.getParameters().put("testParameter", name);
      return config;
    }
  }
}
