# Job-Scheduler [![Continuous Integration](https://github.com/kquiet/job-scheduler/actions/workflows/continuous-integration.yml/badge.svg?branch=dev)](https://github.com/kquiet/job-scheduler/actions/workflows/continuous-integration.yml) [![Continuous Delievery - staging](https://github.com/kquiet/job-scheduler/actions/workflows/continuous-delivery-staging.yml/badge.svg)](https://github.com/kquiet/job-scheduler/actions/workflows/continuous-delivery-staging.yml)
Job-Scheduler is a job scheduler which incorporates [auto-browser][] to support
customized browser automation. Multiple jobs could be scheduled to form a
complete process to automate upon a specific web site.

## Getting Started to develop a job
Add below to [maven][]'s `pom.xml`:
```xml
<dependency>
  <groupId>org.kquiet</groupId>
  <artifactId>job-scheduler</artifactId>
  <version>X.X.X</version>
</dependency>
```

## What is a job
In Job-Scheduler, a job is a class which inherits the designated abstract class
`JobBase` and implements its own processing logic of job.
A sample job is as follows:
```java
import org.kquiet.jobscheduler.JobBase;

public class RestartBrowser extends JobBase {
  public RestartBrowser(String jobName) {
    super(jobName);
  }

  @Override
  public void run() {
    //The processing logic of job goes here.
    restartInternalBrowser();
  }
}
```
It's pretty easy because the only thing needed to do is to implement the
processing logic inside method `run()`. This job will be executed in a thread
managed by an internal thread pool executor.

## What is a job schedule
In Job-Scheduler, all configuration should be placed in the `job-scheduler`
section of a .yml file named `application.yml`. A job schedule could be
configured like this:
```YAML
job-scheduler:
  jobs:
  - name: TestJob1
    start: "2019-11-01T00:00:00"
    end: "2019-12-01T00:00:00"
    dailyStart: "03:00:00"
    dailyEnd: "15:00:00"
    interval: 10
    scheduleAfterExec: true
```
This configuration means job `TestJob1` will be executed repeatedly during
`03:00:00 ~ 15:00:00` everyday with a fix `delay` equal to `10` seconds in
the period from `2019-11-01T00:00:00` through `2019-12-01T00:00:00`.

## Description of `application.yml`
A complete configuration sample:
```YAML
job-scheduler:
  instanceName: JobSchedulerDefault
  gui:
    enable: false
    clearLogInterval: 86400
  browser:
    type: firefox
    maxTask: 1
    pageLoadStrategy: none
    headless: false
  jobParallelism: 1
  jobs:
  - name: RestartBrowser
    enable: true
    impl: org.kquiet.jobscheduler.impl.common.RestartBrowser
    start: "2019-11-01T00:00:00"
    end: "2019-12-01T00:00:00"
    dailyStart: "03:00:00"
    dailyEnd: "03:00:01"
    interval: 43200
    scheduleAfterExec: true
    parameters:
      param1: "param1_value"
```
|Name|Default|Description|
|---|---|---|
|`instanceName`||The instance name of job-scheduler|
|`gui.enable`|`false`|Set `true` to enable optional monitoring GUI|
|`gui.clearLogInterval`|`86400`|Clear log on GUI with specified rate(in seconds)|
|`browser.type`||Available values are `chrome` and `firefox`. Leave it to blank if internal browser is not required.|
|`browser.maxTask`|`1`|maximum number of tasks that can be run in the internal browser concurrently|
|`browser.pageLoadStrategy`|`none`|It controls the behavior of waiting for page loads in the internal browser|
|`browser.headless`|`true`|`true`: internal browser will display its GUI;`false`: internal browser won't display its GUI|
|`jobParallelism`|`1`|It controls how many jobs could be executed concurrently at most.|
|`jobs[*].name`||The name of job|
|`jobs[*].enable`||Indicate whether the job is enabled to be scheduled|
|`jobs[*].impl`||Implementation class name of job|
|`jobs[*].start`||The start of the absolute period in which the execution of job is allowed|
|`jobs[*].end`||The end of the absolute period in which the execution of job is allowed|
|`jobs[*].dailyStart`||The start of the daily period in which the execution of job is allowed|
|`jobs[*].dailyEnd`||The end of the daily period in which the execution of job is allowed|
|`jobs[*].interval`||The interval(in seconds) between two consecutive execution of job|
|`jobs[*].scheduleAfterExec`||`true`: means the `interval` is the delay between the termination of one execution and the commencement of the next ;`false`: means the `interval` is the rate between the commencement of one execution and the commencement of the next.|
|`jobs[*].parameters`||The parameter map of job|

***Note: All time-related parameters of a job are presented in local time zone.***

## Use of Docker Image
1. Get the image from docker hub: `docker pull kquiet/job-scheduler:latest`
2. Prepare all the jar files of your libraries and dependencies along with a configured
`application.yml`, then:
    - Map the library path to `/opt/kquiet/job-scheduler/ext` when you run it,
    e.g., `docker run -d -v /path/to/library:/opt/kquiet/job-scheduler/ext
    kquiet/job-scheduler:latest`
    - Or use `docker build` to put them into `/opt/kquiet/job-scheduler/ext`
    to create your own image to run it without volume mapping

## Q&A
1. How to configure a job with multiple combination of (dailyStart, dailyEnd, interval)?  
=> Currently it can only be achieved by configuring multiple job schedules with
the same job class in different job names like this:
  ```YAML
  jobs:
  - name: TestJob1
    impl: xxx.yyy.zzz.JobClass
    dailyStart: "03:00:00"
    dailyEnd: "05:00:00"
    interval: 10
  - name: TestJob2 
    impl: xxx.yyy.zzz.JobClass
    dailyStart: "15:00:00"
    dailyEnd: "17:00:00"
    interval: 10
  ```
    The caveat of this approach is that two instances of the same job will be created.
2. I can't see the optional GUI or browser on screen even with `gui.enable`, `browser.type`
, `browser.headless` configured correctly when using docker image to run.  
=> Before creating docker container, you may need to grant access control for X server by
executing `xhost +local:`, please see the manual of `xhost` for details. When running container,
please add the environment variable `DISPLAY` with proper value according to your environment and
the mapping of volume path `/tmp/.X11-unix` to run container, e.g.,
`docker run -d -e DISPLAY=:0 -v /tmp/.X11-unix:/tmp/.X11-unix kquiet/job-scheduler:latest`.
3. Error: `The path to the driver executable must be set by the webdriver.xxxx.driver system property`
is shown when I executed by java command directly.  
=> An argument: `-Dwebdriver.xxxx.driver=/path/to/driver/file` is required when executing `java` to
make driver accessible. This argument is not required when executing in provided docker image as the drivers
are located in path.


[maven]: https://maven.apache.org/ "maven official website"
[auto-browser]: https://github.com/kquiet/auto-browser "auto-browser in github"