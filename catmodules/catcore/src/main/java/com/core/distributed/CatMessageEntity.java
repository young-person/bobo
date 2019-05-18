package com.app.distributed;

import java.util.Objects;

public class CatMessageEntity {
    /**
     * 事务id.
     */
    private String transId;

    /**
     * 执行器.
     */
    private CatInvocation catInvocation;

    public CatMessageEntity(String transId, CatInvocation catInvocation) {
        this.transId = transId;
        this.catInvocation = catInvocation;
    }

    public CatMessageEntity() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CatMessageEntity)) return false;
        CatMessageEntity that = (CatMessageEntity) o;
        return Objects.equals(transId, that.transId) &&
                Objects.equals(catInvocation, that.catInvocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transId, catInvocation);
    }

    @Override
    public String toString() {
        return "CatMessageEntity{" +
                "transId='" + transId + '\'' +
                ", catInvocation=" + catInvocation +
                '}';
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public CatInvocation getCatInvocation() {
        return catInvocation;
    }

    public void setCatInvocation(CatInvocation catInvocation) {
        this.catInvocation = catInvocation;
    }
}
