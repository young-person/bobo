package com.app.config;

import com.app.crawler.base.RCache;

public class CatXml {

    private String dataPath ;
    private String tipsDataPath;
    private String periodTime ;
    private String sexforumPath ;
    private String DOMIAN;
    private String sendEmailSubject;
    private String emailSubjectTemplate;

    private String archive = "archive.json";

    private String usersPath = "users.json";

    public CatXml() {}


    public String getArchive() {
        return archive;
    }

    public String getDataPath() {
        return RCache.CAT_CACHE.get("dataPath").getValue();
    }

    public String getTipsDataPath() {
        return RCache.CAT_CACHE.get("tipsDataPath").getValue();
    }

    public String getPeriodTime() {
        return RCache.CAT_CACHE.get("periodTime").getValue();
    }

    public String getSexforumPath() {
        return RCache.CAT_CACHE.get("sexforumPath").getValue();
    }

    public String getDOMIAN() {
        return RCache.CAT_CACHE.get("DOMIAN").getValue();
    }

    public String getSendEmailSubject() {
        return RCache.CAT_CACHE.get("sendEmailSubject").getValue();
    }

    public String getEmailSubjectTemplate() {
        return RCache.CAT_CACHE.get("emailSubjectTemplate").getValue();
    }

    public String getUsersPath() {
        return usersPath;
    }
}
