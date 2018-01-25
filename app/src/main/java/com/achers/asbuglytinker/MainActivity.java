package com.achers.asbuglytinker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.beta.Beta;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView =findViewById(R.id.versionName);

        String channel = WalleChannelReader.getChannel(this.getApplicationContext());

        textView.setText(getCurrentVersion(this)+channel);

        imageView=findViewById(R.id.iv);

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }


    /**
     *显示Log
      * @param view
     */
    public void log(View view){
        //String log=null;
        String log="liu";
        Toast.makeText(this,"显示log"+log.length(),Toast.LENGTH_SHORT).show();
    }

    /**
     *显示图片
     * @param view
     */
    public void img(View view){
        imageView.setImageResource(R.drawable.ic_launcher_foreground);
        Toast.makeText(this,"显示图片,没有图片",Toast.LENGTH_SHORT).show();
    }

    /**
     *退出程序
     * @param view
     */
    public void exit(View view){
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 获取当前版本.
     *
     * @param context 上下文对象
     * @return 返回当前版本
     */
    public String getCurrentVersion(Context context) {
        try {
            PackageInfo packageInfo =
                    context.getPackageManager().getPackageInfo(this.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;

            return versionName + "." + versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("MainActivity", "onBackPressed");

        Beta.unInit();
    }
}
