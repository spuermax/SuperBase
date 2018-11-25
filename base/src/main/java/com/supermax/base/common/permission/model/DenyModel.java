package com.supermax.base.common.permission.model;

import java.util.List;


/**
 * @Author yinzh
 * @Date   2018/5/25 15:04
 * @Description 拒绝权限 用户拒绝并且选中下次不再提醒则为拒绝
 */
public class DenyModel {

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
