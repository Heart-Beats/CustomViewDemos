package com.example.togglebutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author 淡然爱汝不离  on  2018/7/27  at  21:23
 * 邮箱：913305160@qq.com
 */
public class MyToggleButton extends View {

    private Bitmap backGroundBitmap;
    private Bitmap slideBitmap;
    /**
     * 距离左边最大距离
     */
    private int slidLeftMax;
    private Paint paint;
    private int slideLeftX;
    private boolean isOpen;

    private float startX;
    private float downX;

    /**
     * 如果我们在布局文件使用该类，将会用这个构造方法实例该类，如果没有就崩溃
     * @param context 上下文
     * @param attrs    布局中属性
     */
    public MyToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        backGroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        slideBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        slidLeftMax = backGroundBitmap.getWidth() - slideBitmap.getWidth();
        paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);
    }


    /**
     * 一个视图从创建到显示过程中的主要方法
     *  1.构造方法实例化类
     *  2.测量-measure(int,int)-->onMeasure();
     *      如果当前View是一个ViewGroup,还有义务测量孩子,孩子有建议权
     *  3.指定位置-layout()-->onLayout();
     *      指定控件的位置，一般View不需要重写该方法，ViewGroup的时候才需要重写该方法定义孩子位置
     *  4.绘制视图--draw()-->onDraw(canvas)
     *      根据上面两个方法参数，进入绘制，一般ViewGroup类型不需要重写该方法，交给孩子自己绘制视图
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = backGroundBitmap.getWidth();
        int height = backGroundBitmap.getHeight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(backGroundBitmap, 0, 0, paint);
        canvas.drawBitmap(slideBitmap, slideLeftX, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果没有该方法，正常的点击事件（onClick方法）则不会触发
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.记录按下的坐标
                downX =  startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //2.计算偏移量
                slideLeftX += event.getX() - startX;
                //3.屏蔽非法值
                slideLeftX = slideLeftX < 0 ? 0 : (slideLeftX > slidLeftMax ? slidLeftMax : slideLeftX);
                //4.刷新
                invalidate();
                //5.数据还原，这步很重要，如果没有会导致偏移量出问题
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                // 按下到松开大于最小距离，此时为滑动
                if (Math.abs(event.getX() - downX) > MotionEvent.AXIS_TOUCH_MINOR) {
                    slideLeftX = slideLeftX > slidLeftMax / 2 ? slidLeftMax : 0;
                    invalidate();
                } else {
                    // 执行点击事件
                    performClick();
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 当有触摸事件或者触摸监听时重写该方法，该方法为点击事件监听，
     *      但可以在触摸事件中定义何时执行该方法
     * @return  布尔类型返回值
     */
    @Override
    public boolean performClick() {
        isOpen = !isOpen;
        slideLeftX = isOpen ? slidLeftMax : 0;
        invalidate();
        return super.performClick();
    }
}
