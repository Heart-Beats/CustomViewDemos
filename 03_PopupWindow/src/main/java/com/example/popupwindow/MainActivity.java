package com.example.popupwindow;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 淡然爱汝不离
 */
public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private PopupWindow popupWindow;
    private RecyclerView recyclerView;
    private List<String> userNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        ImageView downArrow = findViewById(R.id.down_arrow);
        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow();
                    popupWindow.setWidth(editText.getWidth());
                    popupWindow.setHeight(DensityUtil.dp2px(view.getContext(), 200));
                    popupWindow.setFocusable(true);
                    popupWindow.setContentView(recyclerView);
                }
                popupWindow.showAsDropDown(editText,0,0);
            }
        });
        recyclerView = new RecyclerView(this);
        final MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(initUserNameList());
        myRecyclerAdapter.addViewHolderCallBack(new MyRecyclerAdapter.ViewHolderCallBack() {
            @Override
            public void viewHolderClick(MyRecyclerAdapter.ViewHolder holder, int position) {
                editText.setText(holder.textContent.getText());
                popupWindow.dismiss();
                holder.itemView.setBackgroundResource(R.drawable.item_press);
            }
        });
        recyclerView.setAdapter(myRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        recyclerView.setBackgroundResource(R.drawable.rv_background);
    }

    private List initUserNameList(){
        if (userNameList == null) {
            userNameList = new ArrayList<>();
        }
        String name = "";
        for (int i = 97; i < 123; i++) {
            char c = (char) i;
            name += String.valueOf(c);
            userNameList.add(name);
        }
        return userNameList;
    }
}
