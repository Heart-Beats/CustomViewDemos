package com.example.slidelayout;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

/**
 * @author SYSTEM  on  2018/08/01 at 21:39
 * 邮箱：913305160@qq.com
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

    private List<MyBean> dataList;
    private SlideItem slideItem;
    private boolean enableClick = false;

    MyAdapter(List<MyBean> beanList) {
        dataList = beanList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item, parent,
                false);
        final Holder holder = new Holder(itemView);
        holder.itemContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enableClick) {
                    Toast.makeText(v.getContext(), holder.itemContent.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.itemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
        SlideItem slideItem = (SlideItem) itemView;
        slideItem.setOnStateChangeListener(new MyOnStateChangeListener());
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.itemContent.setText(dataList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView itemContent;
        private TextView itemMenu;

        Holder(View itemView) {
            super(itemView);
            itemContent = itemView.findViewById(R.id.item_content);
            itemMenu = itemView.findViewById(R.id.item_menu);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Holder holder = (Holder) o;
            return Objects.equals(itemContent, holder.itemContent) &&
                    Objects.equals(itemMenu, holder.itemMenu);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public int hashCode() {

            return Objects.hash(itemContent, itemMenu);
        }
    }

    class MyOnStateChangeListener implements SlideItem.OnStateChangeListener {

        @Override
        public void onClose(SlideItem layout) {
            if (slideItem == layout) {
                slideItem = null;
            }
        }

        @Override
        public void onDown(SlideItem layout) {
            if (slideItem != null && slideItem != layout) {
                slideItem.closeMenu();
                enableClick = false;
            } else {
                enableClick = slideItem == null;
            }
        }

        @Override
        public void onOpen(SlideItem layout) {
            slideItem = layout;
        }
    }

}
