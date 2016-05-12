package com.example.zht.mytest.Utils;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by zhangHaiTao on 2016/5/12.
 */
public class ViewUtil {
    /**
     * 将View从它的父View移除
     * @param view
     * @return
     */
    public static boolean removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup)parent).removeView(view);
                return true;
            }
        }
        return false;
    }

    public static boolean isTouchInView(MotionEvent event, View view) {
        int[] vLoc = new int[2];
        view.getLocationOnScreen(vLoc);
        float xDown = event.getRawX();
        float yDown = event.getRawY();

        return xDown >= vLoc[0] && xDown <= (vLoc[0]+view.getWidth())
                && yDown >= vLoc[1] && yDown <= (vLoc[1] + view.getHeight());
    }
}
