package com.example.popupwindow;

import android.content.Context;

/**
 * @author 淡然爱汝不离
 * 作用：dp 与px相互转换工具
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }
}
