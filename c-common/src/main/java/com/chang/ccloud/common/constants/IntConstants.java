package com.chang.ccloud.common.constants;

public enum IntConstants {

    MENU(1),

    BUTTON(0);

    private Integer val;

    IntConstants(Integer val) {
        this.val = val;
    }

    public Integer getVal() {
        return val;
    }

}
