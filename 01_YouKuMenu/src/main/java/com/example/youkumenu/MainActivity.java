package com.example.youkumenu;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 淡然爱汝不离
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.level3)
    ConstraintLayout level3;
    @BindView(R.id.icon_menu)
    ImageView iconMenu;
    @BindView(R.id.level2)
    ConstraintLayout level2;
    @BindView(R.id.icon_home)
    ImageView iconHome;
    private boolean level2Show = true;
    private boolean level3Show = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.icon_menu, R.id.icon_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon_menu:
                if (level3Show) {
                    hideViewByAnimator(level3);
                } else {
                    showViewByAnimator(level3);
                }
                level3Show = !level3Show;
                break;
            case R.id.icon_home:
                if (level2Show) {
                    hideViewByAnimator(level2);
                    if (level3Show) {
                        hideViewByAnimator(level3);
                        level3Show = !level3Show;
                    }
                } else {
                    showViewByAnimator(level2);
                }
                level2Show = !level2Show;
                break;
            default:
                break;
        }
    }

    private void showViewByAnimation(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(180, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    private void hideViewByAnimation(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);

    }

    //设置属性动画
    private void showViewByAnimator(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation",
                180, 360);
        objectAnimator.setDuration(500);
        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
        objectAnimator.start();
    }

    private void hideViewByAnimator(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation",
                0, 180);
        objectAnimator.setDuration(500);
        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
        objectAnimator.start();
    }
}