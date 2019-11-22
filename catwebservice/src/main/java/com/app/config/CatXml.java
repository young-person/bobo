package com.app.config;

import com.app.crawler.base.RCache;

public class CatXml {

    private String dataPath = RCache.CAT_CACHE.get("dataPath").getValue();;
    private String tipsDataPath;
    private String periodTime;
    private String request;
    private String sendEmailAddress;
    private String sexforumPath;
    private String DOMIAN;
    private String sendEmailSubject = RCache.CAT_CACHE.get("sendEmailSubject").getValue();
    private String emailSubjectTemplate;
    private String usersPath;

    public String getDataPath() {
        return dataPath;
    }

    public String getTipsDataPath() {
        return tipsDataPath;
    }

    public String getPeriodTime() {
        return periodTime;
    }

    public String getRequest() {
        return request;
    }

    public String getSendEmailAddress() {
        return sendEmailAddress;
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
