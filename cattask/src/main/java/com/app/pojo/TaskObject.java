package com.app.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;
/**
 * 调度实体
 * @author bobo
 *
 */
public class TaskObject implements  java.io.Serializable {
	private static final long serialVersionUID = 1831921908849300752L;

	private String app;
	/** 调度唯一标识 */
    private String id ;
    /**
     * 调度组ID
     */
    private String groupId;
    /**
     * 调度名称
     */
	private String name;
    /** 
     * 是否禁用调度 
     */
    private boolean disable;
    /**
     * 调度类路径
     */
    private String classPath;

    /** 
     * 调度CronTrigger表达式
     */
    private String expression;
    
    /**
     * 调度参数
     */
    private String params ;
    /**
     * 详细说明
     */
    private String instruction;
    /**
     * 调度状态
     */
    private ScheduleJobStatus status = ScheduleJobStatus.STARTED ;
    /**
     * 最后更新时间
     */
    private Date updateTime;
    /**
     * 执行时间
     */
    private long executeTime;
    /**
     * 下次执行时间
     */
    private Date nextTime;
    
    @XmlAttribute(name="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlAttribute(name="disable")
    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
    
    @XmlAttribute(name="expression")
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
    @XmlAttribute(name="status")
    public ScheduleJobStatus getStatus() {
    	if (!ScheduleJobStatus.Status.contains(status.getCode())) {
    	     throw new RuntimeException("当前状态不符合定义规范 不在0-5范围之间");
		}
        return status;
    }

    public void setStatus(ScheduleJobStatus status) {
        this.status = status;
    }

    @XmlElement(name="params")
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
    
	@XmlAttribute(name="class")
	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	@XmlElement(name="description")
	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "TaskObject [app=" + app + ", id=" + id + ", groupId=" + groupId
				+ ", name=" + name + ", disable=" + disable + ", classPath="
				+ classPath + ", expression=" + expression + ", params="
				+ params + ", instruction=" + instruction + ", status="
				+ status + ", updateTime=" + updateTime + ", executeTime="
				+ executeTime + ", nextTime=" + nextTime + "]";
	}

	public long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(long executeTime) {
		this.executeTime = executeTime;
	}

	public Date getNextTime() {
		return nextTime;
	}

	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}

}
