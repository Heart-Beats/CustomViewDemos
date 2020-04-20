package com.example.viewpager;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 淡然爱汝不离
 */
public class MainActivity extends AppCompatActivity {

    private final int[] imageIdArray = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
    };
    private final String[] imageDescriptions = {
            "尚硅谷波河争霸赛！",
            "凝聚你我，放飞梦想！",
            "抱歉没座位了！",
            "7月就业名单全部曝光！",
            "平均起薪11345元"
    };
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ad_text)
    TextView adText;
    @BindView(R.id.point_group)
    LinearLayout pointGroup;
    private List<ImageView> imageViewList;
    private int prePointPosition;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            handler.sendEmptyMessageDelayed(0, 3000);
            return false;
        }

    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(imageViewList,
                Arrays.asList(imageDescriptions));
        myViewPagerAdapter.setHandler(handler);
        viewPager.setAdapter(myViewPagerAdapter);
        //设置当前显示的页面为总页面中间位置,同时保证是数据列表长度的整数倍，这样就可以实现一开始能向左滑动
        int midItem = MyViewPagerAdapter.MAX_ITEM_COUNTS / 2 -
                MyViewPagerAdapter.MAX_ITEM_COUNTS / 2 % imageViewList.size();
        viewPager.setCurrentItem(midItem);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             当页面滚动状态变化的时候回调这个方法
             静止-->拖拽
             拖拽-->滑动
             滑动-->静止
             */
            boolean isDragging = false;

            /**
             * 当页面滚动了的时候回调这个方法
             * @param position 当前页面的位置
             * @param positionOffset 滑动页面的百分比
             * @param positionOffsetPixels 在屏幕上滑动的像数
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 当某个页面被选中了的时候回调
             * @param position 被选中页面的位置
             */
            @Override
            public void onPageSelected(int position) {
                int realPosition = position % imageViewList.size();
                adText.setText(imageDescriptions[realPosition]);
                pointGroup.getChildAt(realPosition).setEnabled(true);
                pointGroup.getChildAt(prePointPosition).setEnabled(false);
                prePointPosition = realPosition;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    //页面静止
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (isDragging) {
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(0, 3000);
                            isDragging = false;
                        }
                        break;
                    //拖拽页面
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isDragging = true;
                        break;
                    //页面滑动
                    case ViewPager.SCROLL_STATE_SETTLING:
                        break;
                    default:
                        break;
                }

            }
        });
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initData() {
        if (imageViewList == null) {
            imageViewList = new ArrayList<>();
        }
        for (int i = 0; i < imageIdArray.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIdArray[i]);
            imageViewList.add(imageView);
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_selector);
            //控件在什么布局内就使用什么布局参数，LinearLayout.LayoutParams 对象用来设置控件在布局中的各个参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(24, 24);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
                params.setMarginStart(20);
            }
            // 给视图添加布局里的参数
            point.setLayoutParams(params);
            pointGroup.addView(point);
        }
    }
}
