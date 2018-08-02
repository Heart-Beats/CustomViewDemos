package com.example.autoattributes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author SYSTEM  on  2018/07/28 at 14:37
 * 邮箱：913305160@qq.com
 */
public class CustomViewAttribute extends View {

    private float density;
    private int myAge;
    private String myName;
    private Bitmap myBg;
    private Paint paint = new Paint();

    /**
     *
     * @param context  上下文
     * @param attrs   属性集合
     *
     *     自定义view中的属性步骤：
     *         1. 自定义类继承view，声明属性
     *         2. res -> values文件夹下新建 values resource file，命名attrs代表属性文件，
     *                  文件内容中声明对应的自定义view类：<declare-styleable name="CustomViewAttribute">
     *                  为元素添加attr节点，即声明布局文件中可以使用的属性及属性类型：
     *                       <attr name="my_name" format="string"/>
     *         3.  在自定义view类的构造方法View(Context context,AttributeSet attrs)
     *                中获取布局文件中属性集合，推荐使用系统工具获取：
     *                TypedArray typedArray = context.obtainStyledAttributes(attrs,
     *                       R.styleable.CustomViewAttribute);
     *                 并为自定义view类中的相应属性赋值。
     *         4. 自定义view类中的属性获取值后，根据需求绘制view
     *         5. 布局文件中引用该类即可，定义命名空间后即可设置自定义view中的属性
     *
     */

    public CustomViewAttribute(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //Metrics: 指标，度量标准
        density = context.getResources().getDisplayMetrics().density;

        //获取属性三种方式,前两种方法不推荐使用，使用第三种采用系统工具的方式

        //1.用命名空间取获取
        assert attrs != null;
        String age = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_age");
        String name = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_name");
        String bg = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_bg");
        // System.out.println("age=="+age+",name=="+name+",bg==="+bg);

        //2. 遍历属性集合
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            System.out.println(attrs.getAttributeName(i) + "=====" + attrs.getAttributeValue(i));
        }

        //3.使用系统工具，获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomViewAttribute);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.CustomViewAttribute_my_age:
                    myAge = typedArray.getInt(index, 0);
                    break;
                case R.styleable.CustomViewAttribute_my_name:
                    myName = typedArray.getString(index);
                    break;
                case R.styleable.CustomViewAttribute_my_bg:
                    Drawable drawable = typedArray.getDrawable(index);
                    BitmapDrawable drawable1 = (BitmapDrawable) drawable;
                    if (drawable1 != null) {
                        myBg = drawable1.getBitmap();
                    }
                    break;
                default:
                    break;
            }
        }
        // 记得回收
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) (myBg.getWidth() + 100 * density),
                (int) (myBg.getHeight() + 100 * density));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        canvas.drawBitmap(myBg, 50 * density, 50 * density, paint);
        paint.setARGB(127, 255, 0, 255);
        canvas.drawLine(50 * density, 40 * density, 50 * density + myBg.getWidth(),
                40 * density, paint);
        paint.setTextSize(20 * density);
        canvas.drawText(myName + ":" + myAge, 50 * density, 30 * density, paint);
    }
}
