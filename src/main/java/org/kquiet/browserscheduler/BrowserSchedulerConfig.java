/*
 * Copyright 2021 P. Kimberly Chang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kquiet.browserscheduler;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.openqa.selenium.PageLoadStrategy;
import org.springframework.format.annotation.DateTimeFormat;

/** System config. */
public class BrowserSchedulerConfig {

  private String instanceName = "";

  public String getInstanceName() {
    return instanceName;
  }

  public void setInstanceName(String instanceName) {
    this.instanceName = instanceName;
  }

  public boolean isGuiEnable() {
    return gui.isEnable();
  }

  public int getGuiClearLogInterval() {
    return gui.getClearLogInterval();
  }

  private Gui gui = new Gui();

  public Gui getGui() {
    return gui;
  }

  public void setGui(Gui gui) {
    this.gui = gui;
  }

  static class Gui {
    private boolean enable = false;

    public boolean isEnable() {
      return enable;
    }

    public void setEnable(boolean enable) {
      this.enable = enable;
    }

    private int clearLogInterval = 86400;

    public int getClearLogInterval() {
      return clearLogInterval;
    }

    public void setClearLogInterval(int clearLogInterval) {
      this.clearLogInterval = clearLogInterval;
    }
  }

  public String getBrowserType() {
    return browser.getType();
  }

  public int getBrowserMaxTask() {
    return browser.getMaxTask();
  }

  public PageLoadStrategy getBrowserPageLoadStrategy() {
    return browser.getPageLoadStrategy();
  }

  public boolean isBrowserHeadless() {
    return browser.isHeadless();
  }

  private Browser browser = new Browser();

  public Browser getBrowser() {
    return browser;
  }

  public void setBrowser(Browser browser) {
    this.browser = browser;
  }

  static class Browser {
    private String type = "";

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    private int maxTask = 1;

    public int getMaxTask() {
      return maxTask;
    }

    public void setMaxTask(int maxTask) {
      this.maxTask = maxTask;
    }

    private PageLoadStrategy pageLoadStrategy = PageLoadStrategy.NONE;

    public PageLoadStrategy getPageLoadStrategy() {
      return pageLoadStrategy;
    }

    public void setPageLoadStrategy(PageLoadStrategy pageLoadStrategy) {
      this.pageLoadStrategy = pageLoadStrategy;
    }

    private boolean headless = true;

    public boolean isHeadless() {
      return headless;
    }

    public void setHeadless(boolean headless) {
      this.headless = headless;
    }
  }

  private int jobParallelism = 1;

  public int getJobParallelism() {
    return jobParallelism;
  }

  public void setJobParallelism(int jobParallelism) {
    this.jobParallelism = jobParallelism;
  }

  private List<JobConfig> jobs = new ArrayList<>();

  public List<JobConfig> getJobs() {
    return jobs;
  }

  public void setJobs(List<JobConfig> jobs) {
    this.jobs = jobs;
  }

  /**
   * Get enable jobs.
   *
   * @return a map of enable jobs.
   */
  public List<JobConfig> getEnableJobs() {
    return jobs.stream().filter(JobConfig::isEnable).collect(Collectors.toList());
  }

  /** Job config. */
  public static class JobConfig {
    private String name = "";

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    private boolean enable = false;

    public boolean isEnable() {
      return enable;
    }

    public void setEnable(boolean enable) {
      this.enable = enable;
    }

    private String impl = "";

    public String getImpl() {
      return impl;
    }

    public void setImpl(String impl) {
      this.impl = impl;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime start = LocalDateTime.parse("1900-01-01T00:00:00");

    public LocalDateTime getStart() {
      return start;
    }

    public void setStart(LocalDateTime start) {
      this.start = start;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime end = LocalDateTime.MAX;

    public LocalDateTime getEnd() {
      return end;
    }

    public void setEnd(LocalDateTime end) {
      this.end = end;
    }

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime dailyStart = LocalTime.MIN;

    public LocalTime getDailyStart() {
      return dailyStart;
    }

    public void setDailyStart(LocalTime dailyStart) {
      this.dailyStart = dailyStart;
    }

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime dailyEnd = LocalTime.MAX;

    public LocalTime getDailyEnd() {
      return dailyEnd;
    }

    public void setDailyEnd(LocalTime dailyEnd) {
      this.dailyEnd = dailyEnd;
    }

    private int interval = 43200;

    public int getInterval() {
      return interval;
    }

    public void setInterval(int interval) {
      this.interval = interval;
    }

    private boolean scheduleAfterExec = true;

    public boolean isScheduleAfterExec() {
      return scheduleAfterExec;
    }

    public void setScheduleAfterExec(boolean scheduleAfterExec) {
      this.scheduleAfterExec = scheduleAfterExec;
    }

    private Map<String, String> parameters = new LinkedHashMap<>();

    public Map<String, String> getParameters() {
      return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
      this.parameters = parameters;
    }
  }
}
