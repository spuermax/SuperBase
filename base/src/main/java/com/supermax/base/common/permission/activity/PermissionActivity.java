package com.supermax.base.common.permission.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.supermax.base.R;
import com.supermax.base.common.permission.inter.IPermission;
import com.supermax.base.common.permission.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

/*
 * @Author yinzh
 * @Date   2018/10/18 16:26
 * @Description
 */
public class PermissionActivity extends FragmentActivity{
    private static IPermission permissionListener;
    private String[] permissions;
    private static final String PERMISSION_KEY = "permission_key";
    private static final String REQUEST_CODE = "request_code";
    private int requestCode;

    /**
     *Intent activity for permission
     */
    public static void PermissionRequest(Context context, String[] permissions, int requestCode, IPermission iPermission) {
        permissionListener = iPermission;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PERMISSION_KEY, permissions);
        bundle.putInt(REQUEST_CODE, requestCode);
        intent.putExtras(bundle);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(0, 0);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_permission);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            permissions = bundle.getStringArray(PERMISSION_KEY);
            requestCode = bundle.getInt(REQUEST_CODE, 0);
        }
        if (permissions == null || permissions.length <= 0) {
            finish();
            return;
        }
        requestPermission(permissions);
    }


    /**
     * request permission
     */
    private void requestPermission(String[] permissions) {

        if (PermissionUtil.hasSelfPermissions(this, permissions)) {
            //all permissions granted
            if (permissionListener != null) {
                permissionListener.PermissionGranted();
            }
            finish();
            overridePendingTransition(0, 0);
        } else {
            //request permissions
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (PermissionUtil.verifyPermissions(grantResults)) {
            //所有权限都同意
            if (permissionListener != null) {
                permissionListener.PermissionGranted();
            }
        } else {
            if (permissions.length != grantResults.length) return;
            List<String> denyList = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    denyList.add(permissions[i]);
                }
            }
            if (!PermissionUtil.shouldShowRequestPermissionRationale(this, permissions)) {
                //权限被拒绝并且选中不再提示

                if (permissionListener != null) {
                    permissionListener.PermissionDenied(requestCode, denyList);
                }
            } else {
                //权限被取消
                if (permissionListener != null) {
                    permissionListener.PermissionCanceled(requestCode, denyList);
                }
            }

        }
        finish();
        overridePendingTransition(0, 0);
    }



}
