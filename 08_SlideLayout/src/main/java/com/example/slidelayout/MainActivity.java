package com.example.slidelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SYSTEM
 */
public class MainActivity extends AppCompatActivity {

    private List<MyBean> beanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        MyAdapter adapter = new MyAdapter(beanList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }

    private void initData() {
        if (beanList == null) {
            beanList = new ArrayList<>();
        }
        for (int i = 0; i < 100; i++) {
            beanList.add(new MyBean("item" + i));
        }
    }
}
