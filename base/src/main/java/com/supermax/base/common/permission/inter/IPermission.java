package com.supermax.base.common.permission.inter;

import java.util.List;

/**
 * @Author yinzh
 * @Date   2018/5/25 15:04
 * @Description
 */
public interface IPermission {

    void PermissionGranted();


    void PermissionDenied(int requestCode, List<String> denyList);


    void PermissionCanceled(int requestCode, List<String> denyList);
}
