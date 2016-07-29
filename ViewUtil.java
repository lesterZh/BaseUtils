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
	
	/**
     * 判断触电是否在对应的视图区域
     * @param view
     * @param x
     * @param y
     * @return
     */
    private boolean isInViewZone(View view, int x, int y) {
        Rect mChangeImageBackgroundRect = null;
        if (null == mChangeImageBackgroundRect) {
            mChangeImageBackgroundRect = new Rect();
        }
        view.getDrawingRect(mChangeImageBackgroundRect);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mChangeImageBackgroundRect.left = location[0];
        mChangeImageBackgroundRect.top = location[1];
        mChangeImageBackgroundRect.right = mChangeImageBackgroundRect.right + location[0];
        mChangeImageBackgroundRect.bottom = mChangeImageBackgroundRect.bottom + location[1];
        return mChangeImageBackgroundRect.contains(x, y);
    }
}
