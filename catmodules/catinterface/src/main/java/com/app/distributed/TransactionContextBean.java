package com.app.distributed;

import java.io.Serializable;

public class TransactionContextBean implements Serializable {
    private static final long serialVersionUID = -5289080166922118073L;

    private String transId;

    /**
     * 事务参与的角色.
     */
    private int role;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}
