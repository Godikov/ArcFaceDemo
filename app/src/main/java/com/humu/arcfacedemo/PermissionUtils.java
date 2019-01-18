package com.humu.arcfacedemo;

import android.app.Activity;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2018/6/25.
 */

public class PermissionUtils {

    /**
     * 检查是否有某权限
     * @param context
     */
    public static boolean checkPermission(Activity context,String[] perms){
        return EasyPermissions.hasPermissions(context,perms);
    }

    /**
     * 请求权限
     * @param context
     */
    public static void requestPermission(Activity context,String tip,int requestCode,String[] perms) {
        EasyPermissions.requestPermissions(context, tip,requestCode,perms);
    }

}
