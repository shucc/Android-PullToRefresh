package org.cchao.pullrefreshdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.cchao.pullrefreshdemo.adapter.DemoDataAdapter;
import org.cchao.pullrefreshdemo.model.DataModel;
import org.cchao.pullrefreshdemo.model.FoodDetailModel;
import org.cchao.pullrefreshdemo.model.FoodProductModel;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    private RecyclerView rvData;

    private List<DataModel> data;

    private DemoDataAdapter adapter;

    public static void launch(Context context) {
        Intent starter = new Intent(context, DemoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        rvData = findViewById(R.id.rv_data);

        data = new ArrayList<>();
        showData();
        rvData.postDelayed(this::getData, 50);
    }

    private void getData() {
        List<DataModel> temp = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            DataModel dataModel = new DataModel();
            dataModel.setTitle("我是标题" + i);
            dataModel.setName1("上面的食物" + i);
            dataModel.setExpend1(true);
            dataModel.setName2("下面的食物" + i);
            dataModel.setExpend2(true);
            List<FoodProductModel> foodProductModels1 = new ArrayList<>();
            List<FoodProductModel> foodProductModels2 = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                FoodProductModel foodProductModel1 = new FoodProductModel();
                foodProductModel1.setName("上面食物详情" + i + j);
                List<FoodDetailModel> foodDetailModels = new ArrayList<>();
                for (int k = 0; k < 4; k++) {
                    FoodDetailModel foodDetailModel = new FoodDetailModel();
                    foodDetailModel.setName("自定义" + i + "-" + j + "-" + k);
                    foodDetailModels.add(foodDetailModel);
                }
                foodProductModel1.setFoodDetailModels(foodDetailModels);
                foodProductModels1.add(foodProductModel1);

                FoodProductModel foodProductMode2 = new FoodProductModel();
                foodProductMode2.setName("下面的食物详情" + i + j);
                List<FoodDetailModel> foodDetailModels2 = new ArrayList<>();
                for (int k = 0; k < 4; k++) {
                    FoodDetailModel foodDetailModel = new FoodDetailModel();
                    foodDetailModel.setName("下面自定义" + i + "-" + j + "-" + k);
                    foodDetailModels2.add(foodDetailModel);
                }
                foodProductModel1.setFoodDetailModels(foodDetailModels2);
                foodProductModels2.add(foodProductMode2);
            }
            dataModel.setProductModelList1(foodProductModels1);
            dataModel.setProductModelList2(foodProductModels2);
            temp.add(dataModel);
        }
        data.clear();
        data.addAll(temp);
        showData();
    }

    private void showData() {
        if (null != adapter) {
            adapter.notifyDataSetChanged();
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvData.setLayoutManager(linearLayoutManager);
            adapter = new DemoDataAdapter(data);
            rvData.setAdapter(adapter);
        }
    }
}
