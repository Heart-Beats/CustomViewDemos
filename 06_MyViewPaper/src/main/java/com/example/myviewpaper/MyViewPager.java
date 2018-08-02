package com.example.myviewpaper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author SYSTEM  on  2018/07/28 at 20:44
 * 邮箱：913305160@qq.com
 */
public class MyViewPager extends ViewGroup {

    /**
     * 手势识别器
     * 1.定义出来
     * 2.实例化-把想要的方法给重写
     * 3.在onTouchEvent()把事件传递给手势识别器
     */
    private GestureDetector detector;
    private OnPagerChangeListener onPagerChangeListener;
    /**
     * 滚动类，该类系统也为我们提供了，就是Scroller
     */
    private MyScroller scroller;
    private int currentIndex;
    private float startX;
    private float downX;
    private float downY;


    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        scroller = new MyScroller();
        //实例化手势识别器
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

            /**
             *
             * @param e1
             * @param e2
             * @param distanceX 在X轴滑动了的距离
             * @param distanceY 在Y轴滑动了的距离
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //该方法可让内部视图滚动，参数为视图相对坐标轴在屏幕之外的偏移量：
                //                                                     X轴：左边+ 右边—
                //                                                     Y轴：上边+ 下边-
                scrollBy((int) distanceX, getScrollY());
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }
        });
    }

    /**
     * ViewGroup中必须重写此方法，用于给子View指定位置
     *
     * @param b  是否改变视图
     * @param i  视图的左上角X轴坐标
     * @param i1 视图的左上角Y轴坐标
     * @param i2 视图的右下角X轴坐标
     * @param i3 视图的右下角Y轴坐标
     */
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //没重写onDraw（），会调用默认方法绘制视图
        for (int j = 0; j < getChildCount(); j++) {
            View childView = getChildAt(j);
            //给每个孩子指定在屏幕的坐标位置
            childView.layout(j * getWidth(), 0, (j + 1) * getWidth(), getHeight());
        }
    }

    /**
     * 事件的分发
     *
     * @param ev 触摸事件
     * @return 返回值为true，表示事件将向下分发，即将事件传递给它的子View
     * 返回值为false，表示事件不向下分发
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 事件的拦截
     *
     * @param ev 触摸事件
     * @return  返回值为true表示事件将被拦截，不再向下分发，同时会执行onTouchEvent()方法
     *           返回值为false表示事件不被拦截，将会再向下分发，直至被最底层View消费
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX = Math.abs(ev.getX() - downX);
                float distanceY = Math.abs(ev.getY() - downY);
                //如果X轴上滑动的距离大于Y轴上的，可表示左右滑动，否则表示为上下滑动
                if (distanceX > distanceY && distanceX > MotionEvent.AXIS_TOUCH_MINOR) {
                    return true;
                    //左右滑动将会被拦截并被该View消费
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return false;
    }


    /**
     * 事件的处理
     *
     * @param event 触摸事件
     * @return      返回值为true，表示事件将会被当前View消费，不再回传父View，
     *       同时当某一事件被消费后，和该事件相关的事件都会交给该View处理，如：down和up一系列相关的事件
     *               返回值为false，表示事件不处理，交给父View中的onTouchEvent（）处理，
     *      同时当某一事件不被处理，和该事件相关的事件也都不会再分发给该View。
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //把事件传递给手势识别器
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //来到新的坐标
                float endX = event.getX();
                //下标位置
                int tempIndex = currentIndex;
                if ((startX - endX) > getWidth() / 2) {
                    //显示下一个页面
                    tempIndex++;
                } else if ((endX - startX) > getWidth() / 2) {
                    //显示上一个页面
                    tempIndex--;
                }
                //根据下标位置移动到指定的页面
                scrollToPager(tempIndex);
                if (endX - startX < MotionEvent.AXIS_TOUCH_MINOR) {
                    this.performClick();
                }
                break;
            default:
                break;
        }
        // 事件会被消费
        return true;
    }

    /**
     * onTouchEvent（）方法中，确定为点击事件调用performClick()会执行该方法
     *
     * @return 确定一个已被分配的OnClickListener得到调用将返回true，否则返回false
     */
    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void scrollToPager(int tempIndex) {
        if (tempIndex < 0) {
            tempIndex = 0;
        } else if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }
        //当前页面的下标位置
        currentIndex = tempIndex;
        if (onPagerChangeListener != null) {
            onPagerChangeListener.onPagerToChange(currentIndex);
        }
        //getScrollX()代表当前视图相对X轴在屏幕外的偏移量
        int distanceX = currentIndex * getWidth() - getScrollX();
        //scrollBy(distanceX,0);
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0, 500);

        //onDraw()与computeScroll()都会被执行
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            float currentX = scroller.getCurrX();
            float currentY = scroller.getCurrY();
            // scrollTo（）方法中的参数是在以父视图的左上角为原点的坐标系的相对坐标
            scrollTo((int) currentX, (int) currentY);
            invalidate();
        }
    }

    public void setOnPagerChangeListener(OnPagerChangeListener onPagerChangeListener) {
        this.onPagerChangeListener = onPagerChangeListener;
    }

    /**
     * ViewGroup中没有measure（）方法，因为它是相对子视图才有意义的，所以它不需要测量自己，
     * 只需要测量子视图。在视图层级树中，只有当孩子为View时，才会调用孩子的measure（）方法测量它自己。
     * measureChild（）-->   view.measure（）
     *
     * @param widthMeasureSpec  父层视图给当前视图的宽和模式
     * @param heightMeasureSpec 父层视图给当前视图的高和模式
     *                          模式：  MeasureSpec.AT_MOST;      父容器检测出View的精确大小
     *                          MeasureSpec.UNSPECIFIED;   父容器不对View有任何限制
     *                          MeasureSpec.EXACTLY;       父容器指定一个可用大小SpecSize,View大小不能大于此值
     */
    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        super.measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     *      ViewGroup在onLayout（）方法中给它的每个子视图指定位置后，系统即可默认绘画出子View，但是若
     * 子View是一个ViewGroup，此时并不会绘制出子ViewGroup中的子View，这时就需要对子ViewGroup进行
     * 测量---measure（），才会绘制出子ViewGroup中的子View。
     *          系统的onMeasure中所干的事：
     *                 1、根据 widthMeasureSpec、heightMeasureSpec 求得宽度width、高度，和父view给的模式
     *                 2、父View根据自身的宽度width 和自身的padding 值相减，求得子view可以拥有的宽度newWidth
     *                 3、根据 newWidth 和模式求得一个新的MeasureSpec值:
     *                     MeasureSpec.makeMeasureSpec(newSize, newMode);
     *                 4、用新的MeasureSpec来计算子view
     * <p>
     *       因此看来，在ViewGroup中重写onMeasure（）方法时：
     *       最好对它的每个子View都进行测量---measure（），这样才能确保它的所有后代View都能绘制！！！
     *
     * @param widthMeasureSpec  父层视图给当前视图的宽和模式
     * @param heightMeasureSpec 父层视图给当前视图的高和模式
     *                          1.测量的时候测量多次,因为有视图嵌套
     *                          2.widthMeasureSpec父层视图给当前视图的宽和模式
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }


    public interface OnPagerChangeListener {
        /**
         * 页面改变时的回调方法
         *
         * @param position 改变后的页面
         */
        void onPagerToChange(int position);
    }


}
