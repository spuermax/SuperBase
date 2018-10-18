package com.supermax.base.common.permission.model;

import java.util.List;

public class CancelModel {
    private int requestCode;
    private List<String> denyList;

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public List<String> getDenyList() {
        return denyList;
    }

    public void setDenyList(List<String> denyList) {
        this.denyList = denyList;
    }
}
