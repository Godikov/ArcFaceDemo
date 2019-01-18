package com.humu.arcfacedemo;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class InitActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    public final static String[] PERMS_START = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    private Handler handler;
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(InitActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        if (!PermissionUtils.checkPermission(this, PERMS_START)) {
            PermissionUtils.requestPermission(this, "需要部分权限", 100, PERMS_START);
        } else {
            handler.postDelayed(runnable, 500);
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        handler.postDelayed(runnable, 500);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
        //ToastUtil.showToast("授权失败");
        /**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         */
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
