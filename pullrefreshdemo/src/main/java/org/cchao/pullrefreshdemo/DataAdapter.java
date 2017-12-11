package org.cchao.pullrefreshdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shucc on 17/12/11.
 * cc@cchao.org
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {

    private List<String> data;

    private OnItemClickListener onItemClickListener;

    public DataAdapter(List<String> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        DataHolder dataHolder = new DataHolder(view);
        if (null != onItemClickListener) {
            dataHolder.itemView.setOnClickListener(view1 -> onItemClickListener.onClick(dataHolder.getAdapterPosition()));
        }
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        holder.textData.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return null == data ? 0 : data.size();
    }

    protected class DataHolder extends RecyclerView.ViewHolder {

        TextView textData;

        public DataHolder(View itemView) {
            super(itemView);
            textData = itemView.findViewById(R.id.text_data);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
