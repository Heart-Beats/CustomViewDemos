package com.example.customviewstudy.ui.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.customviewstudy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_DRAW_TYPE = "section_draw_type";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index, String drawType) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString(ARG_SECTION_DRAW_TYPE, drawType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_practice, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final ConstraintLayout parent = root.findViewById(R.id.constraintLayout);
        View drawView = null;
        if (getArguments() != null) {
            String drawType = getArguments().getString(ARG_SECTION_DRAW_TYPE);
            if (drawType != null) {
                Log.d("Tag", "onCreateView: drawType==" + drawType);

                final Paint paint = new Paint();
                final Path path = new Path();
                switch (drawType) {
                    case "drawColor":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                canvas.drawColor(Color.argb(100, 50, 200, 200));
                            }
                        };
                        break;
                    case "drawCircle":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                paint.setColor(Color.BLACK);
                                paint.setStyle(Paint.Style.FILL);
                                canvas.drawCircle(400, 400, 100, paint);

                                paint.setStyle(Paint.Style.STROKE);
                                canvas.drawCircle(600, 400, 100, paint);

                                paint.setColor(Color.BLUE);
                                paint.setStrokeCap(Paint.Cap.ROUND);
                                paint.setStrokeWidth(200);
                                canvas.drawPoint(400, 600, paint);

                                paint.setStrokeWidth(40);
                                paint.setColor(Color.BLACK);
                                canvas.drawCircle(600, 600, 80, paint);
                            }
                        };
                        break;
                    case "drawRect":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                paint.setStrokeWidth(10);

                                paint.setColor(Color.BLACK);
                                paint.setStyle(Paint.Style.FILL);
                                canvas.drawRect(300, 300, 500, 500, paint);

                                paint.setStyle(Paint.Style.STROKE);
                                canvas.drawRect(600, 300, 800, 500, paint);

                                paint.setColor(Color.BLUE);
                                paint.setStrokeCap(Paint.Cap.ROUND);
                                paint.setStyle(Paint.Style.FILL);
                                canvas.drawRoundRect(300, 600, 500, 800, 50, 50, paint);

                                paint.setStrokeWidth(20);
                                paint.setColor(Color.BLACK);
                                paint.setStyle(Paint.Style.STROKE);
                                canvas.drawRoundRect(600, 600, 800, 800, 50, 50, paint);
                            }
                        };
                        break;
                    case "drawArc":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                paint.setStrokeWidth(10);

                                paint.setColor(Color.BLACK);
                                paint.setStyle(Paint.Style.FILL);
                                canvas.drawArc(300, 300, 500, 500, -150, 120, true, paint);

                                paint.setStyle(Paint.Style.STROKE);
                                canvas.drawArc(600, 300, 800, 500, -150, 120, true, paint);

                                paint.setColor(Color.BLUE);
                                paint.setStrokeCap(Paint.Cap.ROUND);
                                paint.setStyle(Paint.Style.FILL);
                                canvas.drawArc(300, 600, 500, 800, -150, 120, false, paint);

                                paint.setColor(Color.BLACK);
                                paint.setStyle(Paint.Style.STROKE);
                                canvas.drawArc(600, 600, 800, 800, -150, 120, false, paint);
                            }
                        };
                        break;
                    case "drawPath":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                paint.setStrokeWidth(5);
                                paint.setColor(Color.RED);
                                paint.setStyle(Paint.Style.STROKE);

                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();
                                canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成

                                path.quadTo(400, 300, 570.7f, 370.7f);
                                canvas.drawPath(path, paint);
                            }
                        };
                        break;
                    case "直方图":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);

                            }
                        };
                        break;
                    case "饼图":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                parent.setBackgroundColor(Color.GRAY);
                                canvas.drawColor(Color.argb(100, 50, 200, 200));

                                RectF baseRectF = new RectF(200, 200, 800, 800);
                                paint.setStyle(Paint.Style.FILL);
                                paint.setColor(Color.rgb(244, 66, 54));
                                canvas.drawArc(baseRectF, -180, 120, true, paint);

                                RectF otherRectF = new RectF(220, 220, 820, 820);
                                paint.setColor(Color.rgb(253, 193, 7));
                                canvas.drawArc(otherRectF, -45, 45, true, paint);

                                paint.setColor(Color.rgb(149, 44, 170));
                                canvas.drawArc(otherRectF, 0, 10, true, paint);

                                paint.setColor(Color.rgb(152, 158, 164));
                                canvas.drawArc(otherRectF, 15, 10, true, paint);

                                paint.setColor(Color.rgb(2, 150, 136));
                                canvas.drawArc(otherRectF, 30, 45, true, paint);

                                paint.setColor(Color.rgb(33, 150, 243));
                                canvas.drawArc(otherRectF, 80, 100, true, paint);

                                paint.setColor(Color.rgb(250, 250, 250));
                                paint.setStyle(Paint.Style.STROKE);

                                paint.setTextSize(40);
                                path.moveTo(100, 250);
                                path.lineTo(250, 250);
                                path.lineTo(288, 288);
                                canvas.drawPath(path, paint);

                                paint.setStyle(Paint.Style.FILL);
                                canvas.drawText("红色", 50, 250, paint);

                                canvas.drawLine(782, 398, 810, 370, paint);
                                canvas.drawLine(810, 370, 910, 370, paint);
                                canvas.drawText("黄色", 920, 370, paint);

                                paint.setTextSize(60);
                                canvas.drawText("饼图", 480, 900, paint);
                                paint.setStrokeWidth(10);
                                canvas.drawLine(0, 990, 1000, 1000, paint);
                            }
                        };
                        break;
                    case "LinearGradient":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                paint.setStrokeWidth(5);
                                paint.setColor(Color.RED);
                                paint.setStyle(Paint.Style.FILL_AND_STROKE);

                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();
                                canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成

                                Shader linearGradient = new LinearGradient(200, 200, 600, 542, Color.parseColor("#E91E63"),
                                        Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
                                paint.setShader(linearGradient);
                                canvas.drawPath(path, paint);
                            }
                        };
                        break;
                    case "RadialGradient":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                paint.setStrokeWidth(5);
                                paint.setColor(Color.RED);
                                paint.setStyle(Paint.Style.FILL_AND_STROKE);

                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();
                                canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成

                                Shader radialGradient = new RadialGradient(400, 400, 200, Color.parseColor("#E91E63"),
                                        Color.parseColor("#2196F3"), Shader.TileMode.MIRROR);
                                paint.setShader(radialGradient);
                                canvas.drawPath(path, paint);
                            }
                        };
                        break;
                    case "SweepGradient":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                paint.setStrokeWidth(5);
                                paint.setColor(Color.RED);
                                paint.setStyle(Paint.Style.FILL_AND_STROKE);

                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();
                                canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成

                                Shader sweepGradient = new SweepGradient(400, 400, Color.parseColor("#E91E63"),
                                        Color.parseColor("#2196F3"));
                                paint.setShader(sweepGradient);
                                canvas.drawPath(path, paint);
                            }
                        };
                        break;
                    case "setXferMode":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                canvas.drawColor(Color.GRAY);

                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cloud);
                                Bitmap cloudBitmap = getBitmapByWH(bitmap, 250, 250);
                                canvas.drawBitmap(cloudBitmap, 50, 50, paint);

                                canvas.drawBitmap(cloudBitmap, 450, 50, paint);
                                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.landscape_night);
                                Bitmap landscapeNightBitmap = getBitmapByWH(bitmap, 480, 300);

                                //设置离屏缓冲
                                int saveLayer = canvas.saveLayer(null, null);
                                Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);
                                paint.setXfermode(xfermode);
                                canvas.drawBitmap(landscapeNightBitmap, 450, 50, paint);
                                canvas.restoreToCount(saveLayer);
                                paint.setXfermode(null);

                                canvas.drawBitmap(cloudBitmap, 50, 400, paint);

                                saveLayer = canvas.saveLayer(null, null);
                                xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OVER);
                                paint.setXfermode(xfermode);
                                canvas.drawBitmap(landscapeNightBitmap, 50, 400, paint);
                                canvas.restoreToCount(saveLayer);
                            }
                        };
                        break;
                    case "setPathEffect":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                Path smallPath = new Path();
                                smallPath.addArc(20, 20, 40, 40, -225, 225);
                                smallPath.arcTo(40, 20, 60, 40, -180, 225, false);
                                smallPath.lineTo(40, 54.2f);
                                smallPath.setFillType(Path.FillType.EVEN_ODD);
                                smallPath.close();

                                PathEffect pathEffect = new PathDashPathEffect(smallPath, 50, 10, PathDashPathEffect.Style.TRANSLATE);
                                paint.setPathEffect(pathEffect);

                                paint.setStrokeWidth(5);
                                paint.setColor(Color.RED);
                                paint.setStyle(Paint.Style.FILL_AND_STROKE);

                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();
                                canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成
                            }
                        };
                        break;
                    case "setMaskFilter":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);

                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.landscape_night);
                                Bitmap landscapeNightBitmap = getBitmapByWH(bitmap, 480, 300);

                                paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
                                canvas.drawBitmap(landscapeNightBitmap, 20, 20, paint);

                                paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER));
                                canvas.drawBitmap(landscapeNightBitmap, 520, 20, paint);

                                paint.setMaskFilter(new EmbossMaskFilter(new float[]{100, 100, 100}, 0.5f, 5, 50));
                                canvas.drawBitmap(landscapeNightBitmap, 20, 340, paint);

                                paint.setMaskFilter(new EmbossMaskFilter(new float[]{0, 1, 1}, 0.2f, 8, 50));
                                canvas.drawBitmap(landscapeNightBitmap, 520, 340, paint);
                            }
                        };
                        break;
                    case "getFillPath":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);

                                paint.setStrokeWidth(20);
                                paint.setColor(Color.RED);
                                paint.setStyle(Paint.Style.STROKE);

                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();
                                // canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成
                                Path dstPath = new Path();
                                paint.getFillPath(path, dstPath);
                                canvas.drawPath(dstPath, paint);

                            }
                        };
                        break;
                    case "setTypeface":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                String text = "Hello World !";

                                paint.reset();
                                paint.setStyle(Paint.Style.STROKE);
                                paint.setTextSize(60);
                                paint.setFakeBoldText(true);
                                canvas.drawText(text, 100, 300, paint);

                                paint.setTypeface(Typeface.MONOSPACE);
                                canvas.drawText(text, 100, 300 + paint.getFontSpacing() * 1, paint);

                                paint.setTypeface(Typeface.SERIF);
                                canvas.drawText(text, 100, 300 + paint.getFontSpacing() * 2, paint);
                            }
                        };
                        break;
                    case "measureText":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                String text1 = "三个月内你胖了";
                                String text2 = "4.5";
                                String text3 = "公斤";

                                paint.reset();
                                paint.setTextSize(60);
                                paint.setStrokeWidth(10);
                                Paint paint2 = new Paint();
                                paint2.setColor(Color.RED);
                                paint2.setTextSize(120);

                                canvas.drawText(text1, 200, 200, paint);
                                float measureText1 = paint.measureText(text1);
                                float measureText2 = paint2.measureText(text2);
                                canvas.drawText(text2, 200 + measureText1, 200, paint2);
                                canvas.drawText(text3, 200 + measureText1 + measureText2, 200, paint);

                                canvas.drawText(text1, 200, 400, paint);
                                canvas.drawText(text2, measureText1, 400, paint2);
                                canvas.drawText(text3, 200 + measureText1, 400, paint);
                            }
                        };
                        break;
                    case "getFontMetrics":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                String[] texts = {"A", "a", "J", "j", "Â", "â"};
                                int top = 200;
                                int bottom = 400;

                                paint.setStyle(Paint.Style.STROKE);
                                paint.setStrokeWidth(20);
                                paint.setColor(Color.parseColor("#E91E63"));
                                canvas.drawRect(100, top, getWidth() - 100, bottom, paint);
                                Paint paint2 = new Paint();
                                paint2.setTextSize(120);
                                for (int i = 0; i < texts.length; i++) {
                                    canvas.drawText(texts[i], 100 * (i + 1), (top + bottom) / 2, paint2);
                                }

                                canvas.drawRect(100, bottom + 100, getWidth() - 100, 2 * bottom + 100 - top, paint);
                                Paint.FontMetrics fontMetrics = paint2.getFontMetrics();
                                //ascent为负值，descent为正值，且|ascent|>|descent|
                                float yOffset = -(fontMetrics.ascent + fontMetrics.descent) / 2;
                                for (int i = 0; i < texts.length; i++) {
                                    canvas.drawText(texts[i], 100 * (i + 1), (3 * bottom + 200 - top) / 2 + yOffset, paint2);
                                }

                                canvas.save();
                                canvas.restore();
                            }
                        };
                        break;
                    case "clipPath":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.google_maps);
                                Bitmap newBitmap = getBitmapByScaleXY(bitmap, 0.5, 0.5);

                                canvas.save();
                                path.reset();
                                float width = (bitmap.getWidth() + 200) / 2 + 50;
                                float height = (bitmap.getHeight() + 200) / 2 + 50;
                                path.addCircle(width, height, 200, Path.Direction.CW);
                                canvas.clipPath(path);
                                canvas.drawBitmap(newBitmap, 200, 200, paint);
                                canvas.restore();

                                canvas.save();
                                path.reset();
                                float width2 = 200 + bitmap.getWidth() / 2 - 50;
                                float height2 = 500 + bitmap.getHeight() / 2 - 50;
                                path.addCircle(width2, height2, 200, Path.Direction.CW);
                                canvas.clipOutPath(path);
                                canvas.drawBitmap(newBitmap, 200, 500, paint);
                                canvas.restore();
                            }
                        };
                        break;
                    case "rotate":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.google_maps);
                                Bitmap newBitmap = getBitmapByScaleXY(bitmap, 0.5, 0.5);

                                canvas.save();
                                float rotateX = newBitmap.getWidth() / 2 + 200;
                                float rotateY = newBitmap.getHeight() / 2 + 200;
                                canvas.rotate(180, rotateX, rotateY);
                                canvas.drawBitmap(newBitmap, 200, 200, paint);
                                canvas.restore();

                                canvas.save();
                                float rotateX2 = newBitmap.getWidth() / 2 + 600;
                                float rotateY2 = newBitmap.getHeight() / 2 + 200;
                                canvas.rotate(45, rotateX2, rotateY2);
                                canvas.drawBitmap(newBitmap, 600, 200, paint);
                                canvas.restore();
                            }
                        };
                        break;
                    case "翻页效果":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.google_maps);
                                Bitmap newBitmap = getBitmapByScaleXY(bitmap, 0.5, 0.5);

                                float locationX = 500;
                                float locationY = 500;
                                Rect clipRect = new Rect((int) locationX, (int) locationY, (int) locationX + newBitmap.getWidth(), (int) locationY + newBitmap.getHeight() / 2);
                                canvas.save();
                                canvas.clipRect(clipRect);
                                canvas.drawBitmap(newBitmap, locationX, locationY, paint);
                                canvas.restore();

                                float translateX = -(locationX + newBitmap.getWidth() / 2);
                                float translateY = -(locationY + newBitmap.getHeight() / 2);

                                canvas.save();
                                Camera camera = new Camera();
                                camera.save();
                                camera.rotateX(60);
                                canvas.translate(-translateX, -translateY); //// 旋转之后把投影移动回来
                                camera.applyToCanvas(canvas);           //// 把旋转投影到 Canvas
                                canvas.translate(translateX, translateY);  // 旋转之前把绘制内容移动到轴心（原点）
                                camera.restore();

                                canvas.clipOutRect(clipRect);
                                canvas.drawBitmap(newBitmap, locationX, locationY, paint);

                                canvas.restore();
                            }
                        };
                        break;
                    case "translationX/Y/Z":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.google_maps);
                                Bitmap newBitmap = getBitmapByScaleXY(bitmap, 0.5, 0.5);

                                canvas.drawBitmap(newBitmap, 200, 200, paint);
                            }
                        };

                        final View finalDrawView = drawView;

                        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.topToTop = R.id.constraintLayout;
                        layoutParams.bottomToBottom = R.id.constraintLayout;
                        layoutParams.leftToLeft = R.id.constraintLayout;
                        layoutParams.rightToRight = R.id.translateZ;

                        Button buttonLeftX = new Button(getContext());
                        buttonLeftX.setId(R.id.translate_left_X);
                        buttonLeftX.setText("向左");
                        buttonLeftX.setAllCaps(false);
                        buttonLeftX.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalDrawView.animate().translationXBy(-100);
                            }
                        });

                        ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams2.topToTop = R.id.constraintLayout;
                        layoutParams2.bottomToBottom = R.id.constraintLayout;
                        layoutParams2.rightToRight = R.id.constraintLayout;
                        layoutParams2.leftToLeft = R.id.translateZ;
                        Button buttonRightX = new Button(getContext());
                        buttonRightX.setId(R.id.translate_right_X);
                        buttonRightX.setText("向右");
                        buttonRightX.setAllCaps(false);
                        buttonRightX.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalDrawView.animate().translationXBy(100);
                            }
                        });

                        ConstraintLayout.LayoutParams layoutParams3 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams3.topToTop = R.id.constraintLayout;
                        layoutParams3.bottomToBottom = R.id.translateZ;
                        layoutParams3.leftToLeft = R.id.constraintLayout;
                        layoutParams3.rightToRight = R.id.constraintLayout;
                        Button buttonTopY = new Button(getContext());
                        buttonTopY.setId(R.id.translate_top_Y);
                        buttonTopY.setText("向上");
                        buttonTopY.setAllCaps(false);
                        buttonTopY.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalDrawView.animate().translationYBy(-100);
                            }
                        });

                        ConstraintLayout.LayoutParams layoutParams4 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams4.topToTop = R.id.translateZ;
                        layoutParams4.bottomToBottom = R.id.constraintLayout;
                        layoutParams4.leftToLeft = R.id.constraintLayout;
                        layoutParams4.rightToRight = R.id.constraintLayout;
                        Button buttonBottomY = new Button(getContext());
                        buttonBottomY.setId(R.id.translate_bottom_Y);
                        buttonBottomY.setText("向下");
                        buttonBottomY.setAllCaps(false);
                        buttonBottomY.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalDrawView.animate().translationYBy(100);
                            }
                        });

                        ConstraintLayout.LayoutParams layoutParams5 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams5.topToTop = R.id.constraintLayout;
                        layoutParams5.leftToLeft = R.id.constraintLayout;
                        layoutParams5.rightToRight = R.id.constraintLayout;
                        layoutParams5.bottomToBottom = R.id.constraintLayout;

                        layoutParams5.leftMargin = 50;
                        layoutParams5.rightMargin = 50;

                        Button buttonZ = new Button(getContext());
                        buttonZ.setId(R.id.translateZ);
                        buttonZ.setText("向前");
                        buttonZ.setAllCaps(false);
                        buttonZ.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalDrawView.animate().translationZBy(100);
                            }
                        });

                        parent.addView(buttonLeftX, layoutParams);
                        parent.addView(buttonRightX, layoutParams2);
                        parent.addView(buttonTopY, layoutParams3);
                        parent.addView(buttonBottomY, layoutParams4);
                        parent.addView(buttonZ, layoutParams5);
                        break;
                    case "rotationX/Y":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.google_maps);
                                Bitmap newBitmap = getBitmapByScaleXY(bitmap, 0.5, 0.5);

                                canvas.drawBitmap(newBitmap, 200, 200, paint);
                            }
                        };

                        final View finalDrawView1 = drawView;

                        ConstraintLayout.LayoutParams layoutParams6 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams6.topToTop = R.id.constraintLayout;
                        layoutParams6.bottomToBottom = R.id.constraintLayout;
                        layoutParams6.leftToLeft = R.id.constraintLayout;
                        layoutParams6.rightToRight = R.id.rotationY;

                        Button rotationX = new Button(getContext());
                        rotationX.setId(R.id.rotationX);
                        rotationX.setText("rotationX");
                        rotationX.setAllCaps(false);
                        rotationX.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalDrawView1.animate().rotationXBy(45);
                            }
                        });

                        ConstraintLayout.LayoutParams layoutParams7 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams7.topToTop = R.id.constraintLayout;
                        layoutParams7.bottomToBottom = R.id.constraintLayout;
                        layoutParams7.rightToRight = R.id.constraintLayout;
                        layoutParams7.leftToLeft = R.id.rotationX;
                        Button rotationY = new Button(getContext());
                        rotationY.setId(R.id.rotationY);
                        rotationY.setText("rotationY");
                        rotationY.setAllCaps(false);
                        rotationY.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalDrawView1.animate().rotationYBy(45);
                            }
                        });

                        parent.addView(rotationX, layoutParams6);
                        parent.addView(rotationY, layoutParams7);
                        break;
                    case "ViewPropertyAnimator-多属性":
                        drawView = new View(getContext()) {
                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.google_maps);
                                Bitmap newBitmap = getBitmapByScaleXY(bitmap, 0.5, 0.5);

                                canvas.drawBitmap(newBitmap, 200, 200, paint);
                            }
                        };

                        final View finalDrawView2 = drawView;

                        ConstraintLayout.LayoutParams layoutParams8 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams8.topToTop = R.id.constraintLayout;
                        layoutParams8.bottomToBottom = R.id.constraintLayout;
                        layoutParams8.leftToLeft = R.id.constraintLayout;
                        layoutParams8.rightToRight = R.id.constraintLayout;

                        Button viewPropertyAnimatorButton = new Button(getContext());
                        viewPropertyAnimatorButton.setText("动画结合");
                        viewPropertyAnimatorButton.setAllCaps(false);
                        viewPropertyAnimatorButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalDrawView2.animate()
                                        .translationXBy(200)
                                        .translationYBy(100)
                                        .rotationXBy(15)
                                        .alphaBy(1f)
                                        // .scaleXBy(1.5f)
                                        // .scaleYBy(1.5f)
                                        .setDuration(2000)
                                        .setInterpolator(new OvershootInterpolator())
                                        .setListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {
                                                Toast.makeText(getContext(), "动画开始", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                Toast.makeText(getContext(), "动画结束", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animation) {

                                            }
                                        });
                            }
                        });
                        parent.addView(viewPropertyAnimatorButton, layoutParams8);
                        break;
                    case " ObjectAnimator":
                        drawView = new View(getContext()) {

                            private int percent;
                            private float pathMeasureLength;
                            private float actualDrawLength;
                            private PathMeasure pathMeasure;

                            {
                                path.reset();
                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();

                                pathMeasure = new PathMeasure(path, false);
                                pathMeasureLength = pathMeasure.getLength();
                            }

                            public int getPercent() {
                                return percent;
                            }

                            public void setPercent(int percent) {
                                this.percent = percent;
                                this.actualDrawLength = pathMeasureLength * percent / 100;
                                invalidate(); //强行绘制，执行draw()方法
                            }

                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                float[] position = new float[2];
                                float[] tan = new float[2];
                                pathMeasure.getPosTan(actualDrawLength, position, tan);

                                paint.reset();
                                paint.setColor(Color.RED);
                                paint.setShader(new LinearGradient(200, 200, 600, 542, Color.RED, Color.BLUE, Shader.TileMode.CLAMP));
                                paint.setStyle(Paint.Style.STROKE);
                                canvas.drawPath(path, paint);

                                paint.setStrokeCap(Paint.Cap.ROUND);
                                paint.setStrokeWidth(20);
                                canvas.drawPoint(position[0], position[1], paint);

                                //已知一点的正切值，求固定距离的另一点
                                float distance = 100;
                                float tanValue = tan[1] / tan[0];
                                float x2 = (float) (position[0] + distance / (Math.sqrt(tanValue * tanValue + 1)));
                                float y2 = (float) (position[1] + distance * tanValue / (Math.sqrt(tanValue * tanValue + 1)));

                                float x3 = 2 * position[0] - x2;
                                float y3 = 2 * position[1] - y2;

                                float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
/*                                if (90 <= degrees && degrees <= 270) {
                                    y2 = (float) (position[1] + distance * tanValue / (Math.sqrt(tanValue * tanValue + 1)));
                                } else if (0 <= degrees && degrees < 90 || 270 < degrees && degrees <= 360) {
                                    y2 = (float) (position[1]- distance * tanValue / (Math.sqrt(tanValue * tanValue + 1)));
                                }*/

                                Log.d("TAG", "onDraw: x2==" + x2 + ", y2==" + y2);
                                paint.setStrokeWidth(5);
                                canvas.drawLine(x2, y2, x3, y3, paint);

                                paint.setStyle(Paint.Style.FILL);
                                paint.setTextSize(40);
                                String animationPercent = "已完成" + percent;
                                float measureText = paint.measureText(animationPercent);
                                canvas.drawText(animationPercent, 400 - measureText / 2, (400 / 2 + 542) / 2, paint);

                            }
                        };

                        final View finalDrawView3 = drawView;

                        ConstraintLayout.LayoutParams layoutParams9 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams9.topToTop = R.id.constraintLayout;
                        layoutParams9.bottomToBottom = R.id.constraintLayout;
                        layoutParams9.leftToLeft = R.id.constraintLayout;
                        layoutParams9.rightToRight = R.id.constraintLayout;

                        Button objectAnimatorButton = new Button(getContext());
                        objectAnimatorButton.setText("自定义动画");
                        objectAnimatorButton.setAllCaps(false);
                        objectAnimatorButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(finalDrawView3, "percent", 0, 100);
                                objectAnimator.setDuration(5000);
                                objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
                                //线性插值器解决动画重复执行时卡顿问题，默认先加速再减速导致卡顿
                                objectAnimator.setInterpolator(new LinearInterpolator());
                                objectAnimator.start();
                            }
                        });
                        parent.addView(objectAnimatorButton, layoutParams9);
                        break;
                    case "ArgbEvaluator":
                        drawView = new View(getContext()) {

                            private int color = 0xFFFF0000;

                            {
                                path.reset();
                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();
                            }

                            public int getColor() {
                                return color;
                            }

                            public void setColor(int color) {
                                this.color = color;
                                invalidate(); //强行绘制，执行draw()方法
                            }

                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);

                                paint.reset();
                                paint.setColor(Color.RED);
                                paint.setShader(new LinearGradient(200, 200, 600, 542, color, Color.BLUE, Shader.TileMode.CLAMP));
                                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                                canvas.drawPath(path, paint);

                            }
                        };

                        final View finalDrawView4 = drawView;

                        ConstraintLayout.LayoutParams layoutParams10 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams10.topToTop = R.id.constraintLayout;
                        layoutParams10.bottomToBottom = R.id.constraintLayout;
                        layoutParams10.leftToLeft = R.id.constraintLayout;
                        layoutParams10.rightToRight = R.id.constraintLayout;

                        Button objectAnimatorButton2 = new Button(getContext());
                        objectAnimatorButton2.setText("颜色渐变动画");
                        objectAnimatorButton2.setAllCaps(false);
                        objectAnimatorButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ObjectAnimator objectAnimator = ObjectAnimator.ofArgb(finalDrawView4, "color", 0xFFFF0000, 0x00FF00);
                                objectAnimator.setDuration(5000);
                                objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
                                //线性插值器解决动画重复执行时卡顿问题，默认先加速再减速导致卡顿
                                objectAnimator.setInterpolator(new LinearInterpolator());
                                objectAnimator.start();
                            }
                        });
                        parent.addView(objectAnimatorButton2, layoutParams10);
                        break;
                    case "自定义Evaluator":
                        drawView = new View(getContext()) {

                            private int percent;
                            private float pathMeasureLength;
                            private float actualDrawLength;
                            private PathMeasure pathMeasure;

                            {
                                path.reset();
                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();

                                pathMeasure = new PathMeasure(path, false);
                                pathMeasureLength = pathMeasure.getLength();
                            }

                            public int getPercent() {
                                return percent;
                            }

                            public void setPercent(int percent) {
                                this.percent = percent;
                                this.actualDrawLength = pathMeasureLength * percent / 100;
                                invalidate(); //强行绘制，执行draw()方法
                            }

                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                float[] position = new float[2];
                                float[] tan = new float[2];
                                pathMeasure.getPosTan(actualDrawLength, position, tan);

                                paint.reset();
                                paint.setColor(Color.RED);
                                paint.setShader(new LinearGradient(200, 200, 600, 542, Color.RED, Color.BLUE, Shader.TileMode.CLAMP));
                                paint.setStyle(Paint.Style.STROKE);
                                canvas.drawPath(path, paint);

                                paint.setStrokeCap(Paint.Cap.ROUND);
                                paint.setStrokeWidth(20);
                                canvas.drawPoint(position[0], position[1], paint);

                                //已知一点的正切值，求固定距离的另一点
                                float distance = 100;
                                float tanValue = tan[1] / tan[0];
                                float x2 = (float) (position[0] + distance / (Math.sqrt(tanValue * tanValue + 1)));
                                float y2 = (float) (position[1] + distance * tanValue / (Math.sqrt(tanValue * tanValue + 1)));

                                float x3 = 2 * position[0] - x2;
                                float y3 = 2 * position[1] - y2;

                                paint.setStrokeWidth(5);
                                canvas.drawLine(x2, y2, x3, y3, paint);

                                paint.setStyle(Paint.Style.FILL);
                                paint.setTextSize(40);
                                String animationPercent = "已完成" + percent;
                                float measureText = paint.measureText(animationPercent);
                                canvas.drawText(animationPercent, 400 - measureText / 2, (400 / 2 + 542) / 2, paint);

                            }
                        };

                        final View finalDrawView5 = drawView;

                        ConstraintLayout.LayoutParams layoutParams11 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams11.topToTop = R.id.constraintLayout;
                        layoutParams11.bottomToBottom = R.id.constraintLayout;
                        layoutParams11.leftToLeft = R.id.constraintLayout;
                        layoutParams11.rightToRight = R.id.constraintLayout;

                        Button objectAnimatorButton3 = new Button(getContext());
                        objectAnimatorButton3.setText("自定义Evaluator");
                        objectAnimatorButton3.setAllCaps(false);
                        objectAnimatorButton3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(finalDrawView5, "percent", 0, 100);
                                objectAnimator.setDuration(5000);
                                objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
                                //线性插值器解决动画重复执行时卡顿问题，默认先加速再减速导致卡顿
                                objectAnimator.setInterpolator(new LinearInterpolator());
                                objectAnimator.setEvaluator(new TypeEvaluator<Integer>() {
                                    @Override
                                    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                                        return (int) (startValue + (endValue - startValue) * fraction) + 100;
                                    }
                                });
                                objectAnimator.start();
                            }
                        });
                        parent.addView(objectAnimatorButton3, layoutParams11);
                        break;
                    case "ofObject()":
                        drawView = new View(getContext()) {

                            private int color = 0xFFFF0000;
                            private PointF centerPoint = new PointF();

                            public PointF getCenterPoint() {
                                return centerPoint;
                            }

                            public void setCenterPoint(PointF centerPoint) {
                                this.centerPoint = centerPoint;
                                invalidate();
                            }

                            {
                                path.reset();
                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();
                            }

                            public int getColor() {
                                return color;
                            }

                            public void setColor(int color) {
                                this.color = color;
                                invalidate(); //强行绘制，执行draw()方法
                            }

                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                canvas.translate(centerPoint.x, centerPoint.y);

                                paint.reset();
                                paint.setColor(Color.RED);
                                paint.setShader(new LinearGradient(200, 200, 600, 542, color, Color.BLUE, Shader.TileMode.CLAMP));
                                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                                canvas.drawPath(path, paint);

                            }
                        };

                        final View finalDrawView6 = drawView;

                        ConstraintLayout.LayoutParams layoutParams12 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams12.topToTop = R.id.constraintLayout;
                        layoutParams12.bottomToBottom = R.id.constraintLayout;
                        layoutParams12.leftToLeft = R.id.constraintLayout;
                        layoutParams12.rightToRight = R.id.constraintLayout;

                        Button objectAnimatorButton4 = new Button(getContext());
                        objectAnimatorButton4.setText("ofObject()动画");
                        objectAnimatorButton4.setAllCaps(false);
                        objectAnimatorButton4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ObjectAnimator objectAnimator = ObjectAnimator.ofObject(finalDrawView6, "centerPoint", new PointFEvaluator(),
                                        new PointF(0, 0), new PointF(100, 100));
                                objectAnimator.setDuration(5000);
                                objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
                                //线性插值器解决动画重复执行时卡顿问题，默认先加速再减速导致卡顿
                                objectAnimator.setInterpolator(new LinearInterpolator());
                                objectAnimator.start();
                            }
                        });
                        parent.addView(objectAnimatorButton4, layoutParams12);
                        break;
                    case "PropertyValuesHolder":
                        drawView = new View(getContext()) {

                            private int color = 0xFFFF0000;
                            private PointF centerPoint = new PointF();

                            public PointF getCenterPoint() {
                                return centerPoint;
                            }

                            public void setCenterPoint(PointF centerPoint) {
                                this.centerPoint = centerPoint;
                                invalidate();
                            }

                            public int getColor() {
                                return color;
                            }

                            public void setColor(int color) {
                                this.color = color;
                                invalidate(); //强行绘制，执行draw()方法
                            }

                            {
                                path.reset();
                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();
                            }

                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                canvas.translate(centerPoint.x, centerPoint.y);

                                paint.reset();
                                paint.setColor(Color.RED);
                                paint.setShader(new LinearGradient(200, 200, 600, 542, color, Color.BLUE, Shader.TileMode.CLAMP));
                                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                                canvas.drawPath(path, paint);

                            }
                        };

                        final View finalDrawView7 = drawView;

                        ConstraintLayout.LayoutParams layoutParams13 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams13.topToTop = R.id.constraintLayout;
                        layoutParams13.bottomToBottom = R.id.constraintLayout;
                        layoutParams13.leftToLeft = R.id.constraintLayout;
                        layoutParams13.rightToRight = R.id.constraintLayout;

                        Button objectAnimatorButton5 = new Button(getContext());
                        objectAnimatorButton5.setText("PropertyValuesHolder");
                        objectAnimatorButton5.setAllCaps(false);
                        objectAnimatorButton5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofObject("centerPoint", new PointFEvaluator(),
                                        new PointF(0, 0), new PointF(100, 100));
                                PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofObject("color", new ArgbEvaluator(), 0xFFFF0000, 0x00FF00);
                                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(finalDrawView7, propertyValuesHolder1, propertyValuesHolder2);
                                objectAnimator.setDuration(5000);
                                objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
                                //线性插值器解决动画重复执行时卡顿问题，默认先加速再减速导致卡顿
                                objectAnimator.setInterpolator(new LinearInterpolator());
                                objectAnimator.start();
                            }
                        });
                        parent.addView(objectAnimatorButton5, layoutParams13);
                        break;
                    case "ofKeyframe()":
                        drawView = new View(getContext()) {

                            private float percent;
                            private float pathMeasureLength;
                            private float actualDrawLength;
                            private PathMeasure pathMeasure;

                            {
                                path.reset();
                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();

                                pathMeasure = new PathMeasure(path, false);
                                pathMeasureLength = pathMeasure.getLength();
                            }

                            public float getPercent() {
                                return percent;
                            }

                            public void setPercent(float percent) {
                                this.percent = percent;
                                this.actualDrawLength = pathMeasureLength * percent / 100;
                                invalidate(); //强行绘制，执行draw()方法
                            }

                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                float[] position = new float[2];
                                float[] tan = new float[2];
                                pathMeasure.getPosTan(actualDrawLength, position, tan);

                                paint.reset();
                                paint.setColor(Color.RED);
                                paint.setShader(new LinearGradient(200, 200, 600, 542, Color.RED, Color.BLUE, Shader.TileMode.CLAMP));
                                paint.setStyle(Paint.Style.STROKE);
                                canvas.drawPath(path, paint);

                                paint.setStrokeCap(Paint.Cap.ROUND);
                                paint.setStrokeWidth(20);
                                canvas.drawPoint(position[0], position[1], paint);

                                //已知一点的正切值，求固定距离的另一点
                                float distance = 100;
                                float tanValue = tan[1] / tan[0];
                                float x2 = (float) (position[0] + distance / (Math.sqrt(tanValue * tanValue + 1)));
                                float y2 = (float) (position[1] + distance * tanValue / (Math.sqrt(tanValue * tanValue + 1)));

                                float x3 = 2 * position[0] - x2;
                                float y3 = 2 * position[1] - y2;

                                paint.setStrokeWidth(5);
                                canvas.drawLine(x2, y2, x3, y3, paint);

                                paint.setStyle(Paint.Style.FILL);
                                paint.setTextSize(40);
                                String animationPercent = "已完成" + percent;
                                float measureText = paint.measureText(animationPercent);
                                canvas.drawText(animationPercent, 400 - measureText / 2, (400 / 2 + 542) / 2, paint);

                            }
                        };

                        final View finalDrawView8 = drawView;

                        ConstraintLayout.LayoutParams layoutParams14 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams14.topToTop = R.id.constraintLayout;
                        layoutParams14.bottomToBottom = R.id.constraintLayout;
                        layoutParams14.leftToLeft = R.id.constraintLayout;
                        layoutParams14.rightToRight = R.id.constraintLayout;

                        Button objectAnimatorButton6 = new Button(getContext());
                        objectAnimatorButton6.setText("ofKeyframe()动画");
                        objectAnimatorButton6.setAllCaps(false);
                        objectAnimatorButton6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //动画从(0,0)开始必须要有，否则就不是理想的动画效果
                                Keyframe keyframe = Keyframe.ofFloat(0, 0);
                                Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 100);
                                Keyframe keyframe3 = Keyframe.ofFloat(1, 50);
                                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("percent", keyframe, keyframe2, keyframe3);
                                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(finalDrawView8, propertyValuesHolder);
                                objectAnimator.setDuration(5000);
                                //线性插值器解决动画重复执行时卡顿问题，默认先加速再减速导致卡顿
                                objectAnimator.setInterpolator(new LinearInterpolator());
                                objectAnimator.start();
                            }
                        });
                        parent.addView(objectAnimatorButton6, layoutParams14);
                        break;
                    case "AnimatorSet":
                        drawView = new View(getContext()) {

                            private int color = 0xFFFF0000;
                            private PointF centerPoint = new PointF();

                            public PointF getCenterPoint() {
                                return centerPoint;
                            }

                            public void setCenterPoint(PointF centerPoint) {
                                this.centerPoint = centerPoint;
                                invalidate();
                            }

                            public int getColor() {
                                return color;
                            }

                            public void setColor(int color) {
                                this.color = color;
                                invalidate(); //强行绘制，执行draw()方法
                            }

                            {
                                path.reset();
                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();
                            }

                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);
                                canvas.translate(centerPoint.x, centerPoint.y);

                                paint.reset();
                                paint.setColor(Color.RED);
                                paint.setShader(new LinearGradient(200, 200, 600, 542, color, Color.BLUE, Shader.TileMode.CLAMP));
                                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                                canvas.drawPath(path, paint);

                            }
                        };

                        final View finalDrawView9 = drawView;

                        ConstraintLayout.LayoutParams layoutParams15 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams15.topToTop = R.id.constraintLayout;
                        layoutParams15.bottomToBottom = R.id.constraintLayout;
                        layoutParams15.leftToLeft = R.id.constraintLayout;
                        layoutParams15.rightToRight = R.id.constraintLayout;

                        Button objectAnimatorButton7 = new Button(getContext());
                        objectAnimatorButton7.setText("AnimatorSet");
                        objectAnimatorButton7.setAllCaps(false);
                        objectAnimatorButton7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ObjectAnimator objectAnimator = ObjectAnimator.ofObject(finalDrawView9, "color", new ArgbEvaluator(), 0xFFFF0000, 0x00FF00);
                                objectAnimator.setDuration(5000);
                                //线性插值器解决动画重复执行时卡顿问题，默认先加速再减速导致卡顿
                                objectAnimator.setInterpolator(new LinearInterpolator());

                                ObjectAnimator objectAnimator2 = ObjectAnimator.ofObject(finalDrawView9, "centerPoint", new PointFEvaluator(),
                                        new PointF(0, 0), new PointF(100, 100));
                                objectAnimator2.setDuration(5000);
                                //线性插值器解决动画重复执行时卡顿问题，默认先加速再减速导致卡顿
                                objectAnimator2.setInterpolator(new LinearInterpolator());

                                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(finalDrawView9, "rotationY", 0, 45);
                                objectAnimator3.setDuration(5000);
                                //线性插值器解决动画重复执行时卡顿问题，默认先加速再减速导致卡顿
                                objectAnimator3.setInterpolator(new LinearInterpolator());

                                AnimatorSet animatorSet = new AnimatorSet();
                                //三种效果等同
                                animatorSet.playSequentially(objectAnimator, objectAnimator2, objectAnimator3);

                                // animatorSet.play(objectAnimator2).after(objectAnimator).before(objectAnimator3);

                                // animatorSet.play(objectAnimator).before(objectAnimator2);
                                // animatorSet.play(objectAnimator2).before(objectAnimator3);
                                animatorSet.start();
                            }
                        });
                        parent.addView(objectAnimatorButton7, layoutParams15);
                        break;
                    case "ValueAnimator":
                        drawView = new View(getContext()) {

                            private int color = 0xFFFF0000;

                            {
                                path.reset();
                                path.addArc(200, 200, 400, 400, -225, 225);
                                path.arcTo(400, 200, 600, 400, -180, 225, false);
                                path.lineTo(400, 542);
                                path.setFillType(Path.FillType.EVEN_ODD);
                                path.close();
                            }

                            public int getColor() {
                                return color;
                            }

                            public void setColor(int color) {
                                this.color = color;
                                invalidate(); //强行绘制，执行draw()方法
                            }

                            @Override
                            protected void onDraw(Canvas canvas) {
                                super.onDraw(canvas);

                                paint.reset();
                                paint.setColor(Color.RED);
                                paint.setShader(new LinearGradient(200, 200, 600, 542, color, Color.BLUE, Shader.TileMode.CLAMP));
                                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                                canvas.drawPath(path, paint);

                            }
                        };

                        final View finalDrawView10 = drawView;

                        ConstraintLayout.LayoutParams layoutParams16 = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams16.topToTop = R.id.constraintLayout;
                        layoutParams16.bottomToBottom = R.id.constraintLayout;
                        layoutParams16.leftToLeft = R.id.constraintLayout;
                        layoutParams16.rightToRight = R.id.constraintLayout;

                        Button objectAnimatorButton8 = new Button(getContext());
                        objectAnimatorButton8.setText("ValueAnimator");
                        objectAnimatorButton8.setAllCaps(false);
                        objectAnimatorButton8.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ObjectAnimator objectAnimator = ObjectAnimator.ofArgb(finalDrawView10, "color", 0xFFFF0000, 0x00FF00);
                                objectAnimator.setDuration(5000);
                                objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
                                //线性插值器解决动画重复执行时卡顿问题，默认先加速再减速导致卡顿
                                objectAnimator.setInterpolator(new LinearInterpolator());
                                objectAnimator.start();

                                ValueAnimator valueAnimator = ValueAnimator.ofFloat(50, 150);
                                valueAnimator.setDuration(5000);
                                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                                //线性插值器解决动画重复执行时卡顿问题，默认先加速再减速导致卡顿
                                valueAnimator.setInterpolator(new LinearInterpolator());
                                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        Float animatedValue = (Float) animation.getAnimatedValue();
                                        finalDrawView10.setTranslationX(animatedValue);
                                    }
                                });
                                valueAnimator.start();
                            }
                        });
                        parent.addView(objectAnimatorButton8, layoutParams16);
                        break;
                    case "小球运动":
                        drawView = new View(getContext()) {

                            List<point> points = new ArrayList<>();
                            point centerPoint = new point();

                            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 100, 0L, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<Runnable>(500),
                                    new ThreadFactory() {
                                        @Override
                                        public Thread newThread(Runnable r) {
                                            return new Thread(r, "线程池创建线程");
                                        }
                                    });

                            {
                                for (int i = 0; i < 100; i++) {
                                    points.add(new point());
                                }
                            }

                            @Override
                            protected void onDraw(final Canvas canvas) {
                                final float moveTime = 1 / 16f;
                                centerPoint.draw(canvas);
                                paint.setStrokeWidth(5);
                                paint.setColor(Color.RED);

                                for (point point : points) {
                                    point.draw(canvas);
                                    point.move(48f, moveTime);
                                    if (point.distanceToCenter(point, centerPoint) <= 100) {
                                        canvas.drawLine(point.x, point.y, centerPoint.x, centerPoint.y, paint);
                                    }
                                }
                                threadPoolExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        invalidate();
                                        try {
                                            Thread.sleep((long) (moveTime * 1000));
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                            class point {
                                private float x;
                                private float y;
                                private float radius;
                                private Paint paint = new Paint();
                                private float randomR1;
                                private float randomG1;
                                private float randomB1;
                                private float randomR2;
                                private float randomG2;
                                private float randomB2;
                                private double angle;

                                public point() {
                                    x = (float) (Math.random() * 1000);
                                    y = (float) (Math.random() * 1000);
                                    randomR1 = (float) (Math.random() * 255);
                                    randomG1 = (float) (Math.random() * 255);
                                    randomB1 = (float) (Math.random() * 255);
                                    randomR2 = (float) (Math.random() * 255);
                                    randomG2 = (float) (Math.random() * 255);
                                    randomB2 = (float) (Math.random() * 255);
                                    radius = 20;
                                    angle = Math.random() * 2 * Math.PI;
                                }

                                public point(float x, float y) {
                                    this();
                                    this.x = x;
                                    this.y = y;
                                }

                                public point(float x, float y, float radius) {
                                    this();
                                    this.x = x;
                                    this.y = y;
                                    this.radius = radius;
                                }

                                void draw(Canvas canvas) {
                                    int canvasWidth = canvas.getWidth();
                                    int canvasHeight = canvas.getHeight();


                                    edgeCollision(canvasWidth, canvasHeight);

                                    paint.setShader(new LinearGradient(x - radius, y - radius, x + radius, y + radius,
                                            Color.rgb(randomR1, randomG1, randomB1), Color.rgb(randomR2, randomG2, randomB2), Shader.TileMode.CLAMP));
                                    canvas.drawCircle(x, y, radius, paint);

                                }

                                //超出屏幕边界处理, 碰撞效果
                                private void edgeCollision(int canvasWidth, int canvasHeight) {
                                    if (x - radius <= 0) {
                                        angle = Math.PI - angle;
                                        x = radius;
                                    } else if (canvasWidth <= x + radius) {
                                        angle = Math.PI - angle;
                                        x = canvasWidth - radius;
                                    }
                                    if (y - radius <= 0) {
                                        angle = angle - Math.PI;
                                        y = radius;
                                    } else if (canvasHeight <= y + radius) {
                                        angle = angle - Math.PI;
                                        y = canvasHeight - radius;
                                    }
                                }

                                void move(float speed, float moveTime) {
                                    x = (float) (x + speed * moveTime * Math.cos(angle));
                                    y = (float) (y + speed * moveTime * Math.sin(angle));
                                }

                                float distanceToCenter(point point, point centerPoint) {
                                    return (float) Math.sqrt((point.x - centerPoint.x) * (point.x - centerPoint.x) + (point.y - centerPoint.y) * (point.y - centerPoint.y));
                                }

                                @Override
                                public String toString() {
                                    return "point{" +
                                            "x=" + x +
                                            ", y=" + y +
                                            ", radius=" + radius +
                                            ", color1=#" + Float.toHexString(randomR1) + Float.toHexString(randomG1) + Float.toHexString(randomB1) +
                                            ",color2=#" + Float.toHexString(randomR2) + Float.toHexString(randomG2) + Float.toHexString(randomB2) +
                                            ", angle=" + angle * 180 / Math.PI + "°" +
                                            '}';
                                }
                            }

                        };
                        break;
                    default:
                        break;
                }
            }
        }
        if (drawView != null) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1000, 1000);
            layoutParams.setMargins(500, 500, 0, 0);
            parent.addView(drawView, layoutParams);
        }
        return root;
    }

    public static Bitmap getBitmapByWH(Bitmap bitmap, int screenWidth,
                                       int screenHeight) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = (float) screenWidth / w;
        float scaleHeight = (float) screenHeight / h;
        // scaleWidth = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        if (bitmap != null && !bitmap.equals(bmp) && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bmp;
    }

    public static Bitmap getBitmapByScaleXY(Bitmap bitmap, double scaleX,
                                            double scaleY) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = (float) scaleX;
        float scaleHeight = (float) scaleY;
        // scaleWidth = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        if (bitmap != null && !bitmap.equals(bmp) && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bmp;
    }
}