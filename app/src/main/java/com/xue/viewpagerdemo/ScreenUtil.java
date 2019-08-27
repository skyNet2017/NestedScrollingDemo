package com.xue.viewpagerdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.support.annotation.ColorRes;
import android.support.v4.graphics.ColorUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import java.io.File;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Created by huangshuisheng on 2017/10/26.
 */

public class ScreenUtil {

    /**
     * 获取屏幕尺寸，但是不包括虚拟功能高度
     */
    public static int getScreenHeight() {
        try {
            int height = getWindowManager().getDefaultDisplay().getHeight();
            return height;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static int getScreenWidth() {
        try {
            int width = getWindowManager().getDefaultDisplay().getWidth();
            return width;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过反射，获取包含虚拟键的整体屏幕高度
     */
    public static int getgetScreenHeightHasVirtualKey() {
        int dpi = 0;
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dpi ==0){
            dpi = display.getHeight();
        }
        return dpi;
    }

    public static WindowManager getWindowManager() {
        return (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
    }


    public static Bitmap screenShoot(Dialog dialog) {
        View decorView = dialog.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bmp = decorView.getDrawingCache();
        decorView.destroyDrawingCache();
        return bmp;
    }








    public static int getOrientation(Context context){
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        return mConfiguration.orientation; //获取屏幕方向
    }

    public static boolean isLandscape(Context context){
        return getOrientation(context) == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isPortrait(Context context){
        return getOrientation(context) == Configuration.ORIENTATION_PORTRAIT;
    }

}
