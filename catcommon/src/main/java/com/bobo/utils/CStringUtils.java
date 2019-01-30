package com.bobo.utils;

import org.hyperic.sigar.OperatingSystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;

public class CStringUtils {

    public static String conventFromGzip(String str,String chartset) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in;
        GZIPInputStream gunzip = null;

        in = new ByteArrayInputStream(str.getBytes(chartset));
        gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[1024];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }

        return out.toString();
    }

    /**
     * gzip字符串解压
     * @param str
     * @return
     * @throws IOException
     */
    public static String conventFromGzip(String str) throws IOException {
        return conventFromGzip( str,"ISO-8859-1");
    }

    private static final String WINDOWS = "Windows";

    public static String getLinuxHostIP() throws Exception {
        String localHostIP = null;
        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    String ip = ips.nextElement().getHostAddress();
                    if (isIp(ip) && !"127.0.0.1".equals(ip)) {
                        localHostIP = ip;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (localHostIP == null) {
            throw new Exception("get linux ip error. local_ip = null.");
        }
        return localHostIP;
    }

    public static String getWindowsHostIP() throws Exception {
        String localHostIP = null;
        try {
            localHostIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (localHostIP == null) {
            throw new Exception("get windows ip error. local_ip = null.");
        }
        return localHostIP;
    }

    public static String getHostIP() throws Exception {
        OperatingSystem os = OperatingSystem.getInstance();
        String vendorName = os.getVendorName();
        if (vendorName.contains(WINDOWS)) {
            return getWindowsHostIP();
        } else {
            return getLinuxHostIP();
        }

    }

    private static boolean isIp(String ipAddress) {
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ipAddress.matches(regex);
    }
}
