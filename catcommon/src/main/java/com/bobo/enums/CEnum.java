package com.bobo.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum CEnum {
    JDK("jdk"),
    REAL("real"),
    SUM("sum");

    private String serialize;

    CEnum(final String serialize) {
        this.serialize = serialize;
    }

    public static CEnum acquire(final String serialize) {
        Optional<CEnum> serializeEnum =
                Arrays.stream(CEnum.values())
                        .filter(v -> Objects.equals(v.getSerialize(), serialize))
                        .findFirst();
        return serializeEnum.orElse(CEnum.JDK);

    }
    public String getSerialize() {
        return serialize;
    }
    public void setSerialize(final String serialize) {
        this.serialize = serialize;
    }
}
