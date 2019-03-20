package org.cchao.pullrefreshdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.cchao.pullrefreshdemo.R;
import org.cchao.pullrefreshdemo.model.DataModel;

import java.util.List;

/**
 * Created by shucc on 2019/3/19.
 * cc@cchao.org
 */
public class DemoDataAdapter extends RecyclerView.Adapter<DemoDataAdapter.DataHolder> {

    private List<DataModel> dataModelList;

    public DemoDataAdapter(List<DataModel> dataModelList) {
        this.dataModelList = dataModelList;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_demo_data, viewGroup, false);
        DataHolder dataHolder = new DataHolder(view);
        dataHolder.rvData1.setNestedScrollingEnabled(false);
        dataHolder.rvData1.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));
        dataHolder.imgControl1.setOnClickListener(v -> {
            int position = dataHolder.getAdapterPosition();
            DataModel dataModel = dataModelList.get(position);
            dataModel.setExpend1(!dataModel.isExpend1());
            dataModelList.set(position, dataModel);
            if (dataModel.isExpend1()) {
                dataHolder.imgControl1.setImageResource(R.drawable.icon_less);
                dataHolder.rvData1.setVisibility(View.VISIBLE);
            } else {
                dataHolder.imgControl1.setImageResource(R.drawable.icon_more);
                dataHolder.rvData1.setVisibility(View.GONE);
            }
        });

        dataHolder.rvData2.setNestedScrollingEnabled(false);
        dataHolder.rvData2.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));
        dataHolder.imgControl2.setOnClickListener(v -> {
            int position = dataHolder.getAdapterPosition();
            DataModel dataModel = dataModelList.get(position);
            dataModel.setExpend2(!dataModel.isExpend2());
            dataModelList.set(position, dataModel);
            if (dataModel.isExpend2()) {
                dataHolder.imgControl2.setImageResource(R.drawable.icon_less);
                dataHolder.rvData2.setVisibility(View.VISIBLE);
            } else {
                dataHolder.imgControl2.setImageResource(R.drawable.icon_more);
                dataHolder.rvData2.setVisibility(View.GONE);
            }
        });
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder dataHolder, int i) {
        DataModel dataModel = dataModelList.get(i);
        dataHolder.textTitle.setText(dataModel.getTitle());
        dataHolder.textName1.setText(dataModel.getName1());
        dataHolder.textName2.setText(dataModel.getName2());
        if (dataModel.isExpend1()) {
            dataHolder.imgControl1.setImageResource(R.drawable.icon_less);
            dataHolder.rvData1.setVisibility(View.VISIBLE);
            dataHolder.rvData1.setAdapter(new FoodProductAdapter(dataModel.getProductModelList1()));
        } else {
            dataHolder.imgControl1.setImageResource(R.drawable.icon_more);
            dataHolder.rvData1.setVisibility(View.GONE);
        }
        if (dataModel.isExpend2()) {
            dataHolder.imgControl2.setImageResource(R.drawable.icon_less);
            dataHolder.rvData2.setVisibility(View.VISIBLE);
            dataHolder.rvData2.setAdapter(new FoodProductAdapter(dataModel.getProductModelList2()));
        } else {
            dataHolder.imgControl2.setImageResource(R.drawable.icon_more);
            dataHolder.rvData2.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return null == dataModelList ? 0 : dataModelList.size();
    }

    class DataHolder extends RecyclerView.ViewHolder {

        TextView textTitle;
        ImageView imgControl1;
        TextView textName1;
        RecyclerView rvData1;
        LinearLayout llParent2;
        ImageView imgControl2;
        TextView textName2;
        RecyclerView rvData2;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            imgControl1 = itemView.findViewById(R.id.img_control1);
            textName1 = itemView.findViewById(R.id.text_name1);
            rvData1 = itemView.findViewById(R.id.rv_data1);
            llParent2 = itemView.findViewById(R.id.ll_parent2);
            imgControl2 = itemView.findViewById(R.id.img_control2);
            textName2 = itemView.findViewById(R.id.text_name2);
            rvData2 = itemView.findViewById(R.id.rv_data2);
        }
    }
}
