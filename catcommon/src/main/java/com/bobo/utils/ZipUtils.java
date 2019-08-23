package com.bobo.utils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * zip工具类
 */
public class ZipUtils {

    /**
     * 将指定的压缩文件解压到指定的目标目录下. 如果指定的目标目录不存在或其父路径不存在, 将会自动创建.
     *
     * @param zipFile  将会解压的压缩文件
     * @param destFile 解压操作的目录
     */
    public static void unzipFile(String zipFile, String destFile) throws IOException {
        unzipFile(new File(zipFile), new File(destFile));
    }

    /**
     * 将指定的压缩文件解压到指定的目标目录下. 如果指定的目标目录不存在或其父路径不存在, 将会自动创建.
     *
     * @param zipFile  将会解压的压缩文件
     * @param destFile 解压操作的目录目录
     */
    public static void unzipFile(File zipFile, File destFile) throws IOException {
        unzipFile(openInputStream(zipFile), destFile);
    }
    /**
     * 将指定的输入流解压到指定的目标目录下.
     *
     * @param is       将要解压的输入流
     * @param destFile 解压操作的目标目录
     * @throws IOException
     */
    public static void unzipFile(InputStream is, File destFile) throws IOException {
        try {
            if (is instanceof ZipInputStream) {
                doUnzipFile((ZipInputStream) is, destFile);
            } else {
                doUnzipFile(new ZipInputStream(is), destFile);
            }
        } catch (IOException e) {
        } finally {
            if (null != is) {
                is.close();
            }
        }
    }


    /**
     * 将指定文件或者指定目录下的所有文件压缩并生成指定路径的压缩文件. 如果压缩文件的路径或父路径不存在, 将会自动创建.
     *
     * @param srcFile  将要进行压缩的文件或目录
     * @param destFile 最终生成的压缩文件的路径
     * @throws IOException
     */
    public static void zipFile(String srcFile, String destFile) throws IOException {
        zipFile(new File(srcFile), new File(destFile));
    }

    /**
     * 将指定文件或目录下的所有文件压缩并生成指定路径的压缩文件. 如果压缩文件的路径或父路径不存在, 将会自动创建.
     *
     * @param srcFile  将要进行压缩的文件或目录
     * @param destFile 最终生成的压缩文件的路径
     * @throws IOException
     */
    public static void zipFile(File srcFile, File destFile) throws IOException {
        zipFile(srcFile, openOutputStream(destFile));
    }

    /**
     * 将指定文件或目录下的所有文件压缩并将流写入指定的输出流中.
     *
     * @param srcFile      将要进行压缩的目录
     * @param outputStream 用于接收压缩产生的文件流的输出流
     */
    private static void zipFile(File srcFile, OutputStream outputStream) {
        zipFile(srcFile, new ZipOutputStream(outputStream));
    }
    /**
     * 将指定目录下的所有文件压缩并将流写入指定的ZIP输出流中.
     *
     * @param srcFile 将要进行压缩的目录
     * @param zipOut  用于接收压缩产生的文件流的ZIP输出流
     */
    public static void zipFile(File srcFile, ZipOutputStream zipOut) {
        try {
            doZipFile(srcFile, zipOut,null);
        } catch (IOException e) {
        } finally {
            if (null != zipOut) {
                try {
                    zipOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, "", KeepDirStructure);
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[4 * 1024];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        String tmpName = "".equals(name) ? file.getName() : name + File.separator + file.getName();
                        compress(file, zos, tmpName, KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            }
        }
    }

    /**
     * 压缩文件或目录到指定ZipOutputStream
     *
     * @param srcFile 指定文件或者目录
     * @param zipOut  指定ZipOutputStream输出流
     *                文件组织到ZIP文件时的路径
     * @throws IOException
     */
    public static void doZipFile(File srcFile, ZipOutputStream zipOut,String name) throws IOException {
        if (srcFile.isFile()) {
            zipOut.putNextEntry(new ZipEntry(srcFile.getName()));
            InputStream is = openInputStream(srcFile);
            try {
                copy(is, zipOut);
            } catch (Exception e) {
            } finally {
                if (is != null) {
                    is.close();
                }
            }
            return;
        }
        File[] files = srcFile.listFiles();
        if (files != null) {
            for (File file : files) {
                String tmpName = null == name ? file.getName() : name + File.separator + file.getName();
                doZipFile(file, zipOut, tmpName);
            }
        }
    }

    public static void doUnzipFile(ZipFile zipFile, File dest) throws IOException {
        if (!dest.exists()) {
            forceMkdir(dest);
        }
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            File file = new File(dest, entry.getName());
            if (entry.getName().endsWith(File.separator)) {
                forceMkdir(file);
            } else {
                OutputStream out = openOutputStream(file);
                InputStream in = zipFile.getInputStream(entry);
                try {
                    copy(in, out);
                } catch (Exception e) {
                } finally {
                    if (null != out) {
                        out.close();
                    }
                }
            }
        }
        zipFile.close();
    }



    /**
     * @param zipInput
     * @param dest
     * @throws IOException
     */
    private static void doUnzipFile(ZipInputStream zipInput, File dest) throws IOException {
        if (!dest.exists()) {
            forceMkdir(dest);
        }
        for (ZipEntry entry; (entry = zipInput.getNextEntry()) != null; ) {
            String entryName = entry.getName();
            entryName = entryName.replaceAll(":", "");
            File file = new File(dest, entryName);
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            OutputStream out = openOutputStream(file);
            try {
                copy(zipInput, out);
            } catch (Exception e) {
            } finally {
                if (null != out) {
                    out.close();
                }
            }
            zipInput.closeEntry();
        }
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            } else if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            } else {
                return new FileInputStream(file);
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int) count;
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return copyLarge(input, output, new byte[4096]);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count = 0L;

        int n;
        for (boolean var5 = false; -1 != (n = input.read(buffer)); count += (long) n) {
            output.write(buffer, 0, n);
        }

        return count;
    }

    public static FileOutputStream openOutputStream(File file) throws IOException {
        return openOutputStream(file, false);
    }

    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }

            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("Directory '" + parent + "' could not be created");
            }
        }

        return new FileOutputStream(file, append);
    }

    public static void forceMkdir(File directory) throws IOException {
        String message;
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                message = "File " + directory + " exists and is " + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else if (!directory.mkdirs() && !directory.isDirectory()) {
            message = "Unable to create directory " + directory;
            throw new IOException(message);
        }

    }
}
