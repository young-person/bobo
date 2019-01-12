package com.app.pojo;

import java.util.Arrays;
import java.util.List;

public enum ScheduleJobStatus {
    STARTED(0, "运行"),
    RUNNING(1, "正在运行"),
    FINISHED(2, "已经完成"),
    EXCEPTION(3, "出现异常结束"),
    PAUSE(4,"暂停"),   
    STOPPED(5, "停止");
    
    public final static List<Integer> Status = Arrays.asList(STARTED.ordinal(),RUNNING.ordinal(),FINISHED.ordinal(),EXCEPTION.ordinal(),STOPPED.ordinal(),PAUSE.ordinal());
    private int code;
    private String desc;
    public static ScheduleJobStatus getEnumByKey(int key){
        for(ScheduleJobStatus temp:ScheduleJobStatus.values()){
            if(temp.getCode() == key){
                return temp;
            }
        }
        return null;
    }
    
    ScheduleJobStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
