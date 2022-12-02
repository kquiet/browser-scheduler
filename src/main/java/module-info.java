module org.kquiet.browserscheduler {
  exports org.kquiet.browserscheduler;
  exports org.kquiet.browserscheduler.impl.common;
  exports org.kquiet.browserscheduler.util;

  requires transitive org.kquiet.autobrowser;
  requires transitive ch.qos.logback.classic;
  requires transitive ch.qos.logback.core;
  requires transitive java.desktop;
  requires org.slf4j;
  requires transitive org.seleniumhq.selenium.api;
  requires spring.beans;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.core;

  opens org.kquiet.browserscheduler to spring.core;
}
