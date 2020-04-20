package com.example.quickindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author SYSTEM  on  2018/07/30 at 23:27
 * 邮箱：913305160@qq.com
 */
public class IndexView extends View {

    private int itemWidth;
    private int itemHeight;
    private Paint paint;
    private Rect rect;
    /**
     * 字母的下标位置
     */
    private int touchIndex = -1;
    private OnIndexChangeListener indexChangeListener;

    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        rect = new Rect();
        paint = new Paint();
        paint.setColor(Color.WHITE);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置粗体字
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(15 * 3);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //注意：getMeasuredWidth()获取自身的宽高而不是widthMeasureSpec
        //           getMeasuredWidth() 相当于 MeasureSpec.getSize(widthMeasureSpec)
        itemWidth = getMeasuredWidth();
        itemHeight = getMeasuredHeight() / words.length;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = 0;
        for (String word : words) {
            if (touchIndex == i) {
                //设置灰色
                paint.setColor(Color.GRAY);
            } else {
                //设置白色
                paint.setColor(Color.WHITE);
            }
            //该方法得到文本边界,传入的矩形会获得大小
            paint.getTextBounds(word, 0, 1, rect);
            //字母的高和宽
            int wordWidth = rect.width();
            int wordHeight = rect.height();
            float wordX = (itemWidth - wordWidth) / 2;
            float wordY = (itemHeight + wordHeight) / 2 + i * itemHeight;
            canvas.drawText(word, wordX, wordY, paint);
            i++;
        }
    }


    /**
     * 手指按下文字变色
     * 1.重写onTouchEvent(),返回true,在down/move的过程中计算
     * int touchIndex = Y / itemHeight; 强制绘制
     * <p/>
     * 2.在onDraw()方法对于的下标设置画笔变色
     * <p/>
     * 3.在up的时候
     * touchIndex  = -1；
     * 强制绘制
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int startIndex = (int) (event.getY() / itemHeight);
                if (indexChangeListener != null && startIndex < words.length && startIndex > -1) {
                    indexChangeListener.onIndexChange(startIndex, words[startIndex]);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int index = (int) (event.getY() / itemHeight);
                if (index != touchIndex) {
                    touchIndex = index;
                    invalidate();
                    if (indexChangeListener != null && touchIndex < words.length && touchIndex > -1) {
                        indexChangeListener.onIndexChange(touchIndex, words[touchIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    public void setIndexChangeListener(OnIndexChangeListener indexChangeListener) {
        this.indexChangeListener = indexChangeListener;
    }

    public interface OnIndexChangeListener {
        /**
         * 手指移动下标改变后的回调方法
         *
         * @param index 改变后的下标
         * @param word  下标对应的字符
         */
        void onIndexChange(int index, String word);
    }
}
