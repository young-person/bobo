package com.app.filemonitor.run;

import com.app.filemonitor.WatcherPathCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class WatchTask {
    private static Logger logger = LoggerFactory.getLogger(WatchTask.class);
    public static ConcurrentLinkedQueue<String> caches = new ConcurrentLinkedQueue<String>();
    private WatchService watchService;
    private WatchEvent.Kind<?>[] events = {StandardWatchEventKinds.OVERFLOW,StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY};
    private static ExecutorService executorService = null;
    private static ExecutorService monitorThread = Executors.newSingleThreadExecutor();
    private int poolSize = 0b1000;
    private static ReentrantLock lock = new ReentrantLock();

    /**
     * 监控指定的目录 默认不监控子目录
     * @param events
     * @param path
     */
    public WatchTask(WatchEvent.Kind<?>[] events,String path,final WatcherPathCallback callback){
        if(events.length == 0){
            throw new RuntimeException("必须至少指定一个监控的事件，如：StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE");
        }
        this.events = events;
    }

    public WatchTask(String path,final WatcherPathCallback callback){
        init(path, callback);
    }

    private void init(String path,final WatcherPathCallback callback){
        registerDirectoryTree(Paths.get(path));
        try {
            watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            logger.error("初始化文件监控服务失败",e);
        }
        lock.lock();
        if (null == executorService){
            executorService = Executors.newFixedThreadPool(poolSize);
        }
        lock.unlock();
        caches.add(path);
        monitorThread.execute(new Runnable() {
            @Override
            public void run() {
                watch(callback);
            }
        });
    }

    /**
     * 监控事件分发器
     * @param callback 事件回调
     */
    private synchronized void watch(final WatcherPathCallback callback){
        try {
            while (true) {
                WatchKey key = watchService.take();
                if(key == null)continue;
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    final WatchEvent.Kind<?> kind = watchEvent.kind();
                    if (kind == StandardWatchEventKinds.OVERFLOW) continue;
                    WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
                    //path是相对路径相对于监控目录
                    Path contextPath = watchEventPath.context();
                    final Path absolutePath = Paths.get(caches.poll()).resolve(contextPath);
                    switch (kind.name()) {
                        case "ENTRY_CREATE":
                            if (Files.isDirectory(absolutePath, LinkOption.NOFOLLOW_LINKS)) {
                                logger.info("新增目录：" + absolutePath);
                                registerDirectoryTree(absolutePath);
                            }else{
                                logger.info("新增文件：" + absolutePath);
                            }
                            break;
                        case "ENTRY_DELETE":
                            logger.info("删除：" + absolutePath);
                            break;
                        case "ENTRY_MODIFY":
                            logger.info("修改：" + absolutePath);
                            break;
                    }
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.callBack(kind,absolutePath.toAbsolutePath().toString());
                        }
                    });
                }
                boolean valid = key.reset();
                if (!valid) {
                    if (caches.isEmpty()){
                        break;
                    }
                }
            }
        } catch (InterruptedException ex) {
            logger.error("监控目录线程退出", ex);
        } finally{
            try {
                watchService.close();
                executorService.shutdown();
                logger.info("关闭监控目录服务");
            } catch (Exception e) {
                logger.error("关闭监控目录服务出错", e);
            }
        }
    }

    /**
     * 注册监听目录
     * @param path
     */
    private void registerDirectoryTree(Path path) {
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    WatchKey key = dir.register(watchService,events);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logger.error("监控目录失败：{}",path.toAbsolutePath(), e);
        }
    }

    private class TaskExec<T> implements Callable<T> {
        private T t;

        public TaskExec(T t){
            this.t = t;
        }

        @Override
        public T call() throws Exception {
            return null;
        }
    }

    private class TaskWatch<T> implements Runnable{
        private T t;
        public TaskWatch(T t){
            this.t = t;
        }
        @Override
        public void run() {

        }
    }

    private class TaskStart extends Thread{

    }

}
