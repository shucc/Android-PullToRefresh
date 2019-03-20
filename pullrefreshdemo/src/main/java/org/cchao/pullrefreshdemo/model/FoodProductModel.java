package org.cchao.pullrefreshdemo.model;

import java.util.List;

/**
 * Created by shucc on 2019/3/20.
 * cc@cchao.org
 */
public class FoodProductModel {

    private String name;

    List<FoodDetailModel> foodDetailModels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FoodDetailModel> getFoodDetailModels() {
        return foodDetailModels;
    }

    public void setFoodDetailModels(List<FoodDetailModel> foodDetailModels) {
        this.foodDetailModels = foodDetailModels;
    }
}
