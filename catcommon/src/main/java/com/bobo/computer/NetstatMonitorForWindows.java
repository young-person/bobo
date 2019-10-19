package com.bobo.computer;

import org.hyperic.sigar.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bobo.base.BaseClass;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetstatMonitorForWindows extends BaseClass implements Runnable {
  private static final int INTERVAL_TIME = 1000 * 60;

  private static final AtomicBoolean RUNNING = new AtomicBoolean(true);

  private static final Sigar SIGAR = new Sigar();

  private final String localIp;
  private CpuPerc cpuPerc;
  private Mem mem;
  private Swap swap;
  private CpuPerc[] cpuPercList;
  public NetstatMonitorForWindows(String localIp) {
    this.localIp = localIp;
  }

  public void start() {
    while (RUNNING.get()) {
      try {
        NetStat netStat = SIGAR.getNetStat();
        cpuPerc = SIGAR.getCpuPerc();
        mem = SIGAR.getMem();
        swap = SIGAR.getSwap();
        cpuPercList = SIGAR.getCpuPercList();
        computePersistence(netStat);
        getCpuInfos();
        getMemoryInfos();
        getOsInfos();
        getFileInfos();
        getNetInfos();
        computePersistence();
        getAllTimes();
      } catch (Exception e) {
        LOGGER.error("monitor netstat error.", e);
      }
      try {
        Thread.sleep(INTERVAL_TIME);
      } catch (InterruptedException e) {
        LOGGER.error("thread error.", e);
      }
    }
  }

  private void computePersistence(NetStat netStat) {
    int established = netStat.getTcpEstablished();
    int timeWait = netStat.getTcpTimeWait();
    int finWait2 = netStat.getTcpFinWait2();
    int total = netStat.getTcpCloseWait() + netStat.getAllInboundTotal();
    //TODO 进行数据保存
    //系统环境变量信息map
    Map<String, String> envInfoMap = System.getenv();
    LOGGER.info("获取用户名:{}",envInfoMap.get("USERNAME"));
    LOGGER.info("获取计算机名:{}",envInfoMap.get("COMPUTERNAME"));
    LOGGER.info("获取计算机域名:{}",envInfoMap.get("USERDOMAIN"));

    // java对ip封装的对象
    try {
      InetAddress addr = InetAddress.getLocalHost();
      LOGGER.info("获取Ip:{}",addr.getHostAddress());
      LOGGER.info("获取主机名称:{}",addr.getHostName());

    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    Runtime r = Runtime.getRuntime();
    LOGGER.info("JVM总内存:{}",String.valueOf(r.totalMemory()));
    LOGGER.info("JVM剩余内存:{}",String.valueOf(r.freeMemory()));
    LOGGER.info("jvm处理器个数:{}",String.valueOf(r.availableProcessors()));

    // 系统配置属性
    Properties sysProps = System.getProperties();
    LOGGER.info("Java的运行环境版本:{}",sysProps.getProperty("java.version"));
    LOGGER.info("Java的运行环境供应商:{}",sysProps.getProperty("java.vendor"));
    LOGGER.info("Java供应商的URL:{}",sysProps.getProperty("java.vendor.url"));
    LOGGER.info("Java的安装路径:{}",sysProps.getProperty("java.home"));
    LOGGER.info("Java的虚拟机规范版本:{}",sysProps.getProperty("java.vm.specification.version"));
    LOGGER.info("Java的虚拟机规范供应商:{}",sysProps.getProperty("java.vm.specification.vendor"));
    LOGGER.info("Java的虚拟机规范名称:{}",sysProps.getProperty("java.vm.specification.name"));
    LOGGER.info("Java的虚拟机实现版本:{}",sysProps.getProperty("java.vm.version"));
    LOGGER.info("Java的虚拟机实现供应商:{}",sysProps.getProperty("java.vm.vendor"));
    LOGGER.info("Java的虚拟机实现名称:{}",sysProps.getProperty("java.vm.name"));
    LOGGER.info("Java运行时环境规范版本:{}",sysProps.getProperty("java.specification.version"));
    LOGGER.info("Java运行时环境规范供应商:{}",sysProps.getProperty("java.specification.vendor"));
    LOGGER.info("Java的虚拟机规范名称:{}",sysProps.getProperty("java.specification.name"));
    LOGGER.info("Java的类格式版本号:{}",sysProps.getProperty("java.class.version"));
    LOGGER.info("Java的类路径:{}",sysProps.getProperty("java.class.path"));
    LOGGER.info("加载库时搜索的路径列表:{}",sysProps.getProperty("java.library.path"));
    LOGGER.info("默认的临时文件路径:{}",sysProps.getProperty("java.io.tmpdir"));
    LOGGER.info("一个或多个扩展目录的路径:{}",sysProps.getProperty("java.ext.dirs"));
    LOGGER.info("操作系统的名称:{}",sysProps.getProperty("os.name"));
    LOGGER.info("操作系统的构架:{}",sysProps.getProperty("os.arch"));
    LOGGER.info("操作系统的版本:{}",sysProps.getProperty("os.version"));
    LOGGER.info("文件分隔符:{}",sysProps.getProperty("file.separator"));
    LOGGER.info("路径分隔符:{}",sysProps.getProperty("path.separator"));
    LOGGER.info("行分隔符:{}",sysProps.getProperty("line.separator"));
    LOGGER.info("用户的账户名称:{}",sysProps.getProperty("user.name"));
    LOGGER.info("用户的主目录:{}",sysProps.getProperty("user.home"));
    LOGGER.info("用户的当前工作目录:{}",sysProps.getProperty("user.dir"));
  }
  /**
   * 2.获取cpu信息
   */
  public void getCpuInfos(){
    try {
      CpuInfo[] cpuInfos = SIGAR.getCpuInfoList();
      for (int i = 0; i < cpuInfos.length; i++) {
        CpuInfo cpuInfo = cpuInfos[i];
        LOGGER.info("第{}个CPU信息",i);
        LOGGER.info("第{}个CPU的总量MHz:{}",new Object[]{i,cpuInfo.getMhz()});
        LOGGER.info("第{}获得CPU的供应商:{}",new Object[]{i,cpuInfo.getVendor()});
        LOGGER.info("第{}获得CPU的类别:{}",new Object[]{i,cpuInfo.getModel()});
        LOGGER.info("第{}缓冲存储器数量:{}",new Object[]{i,cpuInfo.getCacheSize()});
      }
      CpuPerc[] cpuPercs = SIGAR.getCpuPercList();
      for (int i = 0; i < cpuPercs.length; i++) {
        CpuPerc cpuPerc = cpuPercs[i];
        LOGGER.info("第{}个CPU片段",i);
        LOGGER.info("第{}CPU用户使用率:{}" + i,new Object[]{i,cpuPerc.getUser()});
        LOGGER.info("第{}CPU系统使用率:{}" + i,new Object[]{i,cpuPerc.getSys()});
        LOGGER.info("第{}CPU当前等待率:{}" + i,new Object[]{i,cpuPerc.getWait()});
        LOGGER.info("第{}CPU当前错误率:{}" + i,new Object[]{i,cpuPerc.getNice()});
        LOGGER.info("第{}CPU当前空闲率:{}" + i,new Object[]{i,cpuPerc.getIdle()});
        LOGGER.info("第{}CPU总的使用率:{}" + i,new Object[]{i,cpuPerc.getCombined()});
      }
    } catch (SigarException e) {
      e.printStackTrace();
    }
  }


    /**
     * 3.获取内存信息
     */
    public void getMemoryInfos() throws SigarException {
      Mem mem = SIGAR.getMem();
      LOGGER.info("内存总量:{} K av",mem.getTotal() / 1024L);
      LOGGER.info("当前内存使用量:{} K used",mem.getUsed() / 1024L);
      LOGGER.info("当前内存剩余量:{} K free",mem.getFree() / 1024L);

      Swap swap = SIGAR.getSwap();
      LOGGER.info("交换区总量:{} K av",swap.getTotal() / 1024L);
      LOGGER.info("当前交换区使用量:{} K used",swap.getUsed() / 1024L);
      LOGGER.info("当前交换区剩余量:{} K free",swap.getFree() / 1024L);
    }

    /**
     * 4.获取操作系统信息
     */
    public void getOsInfos() {
      OperatingSystem os = OperatingSystem.getInstance();
      LOGGER.info("操作系统:{}",os.getArch());
      LOGGER.info("操作系统CpuEndian():{}",os.getCpuEndian());
      LOGGER.info("操作系统DataModel():{}",os.getDataModel());
      LOGGER.info("操作系统的描述:{}",os.getDescription());
      LOGGER.info("操作系统的供应商:{}",os.getVendor());
      LOGGER.info("操作系统的供应商编号:{}",os.getVendorCodeName());
      LOGGER.info("操作系统的供应商名称:{}",os.getVendorName());
      LOGGER.info("操作系统供应商类型:{}",os.getVendorVersion());
      LOGGER.info("操作系统的版本号:{}",os.getVersion());
    }

    /**
     * 5.获取文件信息
     */
    public void getFileInfos() throws SigarException {
      FileSystem fslist[] = SIGAR.getFileSystemList();
      for (int i = 0; i < fslist.length; i++) {
        FileSystem fs = fslist[i];
        LOGGER.info("分区的盘符号:{}",i);
        LOGGER.info("第{}个盘符名称:{}",new Object[]{i,fs.getDevName()});
        LOGGER.info("第{}个盘符路径:{}",new Object[]{i,fs.getDirName()});
        LOGGER.info("第{}个盘符标志:{}",new Object[]{i,fs.getFlags()});
        LOGGER.info("第{}个盘符类型(FAT32,NTFS):{}",new Object[]{i,fs.getSysTypeName()});
        LOGGER.info("第{}个盘符类型名:{}",new Object[]{i,fs.getTypeName()});
        LOGGER.info("第{}个盘符文件系统类型:{}",new Object[]{i,fs.getType()});

        FileSystemUsage usage;
        try {
          usage = SIGAR.getFileSystemUsage(fs.getDirName());
        } catch (SigarException e) {//当fileSystem.getType()为5时会出现该异常——此时文件系统类型为光驱
          LOGGER.info("----------------------------------------------------------------------------------");
          LOGGER.info("盘符名称:{}",fs.getDirName());
          //经测试，会输出个G:\ 我表示是相当的不解。后来发现是我笔记本的光驱，吐血。。。这也行。怪不得原来这代码是OK的
          //估计是台式机，还是没光驱的台式机。
          continue;
        }
        //下面单独这行代码就会报错：org.hyperic.sigar.SigarException: The device is not ready.
        //usage = getInstance().getFileSystemUsage(fs.getDirName());
        switch (fs.getType()) {
          case 0: // TYPE_UNKNOWN ：未知
            break;
          case 1: // TYPE_NONE
            break;
          case 2: // TYPE_LOCAL_DISK : 本地硬盘
            LOGGER.info("{}文件系统总大小:{} KB",new Object[]{fs.getDevName(),usage.getTotal()});
            LOGGER.info("{}文件系统剩余大小:{} KB",new Object[]{fs.getDevName(),usage.getFree()});
            LOGGER.info("{}文件系统可用大小:{} KB",new Object[]{fs.getDevName(),usage.getAvail()});
            LOGGER.info("{}文件系统已经使用量:{} KB",new Object[]{fs.getDevName(),usage.getUsed()});
            LOGGER.info("{}文件系统资源的利用率:{} %",new Object[]{fs.getDevName(),usage.getUsePercent() * 100D});
            break;
          case 3:// TYPE_NETWORK ：网络
            break;
          case 4:// TYPE_RAM_DISK ：闪存
            break;
          case 5:// TYPE_CDROM ：光驱
            break;
          case 6:// TYPE_SWAP ：页面交换
            break;
        }

        LOGGER.info("{}读出:{}",new Object[]{fs.getDevName(),usage.getDiskReads()});
        LOGGER.info("{}写入:{}",new Object[]{fs.getDevName(),usage.getDiskWrites()});
      }
    }

    /**
     * 6.获取网络信息
     */
    public void getNetInfos() throws SigarException {
      String nIfNames[] = SIGAR.getNetInterfaceList();
      for (int i = 0; i < nIfNames.length; i++) {
        String name = nIfNames[i];
        NetInterfaceConfig nIfConfig = SIGAR.getNetInterfaceConfig(name);
        LOGGER.info("网络设备名:{}",name);
        LOGGER.info("IP地址:{}",nIfConfig.getAddress());
        LOGGER.info("子网掩码:{}",nIfConfig.getNetmask());

        if ((nIfConfig.getFlags() & 1L) <= 0L) {
          System.out.println("getNetInterfaceStat not exist");
          continue;
        }
        NetInterfaceStat nIfStat = SIGAR.getNetInterfaceStat(name);
        LOGGER.info("接收的总包裹数:{}",nIfStat.getRxPackets());
        LOGGER.info("发送的总包裹数:{}",nIfStat.getTxPackets());
        LOGGER.info("接收到的总字节数:{}",nIfStat.getRxBytes());
        LOGGER.info("发送的总字节数:{}",nIfStat.getTxBytes());
        LOGGER.info("接收到的错误包数:{}",nIfStat.getRxErrors());
        LOGGER.info("发送数据包时的错误数:{}",nIfStat.getTxErrors());
        LOGGER.info("接收时丢弃的包数:{}",nIfStat.getRxDropped());
        LOGGER.info("发送时丢弃的包数:{}",nIfStat.getTxDropped());
      }
    }

  public void computePersistence() {

    double totalTime = cpuPerc.getCombined() * 100;

    double userTime = cpuPerc.getUser() * 100;

    double niceTime = cpuPerc.getNice() * 100;

    double systemTime = cpuPerc.getSys() * 100;

    double iowaitTime = cpuPerc.getWait() * 100;

    double idleTime = cpuPerc.getIdle() * 100;

    String[] allTimes = getAllTimes();

    long totalMemory = mem.getTotal() ;

    long usedMemory = mem.getUsed() ;

    long idleMemory = mem.getFree() ;

    long swapSize = swap.getTotal() ;

    long usedSwap = swap.getUsed() ;

    long idleSwap = swap.getFree() ;
  }
  private String[] getAllTimes() {
    String[] allTimes = new String[6];
    StringBuilder[] builders = new StringBuilder[6];
    for (int i = 0; i < builders.length; i++) {
      builders[i] = new StringBuilder();
    }
    for (CpuPerc aCpuPercList : cpuPercList) {
      double d1 = aCpuPercList.getCombined() * 100;
      double d2 =aCpuPercList.getUser() * 100;
      double d3 = aCpuPercList.getNice() * 100;
      double d4 =aCpuPercList.getSys() * 100;
      double d5 =aCpuPercList.getWait() * 100;
      double d6 = aCpuPercList.getIdle() * 100;
    }
    return allTimes;
  }
  @Override
  public void run() {
    start();
  }

  /**
   * 在jdk目录的bin下面添加：sigar-amd64-winnt.dll，sigar-x86-winnt.dll文件
   * @param args
   */
  /**
   *
   * 输出结果如下：/usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib
   * 编译运行输出结果中，找到第二个文件目录：如/usr/lib64，在此文件夹下面添加：libsigar-amd64-linux.so（针对64位的操作系统），或者libsigar-x86-linux.so（针对32位的操作系统）文件.如果访问权限不够，还需要sudo chmod 744修改libsigar-amd64-linux.so文件权限。
   * @param args
   */
  public static void main(String[] args) {
    NetstatMonitorForWindows windows = new NetstatMonitorForWindows("127.0.0.1");
    windows.start();
  }

  /**
   * java verbose 通过form 后面判断其安装路径
   *
   * window判断jdk 的 bin 下面是否有 sigar-amd64-winnt.dll，sigar-x86-winnt.dll  没有将这二个文件写入bin里面
   *
   * Linux 判断jdk 的 bin 下面是否有 libsigar-amd64-linux.so，libsigar-x86-linux.so  没有将这二个文件写入bin里面
   *
   */
}
