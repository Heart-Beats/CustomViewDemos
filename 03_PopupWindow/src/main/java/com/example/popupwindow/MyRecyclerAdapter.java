package com.example.popupwindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author 淡然爱汝不离  on  2018/7/27 0027  16:32
 * 邮箱：913305160@qq.com
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List dataList;
    private ViewHolderCallBack viewHolderCallBack;

    public MyRecyclerAdapter(List dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //必须使用此方法填充子项视图，attachToRoot必须设置为空
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.down_arrow_item, parent, false);
        final ViewHolder holder = new ViewHolder(itemView);
        holder.delete.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int holderPosition = holder.getAdapterPosition();
                dataList.remove(holderPosition);
                MyRecyclerAdapter.this.notifyItemRemoved(holderPosition);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int holderPosition = holder.getAdapterPosition();
                viewHolderCallBack.viewHolderClick(holder,holderPosition);
            }
        });
        return holder;
    }

    public void addViewHolderCallBack(ViewHolderCallBack viewHolderCallBack) {
        this.viewHolderCallBack = viewHolderCallBack;
    }

    //绑定视图时尽量不要定义事件，创建时给视图加上事件即可
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.textContent.setText((CharSequence) dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface ViewHolderCallBack {
        /**
         * 当前viewHolder的点击回调
         *
         * @param holder   当前的holder
         * @param position holder的位置
         */
        void viewHolderClick(ViewHolder holder, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textContent;
        ImageView delete;

        ViewHolder(View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.text_content);
            delete = itemView.findViewById(R.id.deiete);
        }
    }
}

