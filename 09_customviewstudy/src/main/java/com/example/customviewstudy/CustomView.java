package com.example.customviewstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Author HeartBeats  on  2020/04/06 at 16:45
 * Email: 913305160@qq.com
 */
public class CustomView extends View {

    private static final String TAG = "CustomView";

    private Paint paint;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "开始绘制onDraw: ");
        canvas.drawColor(getResources().getColor(R.color.colorAccent,null));

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setARGB(100, 0, 0, 0);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int radius = 5; radius <= 75; radius += 5) {
                    Log.d(TAG, "run: radius==" + radius);
                    // canvas.drawCircle(150, 150, radius, paint);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        canvas.drawCircle(150, 150, 75, paint);

        paint.setStrokeWidth(150);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(50,50,paint);

        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(250,250,paint);

        paint.setColor(Color.RED);

        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path(); // 初始化 Path 对象
        // 使用 path 对图形进行描述（这段描述代码不必看懂）
        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);
        path.setFillType(Path.FillType.EVEN_ODD);
        path.close();
        canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成

        path.quadTo(400,300,600,400);
        canvas.drawPath(path,paint);
    }
}
