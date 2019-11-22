package com.app.config;

import com.app.crawler.base.RCache;

public class CatXml {

    private String dataPath = RCache.CAT_CACHE.get("dataPath").getValue();;
    private String tipsDataPath = RCache.CAT_CACHE.get("tipsDataPath").getValue();
    private String periodTime;
    private String sexforumPath;
    private String DOMIAN;
    private String sendEmailSubject = RCache.CAT_CACHE.get("sendEmailSubject").getValue();
    private String emailSubjectTemplate = RCache.CAT_CACHE.get("emailSubjectTemplate").getValue();
    private String usersPath;

    private String archive = "archive.json";

    public String getArchive() {
        return archive;
    }

    public String getDataPath() {
        return dataPath;
    }

    public String getTipsDataPath() {
        return tipsDataPath;
    }

    public String getPeriodTime() {
        return periodTime;
    }

    public String getSexforumPath() {
        return sexforumPath;
    }

    public String getDOMIAN() {
        return DOMIAN;
    }

    public String getSendEmailSubject() {
        return sendEmailSubject;
    }

    public String getEmailSubjectTemplate() {
        return emailSubjectTemplate;
    }

    public String getUsersPath() {
        return usersPath;
    }
}
