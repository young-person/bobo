package com.app.ftp.impl;

import com.app.ftp.FileTransportListener;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;

public abstract class FileTransportAdapter implements FileTransportListener {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 进度
	 * @param cur 当前大小
	 * @param total	总大小
	 * @return	返回精度为2 的比例
	 */
	protected static double progress(long cur,long total){
		double ratio =  cur/(total*1.0)*10000/100.0;
		return new BigDecimal(ratio).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
	}
	/**
	 * 自动换算单位
	 * @param size 文件字节集大小
	 * @return 根据数值大小计算  返回换算单位
	 */
	protected static String getPrintSize(long size) {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        double value = (double) size;
        if (value < 1024) {
            return String.valueOf(value) + "B";
        } else {
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (value < 1024) {
            return String.valueOf(value) + "KB";
        } else {
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        if (value < 1024) {
            return String.valueOf(value) + "MB";
        } else {
            // 否则如果要以GB为单位的，先除于1024再作同样的处理
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            return String.valueOf(value) + "GB";
        }
    }
	@Override
	public void downloadProgressListener(long nowSize,long buf, long totalSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadProgressListener(long nowSize,  long buf, long totalSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadFolderProgressListener(String remoteFile, File local, boolean status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFolderProgressListener(FTPFile ftpFile, String remotePath, String remoteFileName,
			boolean isDirectory, boolean status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downloadFolderProgressListener(String remotePath, File local, boolean status) {
		// TODO Auto-generated method stub
		
	}
	
}
