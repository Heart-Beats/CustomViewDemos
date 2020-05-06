package com.example.customviewstudy.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private String[] TAB_TITLES;
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public SectionsPagerAdapter(Context context, FragmentManager fm, String type) {
        this(context, fm);
        switch (type) {
            case "绘制":
                TAB_TITLES = new String[]{"drawColor", "drawCircle", "drawRect", "drawArc", "drawPath", "直方图", "饼图", "LinearGradient", "RadialGradient", "SweepGradient",
                        "setXferMode", "setPathEffect", "setMaskFilter", "getFillPath", "setTypeface", "measureText", "getFontMetrics", "clipPath", "rotate", "翻页效果"};
                break;
            case "动画":
                TAB_TITLES = new String[]{"translationX/Y/Z", "rotationX/Y", "ViewPropertyAnimator-多属性", " ObjectAnimator", "ArgbEvaluator", "自定义Evaluator",
                        "ofObject()", "PropertyValuesHolder", "ofKeyframe()", "AnimatorSet", "ValueAnimator", "小球运动"};
                break;
            default:
                break;

        }
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1, TAB_TITLES[position]);
    }

    @Nullable
    @Override
    public String getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {


        // Show 2 total pages.
        return TAB_TITLES.length;
    }
}