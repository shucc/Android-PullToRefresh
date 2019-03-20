package org.cchao.pullrefreshdemo.model;

import java.util.List;

/**
 * Created by shucc on 2019/3/20.
 * cc@cchao.org
 */
public class DataModel {

    private String title;

    private String name1;

    private List<FoodProductModel> productModelList1;

    private boolean expend1;

    private String name2;

    private List<FoodProductModel> productModelList2;

    private boolean expend2;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public List<FoodProductModel> getProductModelList1() {
        return productModelList1;
    }

    public void setProductModelList1(List<FoodProductModel> productModelList1) {
        this.productModelList1 = productModelList1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public List<FoodProductModel> getProductModelList2() {
        return productModelList2;
    }

    public void setProductModelList2(List<FoodProductModel> productModelList2) {
        this.productModelList2 = productModelList2;
    }

    public boolean isExpend1() {
        return expend1;
    }

    public void setExpend1(boolean expend1) {
        this.expend1 = expend1;
    }

    public boolean isExpend2() {
        return expend2;
    }

    public void setExpend2(boolean expend2) {
        this.expend2 = expend2;
    }
}
