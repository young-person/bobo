package com.bobo.computer;

import com.bobo.utils.CFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetstatMonitor implements Runnable {
  private static final int INTERVAL_TIME = 1000 * 60;
  private static final Logger logger = LoggerFactory.getLogger(NetstatMonitor.class);
  private  int LISTENING_PORT = 80;
  private static final AtomicBoolean RUNNING = new AtomicBoolean(true);

  private final String localIp;

  public NetstatMonitor(String localIp,int port) {
    this.localIp = localIp;
    this.LISTENING_PORT = port;
  }

  public void start() {
    Runtime runtime = Runtime.getRuntime();
    String[] cmds = generateCmds(runtime);
    while (RUNNING.get()) {
      try {
        Thread.sleep(INTERVAL_TIME);
      } catch (InterruptedException e) {
        logger.error("thread interrupted exception", e);
      }
      int[] datas = getDatas(runtime, cmds);
      //TODO 进行数据保存
    }
  }



  private int[] getDatas(Runtime runtime, String[] cmds) {
    int[] datas = new int[cmds.length];
    Process process = null;
    BufferedReader bufferedReader = null;
    try {
      for (int i = 0; i < cmds.length; i++) {
        process = runtime.exec(cmds[i]);
        bufferedReader = new BufferedReader(
            new InputStreamReader(new BufferedInputStream(process.getInputStream())), 1024);
        String lineStr;
        while ((lineStr = bufferedReader.readLine()) != null) {
          datas[i] = Integer.parseInt(lineStr);
        }
        if (process.waitFor() != 0 && process.exitValue() == 1) {
          logger.error("Failed to execute the command: {}", cmds[i]);
        }
      }
    } catch (IOException | InterruptedException e) {
      logger.error(String.format("Failed to execute commands: %s", Arrays.toString(cmds)), e);
    } finally {
      try {
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (IOException e) {
        logger.error(String.format("close BufferedReader error: %s", Arrays.toString(cmds)), e);
      }
      if (process != null) {
        process.destroy();
      }
    }
    return datas;
  }


  private String[] generateCmds(Runtime runtime) {
    String totalCmd = "netstat -ant |grep ':" + LISTENING_PORT + "' |wc -l";
    String establishedCmd ="netstat -ant |grep ':" + LISTENING_PORT + "' |grep 'ESTABLISHED' |wc -l";
    String timeWaitCmd ="netstat -ant |grep ':" + LISTENING_PORT + "' |grep 'TIME_WAIT' |wc -l";
    String finWait2Cmd ="netstat -ant |grep ':" + LISTENING_PORT + "' | grep 'FIN_WAIT2' |wc -l";
    logger.info("netstat For All STATE: {}", totalCmd);
    logger.info("netstat For ESTABLISHED STATE：{}", establishedCmd);
    logger.info("netstat For TIME_WAIT STATE：{}", timeWaitCmd);
    logger.info("netstat FOR FIN_WAIT2 STATE：{}", finWait2Cmd);
    CFileUtils.writeShellFile("established.sh", establishedCmd);
    CFileUtils.writeShellFile("fin_wait2.sh", finWait2Cmd);
    CFileUtils.writeShellFile("time_wait.sh", timeWaitCmd);
    CFileUtils.writeShellFile("total.sh", totalCmd);
    addPermissions(runtime, "established.sh");
    addPermissions(runtime, "fin_wait2.sh");
    addPermissions(runtime, "time_wait.sh");
    addPermissions(runtime, "total.sh");
    return new String[]{"sh total.sh", "sh established.sh", "sh time_wait.sh", "sh fin_wait2.sh"};
  }

  private void addPermissions(Runtime runtime, String file) {
    String cmd = "chmod +x " + file;
    BufferedReader bufferedReader = null;
    Process process = null;
    try {
      process = runtime.exec(cmd);
      bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(process.getInputStream())), 1024);
      String lineStr;
      while ((lineStr = bufferedReader.readLine()) != null) {
        logger.info(lineStr);
      }
      if (process.waitFor() != 0 && process.exitValue() == 1) {
        logger.error("Failed to perform the command: " + cmd);
      }
    } catch (IOException | InterruptedException e) {
      logger.error(String.format("read file error, file = %s", file), e);
    } finally {
      try {
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (IOException e) {
        logger.error(String.format("close BufferedReader file: %s", file), e);
      }
      if (process != null) {
        process.destroy();
      }
    }
  }

  @Override
  public void run() {
    start();
  }
}
