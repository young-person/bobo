package com.app.ftp;

import org.apache.commons.net.ftp.FTPFile;

import java.io.File;

public interface FileTransportListener {
	
	/**
	 * 断点续传 下载进度监听
	 * @param nowSize 已加载大小
	 * @param buf	本次缓存大小
	 * @param totalSize	总大小
	 */
	void downloadProgressListener(long nowSize, long buf, long totalSize);
	
	/***
	 * 断点续传 上传进度监听
	 * @param nowSize
	 * @param buf
	 * @param totalSize
	 */
	void uploadProgressListener(long nowSize, long buf, long totalSize);
	
	/**
	 * 上传监听 
	 * @param remoteFile	远程路径
	 * @param local	本地路径
	 * @param status 上传结果 
	 */
	void uploadFolderProgressListener(String remoteFile, File local, boolean status);
	
	/**
	 * 删除监听 当 isDirectory 为true 时;ftpFile,remoteFileName 均为 null 
	 * @param ftpFile ftp文件对象
	 * @param remotePath	远程目录路径
	 * @param remoteFileName	远程文件名
	 * @param isDirectory	是否文件夹
	 * @param status	删除状态
	 */
	void deleteFolderProgressListener(FTPFile ftpFile, String remotePath, String remoteFileName, boolean isDirectory, boolean status);
	
	/**
	 * 下载监听
	 * @param remotePath 远程路径
	 * @param local	本地对象
	 * @param status	下载状态
	 */
	void downloadFolderProgressListener(String remotePath, File local, boolean status);
}
