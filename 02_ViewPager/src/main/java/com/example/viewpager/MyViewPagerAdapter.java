package com.example.viewpager;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * @author 淡然爱汝不离
 */
public class MyViewPagerAdapter extends PagerAdapter {

    public static final int MAX_ITEM_COUNTS = 400;
    private List<ImageView> imageList;
    private List<String> imageDescriptions;
    private Handler handler;

    MyViewPagerAdapter(List<ImageView> imageList, List<String> imageDescriptions) {
        this.imageList = imageList;
        this.imageDescriptions = imageDescriptions;
    }


    /**
     * 相当于getView方法
     *
     * @param container ViewPager自身
     * @param position  当前实例化页面的位置，因为ViewPager总是会保存三个页面，
     *                          因此当前实例化的页面是当前显示的页面的位置 + 1
     * @return item中的对象
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        /* 因为当前位置可能远远大于数据长度，所以需要让数据长度对当前位置取余得到需要显示的数据，
        此时向右滑动会循环显示数据 */
        final int realPosition = position % imageList.size();
        View view = imageList.get(realPosition);
        //添加到ViewPager中，否则视图不显示
        container.addView(view);
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    //手指按下
                    case MotionEvent.ACTION_DOWN:
                        // removeCallbacksAndMessages(): 若传入参数为空会移除handle队列中的所有消息
                        System.out.println("手指按下");
                        handler.removeCallbacksAndMessages(null);
                        break;
                    //手指移动
                    case MotionEvent.ACTION_MOVE:
                        break;
                    //同样滑动时会触发，同时ACTION_UP会被取消
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    //手指松开
                    case MotionEvent.ACTION_UP:
                        System.out.println("手指松开");
                        handler.sendEmptyMessageDelayed(0, 3000);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), imageDescriptions.get(realPosition), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    /**
     * 比较view和object是否同一个实例
     *
     * @param view   当前页面上的对象
     * @param object instantiateItem返回的结果
     * @return view与object是否相等
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * 释放资源  ,viewpager最多只保存三个页面，当显示后面的页面时前面的页面会被相应释放
     *
     * @param container viewpager
     * @param position  要释放的位置
     * @param object    要释放的对象
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //若用position移除视图会有问题
        container.removeView((View) object);
    }


    /**
     * 得到item的总数
     *
     * @return 数据总个数
     */
    @Override
    public int getCount() {
        return MAX_ITEM_COUNTS;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
