package com.app.filemonitor.run;

import com.app.filemonitor.FileOperator;
import com.bobo.utils.CFileUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractFileOperator implements FileOperator {

    /**
     * 文件服务器地址
     */
    private String accessUrl;

    public AbstractFileOperator() {
    }

    public AbstractFileOperator(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    @Override
    public String getFileUrl(String realPath) {
        if (StringUtils.isEmpty(accessUrl)) {
            throw new NullPointerException("文件服务器访问地址为空.");
        }
        return StringUtils.isEmpty(realPath) ? "" : CFileUtils.addEndSlash(accessUrl) + realPath;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }
}
