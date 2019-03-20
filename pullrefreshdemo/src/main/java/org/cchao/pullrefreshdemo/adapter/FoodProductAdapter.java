package org.cchao.pullrefreshdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.cchao.pullrefreshdemo.R;
import org.cchao.pullrefreshdemo.model.FoodProductModel;

import java.util.List;

/**
 * Created by shucc on 2019/3/20.
 * cc@cchao.org
 */
public class FoodProductAdapter extends RecyclerView.Adapter<FoodProductAdapter.FoodProductHolder> {

    private List<FoodProductModel> foodProductModels;

    public FoodProductAdapter(List<FoodProductModel> foodProductModels) {
        this.foodProductModels = foodProductModels;
    }

    @NonNull
    @Override
    public FoodProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_demo_food_product, viewGroup, false);
        FoodProductHolder foodProductHolder = new FoodProductHolder(view);
        foodProductHolder.rvData.setNestedScrollingEnabled(false);
        foodProductHolder.rvData.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));
        foodProductHolder.itemView.setOnClickListener(v -> Toast.makeText(viewGroup.getContext(), "点击了" + foodProductModels.get(foodProductHolder.getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show());
        return foodProductHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodProductHolder foodProductHolder, int i) {
        FoodProductModel foodProductModel = foodProductModels.get(i);
        foodProductHolder.textName.setText(foodProductModel.getName());
        foodProductHolder.rvData.setAdapter(new FoodDetailAdapter(foodProductModel.getFoodDetailModels()));
    }

    @Override
    public int getItemCount() {
        return null == foodProductModels ? 0 : foodProductModels.size();
    }

    class FoodProductHolder extends RecyclerView.ViewHolder {

        TextView textName;
        RecyclerView rvData;

        public FoodProductHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            rvData = itemView.findViewById(R.id.rv_data);
        }
    }
}
