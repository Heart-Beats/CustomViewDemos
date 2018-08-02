package com.example.quickindex;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author SYSTEM
 */
public class MainActivity extends AppCompatActivity {

    private TextView charIndex;
    private Handler handler = new Handler();
    private List<Person> personList;
    private LinearLayoutManager layoutManager;

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager  设置RecyclerView对应的manager
     * @param n  要跳转的位置
     */
    public static void moveToPosition(LinearLayoutManager manager, int n) {
        //  将recyclerView滚动到指定位置，与屏幕上方偏移为0
        manager.scrollToPositionWithOffset(n, 0);
        //  从底部填充视图
        manager.setStackFromEnd(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.contact_list);
        charIndex = findViewById(R.id.chara_index);
        IndexView indexView = findViewById(R.id.indexView);
        indexView.setIndexChangeListener(new IndexView.OnIndexChangeListener() {
            @Override
            public void onIndexChange(int index, String word) {
                updateListView(word);
                updateWord(word);
            }
        });
        initData();
        ContactPersonAdapter adapter = new ContactPersonAdapter(personList);
        recyclerView.setAdapter(adapter);
        // recyclerView必须设置布局管理器，否则不会显示数据
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void updateListView(String word) {
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getPinYin().substring(0, 1).equals(word)) {
                moveToPosition(layoutManager, i);
                return;
            }
        }
    }

    private void updateWord(String word) {
        charIndex.setVisibility(View.VISIBLE);
        charIndex.setText(word);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //也是运行在主线程
                System.out.println(Thread.currentThread().getName() + "------------");
                charIndex.setVisibility(View.GONE);
            }
        }, 3000);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        personList = new ArrayList<>();
        personList.add(new Person("张晓飞"));
        personList.add(new Person("杨光福"));
        personList.add(new Person("胡继群"));
        personList.add(new Person("刘畅"));

        personList.add(new Person("钟泽兴"));
        personList.add(new Person("尹革新"));
        personList.add(new Person("安传鑫"));
        personList.add(new Person("张骞壬"));

        personList.add(new Person("温松"));
        personList.add(new Person("李凤秋"));
        personList.add(new Person("刘甫"));
        personList.add(new Person("娄全超"));
        personList.add(new Person("张猛"));

        personList.add(new Person("王英杰"));
        personList.add(new Person("李振南"));
        personList.add(new Person("孙仁政"));
        personList.add(new Person("唐春雷"));
        personList.add(new Person("牛鹏伟"));
        personList.add(new Person("姜宇航"));

        personList.add(new Person("刘挺"));
        personList.add(new Person("张洪瑞"));
        personList.add(new Person("张建忠"));
        personList.add(new Person("侯亚帅"));
        personList.add(new Person("刘帅"));

        personList.add(new Person("乔竞飞"));
        personList.add(new Person("徐雨健"));
        personList.add(new Person("吴亮"));
        personList.add(new Person("王兆霖"));

        personList.add(new Person("阿三"));
        personList.add(new Person("李博俊"));


        //排序
        Collections.sort(personList, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                return lhs.getPinYin().compareTo(rhs.getPinYin());
            }
        });
    }
}
