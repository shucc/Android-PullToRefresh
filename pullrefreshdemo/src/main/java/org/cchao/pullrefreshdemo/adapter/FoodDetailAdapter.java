package org.cchao.pullrefreshdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.cchao.pullrefreshdemo.R;
import org.cchao.pullrefreshdemo.model.FoodDetailModel;

import java.util.List;

/**
 * Created by shucc on 2019/3/20.
 * cc@cchao.org
 */
public class FoodDetailAdapter extends RecyclerView.Adapter<FoodDetailAdapter.FoodDetailHolder> {

    private List<FoodDetailModel> foodDetailModels;

    public FoodDetailAdapter(List<FoodDetailModel> foodDetailModels) {
        this.foodDetailModels = foodDetailModels;
    }

    @NonNull
    @Override
    public FoodDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_demo_food_detail, viewGroup, false);
        FoodDetailHolder foodDetailHolder = new FoodDetailHolder(view);
        return foodDetailHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodDetailHolder foodDetailHolder, int i) {
        foodDetailHolder.textName.setText(foodDetailModels.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return null == foodDetailModels ? 0 : foodDetailModels.size();
    }

    class FoodDetailHolder extends RecyclerView.ViewHolder {

        TextView textName;

        public FoodDetailHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
        }
    }
}
