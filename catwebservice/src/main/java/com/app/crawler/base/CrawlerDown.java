package com.app.crawler.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * 下载
 */
public interface CrawlerDown{
    /**
     * 开始执行
     */
     void start();

    /**
     * 工作执行
     */
    boolean stop();

    /**
     * 暂停
     */
    boolean suspend();

    /**
     * 是否正在运行
     *
     * @return
     */
    boolean isRuning();

    /**
     * 定时执行时间
     * @return
     */
    String executeTimeFormat();

    String SPACE = "    ";

    Logger LOGGER = LoggerFactory.getLogger(CrawlerDown.class);

    /**
     * 将数据写入到对应路径
     * @param str
     * @param path
     */
    static void writeToTxt(String str, String path) {

        FileWriter writer = null;
        PrintWriter printWriter = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
            printWriter = new PrintWriter(writer);
            printWriter.println(str);
            printWriter.flush();
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("数据写入到目录:[{}]失败:[{}]", path, str, e);
        }finally {
            if (Objects.nonNull(printWriter)){
                printWriter.close();
            }
            if (Objects.nonNull(writer)){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
