package com.example.myviewpaper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * @author SYSTEM
 */
public class MainActivity extends AppCompatActivity {

    public final static float DENSITY = MyApplication.getContext().getResources().getDisplayMetrics().density;
    MyViewPager myViewPager;
    private int[] imagesId = {
            R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,
            R.drawable.a6
    };
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewPager = findViewById(R.id.my_view_pager);
        radioGroup = findViewById(R.id.radioGroup);
        initViewData();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                myViewPager.scrollToPager(checkedId);
            }
        });
        myViewPager.setOnPagerChangeListener(new MyViewPager.OnPagerChangeListener() {
            @Override
            public void onPagerToChange(int position) {
                radioGroup.check(position);
            }
        });
    }

    private void initViewData() {
        for (int imageId : imagesId) {
            CardView cardView = new CardView(this);
            cardView.setBackgroundResource(imageId);
            cardView.setRadius(10*DENSITY);
            myViewPager.addView(cardView);
        }
        View measureTest = View.inflate(this, R.layout.measure_test,null);
        myViewPager.addView(measureTest,2);
        for (int i = 0; i < myViewPager.getChildCount(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            radioGroup.addView(radioButton);
            if (i == 0) {
                radioButton.setChecked(true);
            }
        }
    }
}
