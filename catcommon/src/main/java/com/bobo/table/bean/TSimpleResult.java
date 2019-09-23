package com.bobo.table.bean;

import java.util.List;

public class TSimpleResult extends SimpleResult {
    private List<Baseid> baseids;
    public List<Baseid> getBaseids() {
        return baseids;
    }
    public void setBaseids(List<Baseid> baseids) {
        this.baseids = baseids;
    }
}
