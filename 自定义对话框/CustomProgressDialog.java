package com.gaia.member.gaiatt.makeorder.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaia.member.gaiatt.R;
/*
* 依赖文件和配置：
<!-- 自定义Dialog -->
 1.styles文件下
<style name="CustomProgressDialog" parent="@android:style/Theme.Dialog">
    <item name="android:windowFrame">@null</item>
    <item name="android:windowIsFloating">true</item>
    <item name="android:windowContentOverlay">@null</item>
    <item name="android:windowAnimationStyle">@android:style/Animation.Dialog</item>
    <item name="android:windowSoftInputMode">stateUnspecified|adjustPan</item>
    <item name="android:windowBackground">@android:color/transparent</item>
    <item name="android:windowNoTitle">true</item>
</style>

2.布局：layout_custom_progress_dialog.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center"
    android:background="@drawable/bg_custom_progress_dialog"
    android:gravity="center_horizontal"
    android:orientation="vertical"
    android:paddingBottom="20dp"
    android:paddingLeft="30dp"
    android:paddingRight="30dp"
    android:paddingTop="20dp" >

    <!--这里可以放置图片 替换progressBar-->
    <ProgressBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

    <TextView
        android:id="@+id/message"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:text="Message"
        android:textColor="#FFFFFF" />

</LinearLayout>

3.背景:bg_custom_progress_dialog.xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">

    <solid android:color="#404040" />

    <corners android:radius="8dp" />

</shape>
*/

/**
 * Created by zhangHaiTao on 2016/5/14.
 */
public class CustomProgressDialog extends Dialog{

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        //隐藏progressBar
        findViewById(R.id.progress_bar).setVisibility(View.GONE);

        //如果自定义了图片动画，在这里开始执行动画
        ImageView imageView = (ImageView) findViewById(R.id.image);
        AnimationDrawable ad = (AnimationDrawable) imageView.getBackground();
        ad.start();//开始动画
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    private static CustomProgressDialog mDialog = null;
    /**
     * 弹出自定义ProgressDialog
     *
     * @param context
     *            上下文，一般为activity的context
     * @param message
     *            提示
     * @param cancelable
     *            是否按返回键取消
     * @param cancelListener
     *            按下返回键监听
     * @return
     */
    public static CustomProgressDialog show(Context context, String message, boolean cancelable, OnCancelListener cancelListener) {
        if (mDialog != null) return null; //防止多次弹出

        mDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
        mDialog.setTitle("");
        mDialog.setContentView(R.layout.layout_custom_progress_dialog);


        if (message == null || message.length() == 0) {
            mDialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) mDialog.findViewById(R.id.message);
            txt.setText(message);
        }
        // 按返回键是否取消
        mDialog.setCancelable(cancelable);
        // 监听返回键处理
        mDialog.setOnCancelListener(cancelListener);
        // 设置居中
        mDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.4f;
        mDialog.getWindow().setAttributes(lp);
        // mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        //设定为系统级警告，如果需要在service或者非acivity中弹出
        //需要添加权限 <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
        //且context为app context或者activity context
//        mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        mDialog.show();

        return mDialog;
    }

    /**
     * 关闭对话框
     */
    public static void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
