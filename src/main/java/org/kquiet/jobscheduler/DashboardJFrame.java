/*
 * Copyright 2019 P. Kimberly Chang
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

package org.kquiet.jobscheduler;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.aeonbits.owner.ConfigCache;

import org.kquiet.concurrent.PausableScheduledThreadPoolExecutor;
import org.kquiet.jobscheduler.util.JTextAreaLogAppender;
import org.kquiet.jobscheduler.util.TimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Optional gui to monitor jobs.
 * 
 * @author Kimberly
 */
public class DashboardJFrame extends javax.swing.JFrame {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = LoggerFactory.getLogger(DashboardJFrame.class);

  private final LocalDateTime initTime = LocalDateTime.now();
  private SystemConfig configInfo = null;
  private transient JobCtrl controller = null;
  private transient PausableScheduledThreadPoolExecutor timerExecutor = null;

  public DashboardJFrame() {
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    jtabbedPaneMain = new javax.swing.JTabbedPane();
    jscrollPaneInfoLog = new javax.swing.JScrollPane();
    jtextAreaInfoLog = new javax.swing.JTextArea();
    jscrollPaneErrorLog = new javax.swing.JScrollPane();
    jtextAreaErrorLog = new javax.swing.JTextArea();
    jscrollPaneDebugLog = new javax.swing.JScrollPane();
    jtextAreaDebugLog = new javax.swing.JTextArea();
    jscrollPaneMisc = new javax.swing.JScrollPane();
    jpanelMisc = new javax.swing.JPanel();
    jscrollPaneMiscText = new javax.swing.JScrollPane();
    jtextAreaMisc = new javax.swing.JTextArea();
    jbuttonCancel = new javax.swing.JButton();
    jmenuBarMain = new javax.swing.JMenuBar();
    jmenuPause = new javax.swing.JMenu();
    jmenuClear = new javax.swing.JMenu();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
      
      public void windowOpened(java.awt.event.WindowEvent evt) {
        formWindowOpened(evt);
      }
    });

    jtextAreaInfoLog.setColumns(20);
    jtextAreaInfoLog.setRows(5);
    jscrollPaneInfoLog.setViewportView(jtextAreaInfoLog);

    jtabbedPaneMain.addTab("InfoLog", jscrollPaneInfoLog);

    jtextAreaErrorLog.setColumns(20);
    jtextAreaErrorLog.setRows(5);
    jscrollPaneErrorLog.setViewportView(jtextAreaErrorLog);

    jtabbedPaneMain.addTab("ErrorLog", jscrollPaneErrorLog);

    jtextAreaDebugLog.setColumns(20);
    jtextAreaDebugLog.setRows(5);
    jscrollPaneDebugLog.setViewportView(jtextAreaDebugLog);

    jtabbedPaneMain.addTab("DebugLog", jscrollPaneDebugLog);

    jpanelMisc.setLayout(new java.awt.GridBagLayout());
    jbuttonPositive = new javax.swing.JButton();

    jbuttonPositive.setText("Positive");
    jbuttonPositive.setEnabled(false);
    jbuttonPositive.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jbuttonSubmitActionPerformed(evt);
      }
    });
    java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.insets = new Insets(0, 0, 5, 0);
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    jpanelMisc.add(jbuttonPositive, gridBagConstraints);

    jtextAreaMisc.setColumns(20);
    jtextAreaMisc.setRows(5);
    jscrollPaneMiscText.setViewportView(jtextAreaMisc);

    gridBagConstraints2 = new java.awt.GridBagConstraints();
    gridBagConstraints2.insets = new Insets(0, 0, 5, 0);
    gridBagConstraints2.gridx = 0;
    gridBagConstraints2.gridy = 1;
    gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints2.weightx = 1.0;
    gridBagConstraints2.weighty = 1.0;
    jpanelMisc.add(jscrollPaneMiscText, gridBagConstraints2);

    jbuttonCancel.setText("Negative");
    jbuttonCancel.setEnabled(false);
    jbuttonCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jbuttonCancelActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    jpanelMisc.add(jbuttonCancel, gridBagConstraints);

    jscrollPaneMisc.setViewportView(jpanelMisc);

    jtabbedPaneMain.addTab("Misc.", jscrollPaneMisc);

    jmenuPause.setText("Pause");
    jmenuPause.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jmenuPauseMouseClicked(evt);
      }
    });
    jmenuBarMain.add(jmenuPause);

    jmenuClear.setText("Clear");
    jmenuClear.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jmenuClearMouseClicked(evt);
      }
    });
    jmenuBarMain.add(jmenuClear);

    setJMenuBar(jmenuBarMain);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jtabbedPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jtabbedPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
    );

    pack();
  } // </editor-fold>//GEN-END:initComponents

  private void formWindowOpened(java.awt.event.WindowEvent evt) { //GEN-FIRST:event_formWindowOpened
    SwingUtilities.invokeLater(() -> {
      this.customInit();
    });
  } //GEN-LAST:event_formWindowOpened

  private void formWindowClosing(java.awt.event.WindowEvent evt) {
    //GEN-FIRST:event_formWindowClosing
    this.setVisible(false);
    this.releaseResources();
  } //GEN-LAST:event_formWindowClosing

  private void jbuttonSubmitActionPerformed(java.awt.event.ActionEvent evt) {
    //GEN-FIRST:event_jButtonSubmitActionPerformed
    jbuttonPositive.setEnabled(false);
    jbuttonCancel.setEnabled(false);
    controller.signalInteractionResult(true);
  } //GEN-LAST:event_jButtonSubmitActionPerformed

  private void jbuttonCancelActionPerformed(java.awt.event.ActionEvent evt) {
    //GEN-FIRST:event_jButtonCancelActionPerformed
    jbuttonCancel.setEnabled(false);
    jbuttonPositive.setEnabled(false);
    controller.signalInteractionResult(false);
  } //GEN-LAST:event_jButtonCancelActionPerformed

  private void jmenuClearMouseClicked(java.awt.event.MouseEvent evt) {
    //GEN-FIRST:event_jMenuClearMouseClicked
    try {
      SwingUtilities.invokeLater(() -> {
        jtextAreaInfoLog.setText(null);
        jtextAreaErrorLog.setText(null);
        jtextAreaDebugLog.setText(null);
        jtextAreaMisc.setText(null);
      });
      LOGGER.info("[Event] JTextArea log cleared");
    } catch (Exception ex) {
      LOGGER.error("[Event] clear JTextArea log fail", ex);
    }
  } //GEN-LAST:event_jMenuClearMouseClicked

  private void jmenuPauseMouseClicked(java.awt.event.MouseEvent evt) {
    //GEN-FIRST:event_jMenuPauseMouseClicked
    if (controller.isPaused()) {
      changePageStatus(PageStatus.Resumed);
    } else {
      changePageStatus(PageStatus.Paused);
    }
  } //GEN-LAST:event_jMenuPauseMouseClicked

  private enum PageStatus {
    Running, Paused, Resumed
  }
  
  private void changePageStatus(PageStatus newStatus) {
    switch (newStatus) {
      default:
        break;
      case Resumed:
        SwingUtilities.invokeLater(() -> {
          if (controller.isPaused()) {
            controller.resume();
            jmenuPause.setText("Pause");
            LOGGER.info("[Event] system resumed");
          }
        });
        break;
      case Paused:
        SwingUtilities.invokeLater(() -> {
          if (!controller.isPaused()) {
            controller.pause();
            jmenuPause.setText("Resume");
            LOGGER.info("[Event] system paused");
          }
        });
        break;
    }
  }

  /**
   * Release all resources. This method should be called before closing this window frame.
   */
  public void releaseResources() {
    try {
      LOGGER.info("[Event] Releasing all resources...");
      controller.stop();
      timerExecutor.shutdown();
      LOGGER.info("[Event] All resources released");
    } catch (Exception ex) {
      ex.printStackTrace(System.out);
    } finally {
      configInfo = null;
      controller = null;
    }
  }

  private void refreshTitle(String extra) {
    try {
      String title = String.format("JobScheduler(%s-%s)_%s", configInfo.instanceName(),
          this.getClass().getPackage().getImplementationVersion(), extra);
      String since = ", since " + TimeUtility.toStr(initTime, "yyyy-MM-dd HH:mm:ss");
      SwingUtilities.invokeLater(() -> {
        this.setTitle(title + since);
      });
    } catch (Exception ex) {
      LOGGER.error("[View] set frame title error", ex);
    }
  }
  
  private static void setJTextAreaLogAppender(String appenderName, JTextArea textArea) {
    LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();
    context.getLoggerList().stream()
        .map((logger) -> (AsyncAppender)logger.getAppender(appenderName))
        .filter((asyncAppender) -> (asyncAppender != null))
        .forEachOrdered((asyncAppender) -> {
          asyncAppender.iteratorForAppenders()
            .forEachRemaining(f -> ((JTextAreaLogAppender)f).setTextArea(textArea));
        });
  }

  private void customInit() {
    //1. init
    configInfo = ConfigCache.getOrCreate(SystemConfig.class);
    setJTextAreaLogAppender("asyncJTextAreaError", this.jtextAreaErrorLog);
    setJTextAreaLogAppender("asyncJTextAreaInfo", this.jtextAreaInfoLog);
    setJTextAreaLogAppender("asyncJTextAreaDebug", this.jtextAreaDebugLog);

    //2. refresh title
    refreshTitle("");

    //3. maximize JFrame
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);

    //4. start timer worker
    controller = new JobCtrl();
    controller.setExecutingJobDescriptionConsumer((s) -> {
      refreshTitle(s);
    });
    controller.setPreInteractionFunction(() -> {
      SwingUtilities.invokeLater(() -> {
        //enable only when in interactive mode
        if (configInfo.interactiveFlag()) {
          jbuttonPositive.setEnabled(true);
          jbuttonCancel.setEnabled(true);
          jtabbedPaneMain.setSelectedIndex(jtabbedPaneMain.getTabCount() - 1);
        }
      });
    });
    controller.setPauseDelegate(() -> {
      SwingUtilities.invokeLater(() -> {
        jmenuPause.setText("Resume");
      });
    });
    controller.setResumeDelegate(() -> {
      SwingUtilities.invokeLater(() -> {
        jmenuPause.setText("Pause");
      });
    });
    controller.start();

    //5. init inner timer
    timerExecutor = new PausableScheduledThreadPoolExecutor("FrameTimerExecutor", 1);
    timerExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
    timerExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
    int logClearInterval = configInfo.logClearInterval();
    timerExecutor.scheduleWithFixedDelay(() -> {
      SwingUtilities.invokeLater(() -> {
        jtextAreaInfoLog.setText(null);
        jtextAreaErrorLog.setText(null);
        jtextAreaDebugLog.setText(null);
        jtextAreaMisc.setText(null);
      });
      LOGGER.info("[View] clearn JTextAreaLog every {} seconds", logClearInterval);
    }, logClearInterval, logClearInterval, TimeUnit.SECONDS);
  }

  /**
   * Get brief information about this window frame.
   * @return window information
   */
  public String ping() {
    String fullLog = jtextAreaInfoLog.getText();
    if (fullLog.endsWith(System.lineSeparator())) {
      fullLog = fullLog.substring(0, fullLog.length() - System.lineSeparator().length());
    }
    int index = fullLog.lastIndexOf(System.lineSeparator());
    if (index >= 0) {
      return fullLog.substring(index);
    } else {
      return fullLog;
    }
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jbuttonCancel;
  private javax.swing.JButton jbuttonPositive;
  private javax.swing.JMenuBar jmenuBarMain;
  private javax.swing.JMenu jmenuClear;
  private javax.swing.JMenu jmenuPause;
  private javax.swing.JPanel jpanelMisc;
  private javax.swing.JScrollPane jscrollPaneDebugLog;
  private javax.swing.JScrollPane jscrollPaneErrorLog;
  private javax.swing.JScrollPane jscrollPaneInfoLog;
  private javax.swing.JScrollPane jscrollPaneMisc;
  private javax.swing.JScrollPane jscrollPaneMiscText;
  private javax.swing.JTabbedPane jtabbedPaneMain;
  private javax.swing.JTextArea jtextAreaDebugLog;
  private javax.swing.JTextArea jtextAreaErrorLog;
  private javax.swing.JTextArea jtextAreaInfoLog;
  private javax.swing.JTextArea jtextAreaMisc;
  private GridBagConstraints gridBagConstraints2;
  // End of variables declaration//GEN-END:variables
}
