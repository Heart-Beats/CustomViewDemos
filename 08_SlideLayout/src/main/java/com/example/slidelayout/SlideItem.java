package com.example.slidelayout;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * @author SYSTEM  on  2018/08/01 at 20:35
 * 邮箱：913305160@qq.com
 */
public class SlideItem extends FrameLayout {

    private TextView itemContent;
    private TextView itemMenu;
    private Scroller scroller;
    private float startX;
    private float downX;
    private float downY;
    private OnStateChangeListener onStateChangeListener;
    public SlideItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        itemContent = (TextView) getChildAt(0);
        itemMenu = (TextView) getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        itemMenu.layout(itemContent.getWidth(), 0, itemContent.getWidth() + itemMenu.getWidth(),
                itemMenu.getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = startX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动的距离， 左 - 右 +
                float distanceX = event.getX() - startX;
                // getScrollX()是当前视图相对X轴在屏幕外的偏移 ， 左 + 右 —
                int toScrollX = (int) (getScrollX() - distanceX);
                //  scrollTo（）是将视图移到坐标轴的指定位置
                if (toScrollX < 0) {
                    toScrollX = 0;
                } else if (toScrollX > itemMenu.getWidth()) {
                    toScrollX = itemMenu.getWidth();
                }
                scrollTo(toScrollX, 0);
                startX = event.getX();
                if (swipeLeftAndRight(event)) {
                    //反拦截事件, 参数为true：将事件从父View分发下来
                    requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                //偏移量
                int totalScrollX = getScrollX();
                if (totalScrollX < itemMenu.getWidth() / 2) {
                    //关闭Menu
                    closeMenu();
                } else {
                    //打开Menu
                    openMenu();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private boolean swipeLeftAndRight(MotionEvent event) {
        float offsetX = Math.abs(event.getX() - downX);
        float offsetY = Math.abs(event.getY() - downY);
        return offsetX > offsetY && offsetX > MotionEvent.AXIS_TOUCH_MINOR;
    }

    public void openMenu() {
        // 这里的距离为 目标位置 - 当前视图的偏移
        int distanceX = itemMenu.getWidth() - getScrollX();
        scroller.startScroll(getScrollX(), 0, distanceX, 0, 300);
        invalidate();
        if (onStateChangeListener != null) {
            onStateChangeListener.onOpen(this);
        }
    }

    public void closeMenu() {
        int distanceX = 0 - getScrollX();
        scroller.startScroll(getScrollX(), 0, distanceX, 0, 300);
        invalidate();
        if (onStateChangeListener != null) {
            onStateChangeListener.onClose(this);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                if(onStateChangeListener != null){
                    onStateChangeListener.onDown(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(ev.getX() - startX) ;
                if (dx > MotionEvent.AXIS_TOUCH_MINOR) {
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return intercept;
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    public interface OnStateChangeListener {

        void onClose(SlideItem slideItem);

        void onOpen(SlideItem slideItem);

        void onDown(SlideItem slideItem);
    }
}
