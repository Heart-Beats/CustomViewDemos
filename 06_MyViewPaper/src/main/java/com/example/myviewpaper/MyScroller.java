package com.example.myviewpaper;

import android.os.SystemClock;

/**
 * @author SYSTEM
 */
public class MyScroller {

    /**
     * X轴的起始坐标
     */
    private float startY;
    /**
     * Y轴的起始坐标
     */
    private float startX;

    /**
     * 在X轴要移动的距离
     */
    private int distanceX;
    /**
     * 在Y轴要移动的距离
     */
    private int distanceY;
    /**
     开始的时间
     */
    private long startTime;

    /**
     * 总时间
     */
    private long totalTime ;
    /**
     * 是否移动完成
     * false没有移动完成
     * true:移动结束
     */
    private boolean isFinish;
    /**
     * 当前视图在X的位置
     */
    private float currX;
    private float currY;

    /**
     * 得到坐标
     */
    public float getCurrX() {
        return currX;
    }

    public float getCurrY() {
        return currY;
    }

    /**
     *  设置滑动的参数
     * @param startX        开始时相对X轴在屏幕外的偏移
     * @param startY        开始时相对Y轴在屏幕外的偏移
     * @param distanceX     在X轴上滑动的距离， 从左往右为正，从右往左为负
     * @param distanceY     在Y轴上滑动的距离
     * @param totalTime     滑动的总时间
     *
     *        //getScrollX（）为子View相对X轴在屏幕外的偏移
     *        distanceX = 目标位置 - getScrollX（）
     *                如：目标 50    子View相对X轴在屏幕外的偏移：30 ，则此时它们之间的距离为20
     */
    public void startScroll(float startX, float startY, int distanceX, int distanceY,long totalTime) {
        this.startY = startY;
        this.startX = startX;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        //系统时间,相对系统开机的时间
        this.startTime = SystemClock.uptimeMillis();
        this.totalTime = totalTime;
        this.isFinish = false;
    }

    /**
     * 速度
     求移动一小段的距离
     求移动一小段对应的坐标
     求移动一小段对应的时间
     true:正在移动
     false:移动结束

     * @return 是否还在计算偏移
     */
    public boolean computeScrollOffset(){
        if(isFinish){
            return  false;
        }
        //因为该方法会被循环执行，所以endTime一直变化
        long endTime = SystemClock.uptimeMillis();

        //这一小段所花的时间
        long passTime = endTime - startTime;
        if(passTime < totalTime){
            //还没有移动结束
            //计算平均速度
//            float voleCity = distanceX/totalTime;
            //移动这个一小段对应的距离
            float distanceSmallX = passTime* distanceX/totalTime;
            float distanceSmallY = passTime* distanceY/totalTime;
            //当前视图的位置更新
            currX = startX + distanceSmallX;
            currY = startY + distanceSmallY;
        }else{
            //移动结束
            isFinish =true;
            currX = startX + distanceX;
            currY = startY + distanceY;
        }
      return  true;
    }
}
