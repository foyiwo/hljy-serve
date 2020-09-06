package com.mall.common.enumconfig;

public enum enumSynPlateform {
    B2B(1, "B2B平台"),
    B2C(2, "B2C平台"),
    B2P(3, "B2P平台");

    private int code;
    private String message;

    private enumSynPlateform(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
