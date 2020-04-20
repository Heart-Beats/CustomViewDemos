package com.example.quickindex;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author SYSTEM  on  2018/07/31 at 19:29
 * 邮箱：913305160@qq.com
 */
public class ContactPersonAdapter extends RecyclerView.Adapter<ContactPersonAdapter.Holder> {

    private static final String TAG = "ContactPersonAdapter";

    private List<Person> personList;

    public ContactPersonAdapter(List<Person> personList) {
        this.personList = personList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_persons_item,
                parent, false);
        return new Holder(view);
    }

    /**
     *      此方法中一般用来绑定视图的显示，不建议在此方法中绑定事件
     *
     * @param holder    当前的holder，即视图持有者
     * @param position  当前holder的位置
     */
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.personName.setText(personList.get(position).getName());
        String index = personList.get(position).getPinYin().substring(0, 1);
        holder.indexWord.setText(index);
        if (position == 0) {
            holder.indexWord.setVisibility(View.VISIBLE);
        } else {
            String preIndex = personList.get(position-1).getPinYin().substring(0, 1);
            Log.e(TAG, "onBindViewHolder: 下标："+index);
            if (preIndex.equals(index)) {
                holder.indexWord.setVisibility(View.GONE);
            } else {
                holder.indexWord.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "getItemCount: item总个数"+personList.size() );
        return personList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView indexWord;
        private TextView personName;

        public Holder(View itemView) {
            super(itemView);
            indexWord = itemView.findViewById(R.id.index_word);
            personName = itemView.findViewById(R.id.persion_name);
        }
    }
}
